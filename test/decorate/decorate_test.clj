(ns decorate.decorate-test
  (:require [clojure.test :refer :all]
            [decorate.core :refer :all]))


(defn- hello []
  (str "hello"))

(defn- wrap-with-value [func v]
    (fn [& args]
      (str (apply func args) v)))

(deftest decorate-test
  (decorate hello (wrap-with-value "1"))
  (is (= (hello) "hello1"))
  (decorate hello (wrap-with-value "2") (wrap-with-value "3"))
  (is (= (hello) "hello123")))

(defn- a []
  "a")

(defn- b []
  "b")

(deftest decorate-with-test
  (decorate-with (wrap-with-value " kitty ") a b)
  (is (= "a kitty b kitty ") (str (a) (b))))


(deftest decorate-local-test
  (is (= (decorate-local
       (wrap-with-value "1")
       [hello]
       (hello)) "hello1"))

  (is (= (hello) "hello")))

