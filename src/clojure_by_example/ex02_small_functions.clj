(ns clojure-by-example.ex02-small-functions)

;; First, some simple functions:


;; We define functions like this:
;;
;; (defn function-name
;;   "Documentation string (optional)."
;;   [arg1 arg2 arg3 ... argN]
;;   (body of the function))


(defn same
  "Simply return the input unchanged."
  [x]
  x)


;; EXERCISE
;;
;; What will these return?

(same 42)

(same {:name "Earth" :moons 1})

(same [1 2 3 4 5])


;; We can define name-less functions too, like this:
;;
;; (fn [arg1 arg2 arg3 ... argN]
;;     (body of the function))
;;
;; `fn` is short for "define a FuNction, but _do not_ name it at all"
;;
;; E.g. This behaves _exactly_ like `same`, but it does not have a name:

(fn [x] x)



;; EXERCISE:
;;
;; What will these return?
;; - Hint: _mentally replace_ the function definition `(fn [x] x)`
;;   with _any name you like_, and imagine that is the function name.

((fn [x] x) 42)

((fn [x] x) {:name "Earth" :moons 1})

((fn [x] x) [1 2 3 4 5])



;; Some "built-in" functions

;; - `fn?` is a function that returns true if we pass it a function


(fn? 42)

(fn? "moons")

(fn? [1 2 3 4 5])


;; We defined the function `same`, and truly, it is a function...
(fn? same)

;; Our "anonymous" function, is a function too. But of course!
(fn? (fn [x] x))

;; And `fn?` itself is a... ______ ?
(fn? fn?)


;; Suppose we pass `fn?` to `same`?
(= fn?
   (same fn?))


;; How about the anonymous version of `same`?
;; Does it return `fn?` unchanged as well?
(= 'FIX
   ((fn [x] x) fn?))

;; How about this?
;; What do we get if we pass `same` to itself?
(= 'FIX
   (same same))



;; Some other "built-in" Clojure functions

;; Return `true` if even, `false` otherwise
(even? 2)

;; Return `true` if odd, `false` otherwise
(odd? 3)

;; Increment by one
(inc 42)

;; Clojure's `identity` function is exactly like our `same` function
(= 'FIX
   (identity :moon)
   (same :moon))



;; `map`
;; - a function over a collection...

(map inc [1 2 3 4 5 6])

;; Such that...
'( 1 2 3 4 5 6)  ; each item of input
;; | | | | | |   ; is incremented by `inc` to give a result where
'( 2 3 4 5 6 7)  ; each output item "maps" back to an input item


;; EXERCISE:
;;
;; What should this return?
(map even? [1 2 3 4 5 6])



;; How about this?
(map odd? [1 2 3 4 5 6])


;; And this?
(map identity [1 2 3 4 5 6]) ; Recall: `identity` is just like `same`


;; And this?
(map (fn [x] x) [1 2 3 4 5 6])



;; Now for a little bit of fun with `map` and some planets.


;; This is a Clojure vector... of numbers

[1 2 3 4 5 6]


;; This is a Clojure vector... of Clojure hash-maps.

[{:name "Mercury" :moons 0}
 {:name "Venus"   :moons 0}
 {:name "Earth"   :moons 1}
 {:name "Mars"    :moons 2}]


;; Let's name our collection of planets as, um... `planets`

(def planets [{:name "Mercury" :moons 0}
              {:name "Venus"   :moons 0}
              {:name "Earth"   :moons 1}
              {:name "Mars"    :moons 2}])


;; Recall that we can query a hash-map, like this:

(:name {:name "Mercury" :moons 0})

;; That is, a keyword _behaves like a function_ of a hash-map.


;; EXERCISE
;;
;; What if we pass a keyword instead of a real function, to `map`?
;; - What should the following map expression return?

(map :name planets)


;; Stitching it all together...


;; Let's define another little function.

(defn planet-has-moons?
  "Given a 'planet' (assume it's a hash-map), return true if
  it has at least one moon."
  [planet]
  (> (:moons planet)
     0))

(planet-has-moons? {:name "Mercury" :moons 0})

(planet-has-moons? {:name "Earth" :moons 1})

(planet-has-moons? {:name "Mars" :moons 2})


;; EXERCISE
;;
;; Instead of querying each hash-map for moons, why not
;; query all of them at one go?

#_(map 'FIX 'FIX)


;; EXERCISE
;;
;; Replace 'FIX with your own anonymous function that works
;; just like `planet-has-moons?`.

#_(map 'FIX  planets)


;; EXERCISE
;;
;; And, finally, prove that both variants do exactly the same thing:

#_(= (map planet-has-moons? 'FIX)

     ('FIX 'FIX 'FIX)  ; use anonymous function

     [false false true true])
