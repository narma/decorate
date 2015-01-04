# Decorator
Inspired by Python decorators.
Following examples in clean clojure w/o any libs show how it works.


Example of simple decorator which is calling original fn

```
(defn mydecorator
  [f] ; takes fn
  (fn [& args] ; returns fn which will be invoked instead of f
    ;; decorator staff:
    ;; logging, right checks, measuring performance, anything other.
    (apply f args))) ;; invoke original f

(def new-func (mydecorator func))
```

A little more complex, this function returns decorator
```
(defn mydecorator-with-args
  [& decorator-args]
  (fn [f] ;; decorator, takes fn
    (fn [& args]
      (apply f args))))

(def new-func ((mydecorator-with-args "hello") func))
```

We can compose decorators with `comp`

```
(def my-super-decorator (comp (mydecorator-with-args "hello") mydecorator))
```

# Defdecorator macro

Signature
```
(defdecorator name
  [original-func decorator-args?]
  [original-func-args] body)
```

Helps to define decorators with or without arguments
The difference is in one or more arguments are passed to the first vector

If there's one then macro produces a decorator without arguments
and this argument will be the original function.

If there're more than one then the first argument remains an original function,
and the rest part is arguments for fn which produces a real decorator.


First case, define decorator without arguments:
```
(defdecorator with-logging
  [f] [& args]
  (println "Entering")
  (apply f args))

(with-logging original-func)
```

Second case, define decorator with arguments

```
(defdecorator with-logging
  [f v] [& args]
  (println "Hello there " v)
  (apply f args))
((with-logging "argument1") original-func)
```

## Utility macros

`(redef name value)` - redefine a var without losing its metadata.

`(decorate fn decorator)` - redefine a var fn with applied decorator


## More examples
See https://github.com/narma/decorate/blob/master/test/decorate/decorate_test.clj
