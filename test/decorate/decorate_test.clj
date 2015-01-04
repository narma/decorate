(ns decorate.decorate-test
  (:require [clojure.test :refer :all]
            [decorate.core :refer :all]))


(defn- hello
  ([]
    "hello")
  ([a] (str "hello " a)))


(defdecorator wrap-with-value
  [f v] [& args]
      (str (apply f args) v))


(deftest decorate-test
  (decorate hello (wrap-with-value "!"))
  (is (= (hello) "hello!"))
  (decorate hello (comp (wrap-with-value "2")
                        (wrap-with-value "1")))
  (is (= (hello) "hello!12")))

