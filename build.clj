(ns build
  (:require [clojure.tools.build.api :as b]))

(def lib 'com.xadecimal/riddley)
(def version "0.2.3")
(def description "code-walking without caveats (fork of ztellman/riddley)")
(def license {:name "MIT License"
              :url "http://opensource.org/licenses/MIT"})

(def class-dir "target/classes")
(def basis (b/create-basis))

(defn clean [_]
  (b/delete {:path "target"}))

(defn compile [_]
  (b/javac {:src-dirs ["src/com/xadecimal/riddley"]
            :class-dir class-dir
            :basis basis
            :javac-opts ["-source" "8" "-target" "8"]}))
