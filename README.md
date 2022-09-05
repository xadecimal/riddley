Fork of ztellman/riddley that allows walking forms that want to rewrite the (var ...) special form.

    Walker is my name
    and I am the same.
    Riddley Walker.
    Walking my riddels
    where ever theyve took me
    and walking them now
    on this paper the same.

from [Riddley Walker](http://en.wikipedia.org/wiki/Riddley_Walker) by Russell Hoban

---

Code may be data, but only some of that data is executable.  If we want to perform a pervasive code transformation, using something like `clojure.walk` presents a few problems:

* binding forms are treated the same as actual expressions
* `clojure.walk/macroexpand-all` will pass in a nil `&env` to all macros
* macroexpansion doesn't expand inlined functions

This means that transforms that we intend to apply to expressions may have unintended consequences on a `fn`, `let`, or `case` form.  It also means that any macro which relies on `&env` will not compose with our transformation.  Finally, if inlined functions aren't expanded, certain transformations will break.

### usage

This fork is not published to Clojars or Mvn Central as of yet, for now you can only depend on it as a git deps:
```clj
com.xadecimal/riddley {:git/url "https://github.com/xadecimal/riddley.git"
                       :git/tag "0.2.3"
                       :git/sha "e9ecc4b"}
```

Do not use an alternate coordinate, make sure to always declare it as `com.xadecimal/riddley` to properly detect and handle potential dependency conflicts.

The library needs to be prepped first, so remember to run:
```clj
clj -X:deps prep
```

This fork uses different namespace prefix, all starting with `com.xadecimal.riddley` and is therefore safe to use in conjunction with the original ztellman's `riddley/riddley` from Clojars, as they will not conflict.

Riddley provides a correct `com.xadecimal.riddley.walk/macroexpand-all`, which preserves the binding information in `&env` and expands inlined functions, and `riddley.walk/walk-exprs`, which is a general mechanism for code walking and transformation.

`walk-exprs` takes two arguments, a `predicate` for whether it should transform the sub-form, and a `handler` for doing the transformation.

```clj
com.xadecimal.riddley.walk> (walk-exprs number? inc '(let [n 1] (+ n 1)))
(let* [n 2] (. clojure.lang.Numbers (add n 2)))
```

Notice that `walk-exprs` implicitly macroexpands the form, including the inline form for `+`.  Unlike `clojure.walk`, if `handler` is called, sub-forms will not be walked.  The handler function is responsible for recursively calling `walk-exprs` on the form it's handed.

Access to `&env` is available via `(com.xadecimal.riddley.compiler/locals)` if you need it as part of your transformation.

Full documentation can be found [here](http://aleph.io/codox/riddley/).

### license

Copyright © 2013 Zachary Tellman
Copyright © 2022 xadecimal

Distributed under the MIT License.
