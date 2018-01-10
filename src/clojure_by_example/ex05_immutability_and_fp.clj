(ns clojure-by-example.ex05-immutability-and-fp)


;; What if we try to "change" things?


;; EXERCISE:
;;
;; Predict the value of pi:

(def pi 3.141)

(+ pi 1) ; add one to pi

pi ; evaluate to confirm


;; Hm, let's try vectors and maps.
;;
;; - We can "associate" new key-vals into an existing map
(assoc {:a 1}
  :b 2
  :c 3)
;;
;; - With assoc, we can also update existing key-value pairs
(assoc {:a 1 :b 2} :b 99)
;;
;; - And, finally, we can "dissociate" existing key-vals
(dissoc {:a 1 :b 2 :c 3} :b :c)


;; EXERCISE:
;;
;; Predict the result of filtering by the value of `:habitable?`:

;; First let's...
(def planets [{:pname "Earth" :moons 1}
              {:pname "Mars" :moons 2}])


;; Then let's try to change planets...
(map (fn [planet]
       (assoc (dissoc planet :moons)
         :habitable? true))
     planets)


;; Predict...
(filter :habitable? planets)

planets ; what's the value?




;; On `def`:

;; DO NOT use `def` to _emulate_ mutation like this:

(def am-i-mutable? 3.141)

#_(def am-i-mutable? 42)

#_(def am-i-mutable? "LoL, No!")



;; What's the point of `def`, if we can't use it to mutate values?
;;
;; Well, remember functions are values?

(fn [x] x) ; is a value (which shall remain anonymous)


(defn same
  [x]
  x) ; `same` is the name of a function. Therefore `same` names a value.


;; So what if we...
(def same-same
  (fn [x] x)) ; hah!



;; And actually, `defn` is just a convenience wrapper over `def`,

(macroexpand '(defn same [x] x)) ; yes, it is



;; Lexical Scope, and Global Vars


(def x 42) ; `x` is a global "var"

(defn x+
  [y]
  (+ x 1)) ; this `x` refers to the global `x`

(x+ 1) ; will return 43
(x+ 9) ; will still return 43


(defn x++
  [x] ; this `x` is local to the scope of x++,
      ; and will "shadow" the global `x`
  (+ x 1))

(x++ 1) ; will return 2
(x++ 9) ; will return 10


(defn x+++
  [x] ; this `x` will shadow the global `x`
  (let [x 10] ; but this `x` is local to the let,
              ; and will shadow all "outer" x-es
    (+ x 1)))

(x+++ 1) ; will return 11
(x+++ 9)  ; will still return 11




;; EXERCISE:
;;
;; Reason about what is happening here:

(let [x 3.141]
  ((fn [x] x)  x))


;; How about this? What will happen here?

((fn [x] x)  x)


;; How about this?

(let [x x]
  ((fn [x] x)  x))


;; This?

((fn [x] x) (let [x x]
              x))

;; And finally, this?

((let [x 3.141]
   (fn [x] x))   x)



;; "Pure Functions"


;; This function is "pure"
;; - Why?
(defn add-one
  [x]
  (+ x 1))


;; This function is "impure"
;; - Why?
(defn add-one!
  [x]
  (println x)
  (+ x 1))
;; - What are some other examples of side-effects?


(add-one 1) ; adds one, but never changes the outside world

(add-one! 1) ; adds one, and also changes the outside world


;; Pure functions are drop-in replacements for each other.

(= 2
   ((fn [x] (+ x 1)) 1)
   (add-one 1)
   (inc 1))

;; Impure functions cannot be used as drop-in replacements for pure
;; functions, or for other impure functions for that matter.




;; Convenient Syntax for functions
;;
;; - We use these conveniences for good effect in API design.


;; Multiple arities
;; - When we know for sure we have to handle some known numbers
;;   of arguments.

(defn add-upto-three-nums
  ([] 0) ; identity of addition
  ([x] x)
  ([x y] (+ x y))
  ([x y z] (+ x y z)))

(add-upto-three-nums)
(add-upto-three-nums 1)
(add-upto-three-nums 1 2)
(add-upto-three-nums 1 2 3)
#_(add-upto-three-nums 1 2 3 4) ; will fail


;; Variable arity
;; - When we don't know in advance how many arguments we
;;   will have to handle, but we want to handle them all.

(defn add-any-numbers
  [& nums]
  (reduce + 0 nums))

(add-any-numbers)
(add-any-numbers 1)
(add-any-numbers 1 2)
(add-any-numbers 1 2 3 4 5)


;; Multiple _and_ Variable arities, combined
;; - Guess what + actually is inside?
;;
(clojure.repl/source +) ; evaluate, check the REPL/LightTable console

(+)
(+ 1)
(+ 1 2 3 4 5 6 7 8 9 0)



;; "De-structuring"
;; - For convenient access to items in collections.


;; Suppose a function expects a two-item sequence, we can...

(defn print-tuple-in-strange-ways
  [[a b]] ; expects a two-item vector
  (println "-----------------")
  (println b)
  (println a)
  (println "abba " [a b b a])
  (println "hash b a " {:b b :a a})
  (println b a a b a a " black sheep"))

(print-tuple-in-strange-ways [1 2])

;; It's like visually matching shapes to shapes.
;; - [1 2] ; structure 1 and 2 in a vector
;;    | |
;; - [a b] ; name by position, and use each named value however we wish


;; De-structuring works in `let` and functions:

(let [[k v] [:a 42]]
  {:a 42})

((fn [[k v]] {k v})  [:a 42])


;; We can mix-and match de-structuring, for great good.
;; Compare:

(reduce (fn [acc-map kv-pair]
          (assoc acc-map
                 (first kv-pair) (second kv-pair)))
        {:a 42}
        {:b 0 :c 7 :d 10})

(reduce (fn [acc-map [k v]]
          (assoc acc-map k v))
        {:a 42}
        {:b 0 :c 7 :d 10})


;; Vectors are ordered collections, which we de-structure by _position_.


;; Hash-maps are _unordered_ collections.
;; - BUT, they are keyed by named keys.
;; - We can exploit this "pattern" as follows:

;; Compare this:
(let [make-message (fn [planet]
                     (str "Planet " (:pname planet) " has "
                          (:moons planet) " moons."))]
  (make-message
   {:pname "Mars" :moons 2}))


;; With this...
(let [make-message (fn [{:keys [pname moons]}]
                     (str "Planet " pname " has "
                          moons " moons."))]
  (make-message
   {:pname "Mars" :moons 2}))


;; And we can further...
(let [make-message (fn [{:keys [pname moons]}]
                     (str "Planet " pname " has "
                          (or moons 0) " moons."))]
  (map make-message [{:pname "Earth" :moons 1}
                     {:pname "Mars"  :moons 2}
                     {:pname "Moonless"}]))


;; We can also alias the whole hash-map:

(defn add-message-1
  [{:keys [pname moons]
    :as   planet}]  ; alias the hash-map as `planet`
  (assoc planet
    :message (str "Planet " pname " has "
                  (or moons 0) " moons.")))

(map add-message-1 [{:pname "Earth" :moons 1}
                    {:pname "Mars" :moons 2}
                    {:pname "Moonless"}])


;; Finally, we can specify default values directly in the destructuring:

(defn add-message-2
  [{:keys [pname moons]
    :or   {moons 0} ; use 0, if :moons is absent
    :as   planet}]
  (assoc planet
    :message (str "Planet " pname " has "
                  moons " moons.")))

(map add-message-2 [{:pname "Earth" :moons 1}
                    {:pname "Mars" :moons 2}
                    {:pname "Moonless"}])


;; Further, we can exploit combinations of de-structuring
;;
;; - Suppose we have a hash map, keyed by planet names:
;;
{"Earth"    {:moons 1}
 "Mars"     {:moons 2}
 "Moonless" {}}
;;
;; - Recall: a hash-map is like a collection of key-value pairs/tuples
;;
;; - Now, we can exploit vector and map de-structuring, in combination:
;;

(defn add-message-3
  [acc-map [pname {:keys [moons]
                   :or {moons 0}
                   :as pdata}]]
  (let [msg (str "Planet " pname " has " moons " moons.")]
    (assoc acc-map
      pname (assoc pdata :message msg))))

(reduce add-message-3
        {} ; acc-map
        {"Earth"    {:moons 1}
         "Mars"     {:moons 2}
         "Moonless" {}
         "Nomoon"   nil})


;; There are _many_ many ways of de-structuring.
