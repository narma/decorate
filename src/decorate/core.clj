(ns decorate.core
  "Macros for redefining functions and defining decorators.")


(defmacro defdecorator
  "define decorators in two ways: w & w/o args
  1) (defdecorator
        [original-function] ;; one argument
        [& original-functions-args]
          ... body)
  2) (defdecorator
        [original-function a b] ;; many arguments, or
                                ;; [f & dargs] for ex.
        [& original-functions-args]
          ... body)"
  [name' decorator-args func-args & body]
  (if (= 1
         (count decorator-args))
    `(defn ~name'
       ~decorator-args
       (fn ~func-args ~@body))
    `(defn ~name'
       ~(subvec decorator-args 1)
       (fn [~(first decorator-args)]
         (fn ~func-args ~@body)))))

(defmacro redef
  "Redefine an existing value, keeping the metadata intact."
  [name value]
  `(let [m# (meta #'~name)
         v# (def ~name ~value)]
     (alter-meta! v# merge m#)
     v#))

(defmacro decorate
  "Redefine fn var with applied decorator to fn"
  [f decorator]
  `(redef ~f (~decorator ~f)))

(defmacro ns-decorate
  "Intern the new function to ns with decorated value"
  [f decorator]
  `(let [m# (meta #'~f)
         ns# (:ns m#)
         name# (:name m#)]
    (intern ns# name# (~decorator ~f))))
