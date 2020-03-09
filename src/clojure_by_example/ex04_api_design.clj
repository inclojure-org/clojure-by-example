(ns clojure-by-example.ex04-api-design
  (:require [clojure-by-example.data.planets :as p]))

;; EX04: Lesson Goals:
;; - We use these conveniences for good effect in API design.
;; - See how to allow the same function to support different arities,
;;   as well as a variable number of arguments
;; - See how to "de-structure" data (it's a powerful, flexible lookup mechanism)
;; - Leverage de-structuring to design a self-documenting function API


;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Multiple arities
;;
;; - When we know for sure that a function must handle more than
;;   one "arity". An "arity" is the number of arguments
;;
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


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
#_(clojure.repl/source +) ; evaluate, check the REPL
;;
;; See how + tries to implement each arity as a special case,
;; to compute results as optimally as possible? We can do
;; such things too, in functions we define.

(+)
(+ 1)
(+ 1 2 3 4 5 6 7 8 9 0)


;; We can also use multiple arities to define sane fallbacks.

;; EXERCISE
;; - Recall `lower-bound`, and `upper-bound` from ex03
;; - Refactor these to support more than one arity.

(def tolerances
  "Define low/high bounds of planetary characteristics we care about."
  {:co2                {:low 0.1,  :high 5.0}
   :gravity            {:low 0.1,  :high 2.0}
   :surface-temp-deg-c {:low -125, :high 60}})

(defn lower-bound
  [tolerance-key]
  (get-in tolerances [tolerance-key :low]))

(defn upper-bound
  [tolerance-key]
  (get-in tolerances [tolerance-key :high]))


;; Fix `lower-bound-v2`, to make this expression evaluate
;; to true. Pay close attention to what should go where.
(defn lower-bound-v2
  "Look up the lower bound for the given tolerance key, in the
  given map of `tolerances`. Use a globally-defined `tolerances`
  map as a sane default if only tolerance-key is passed in."
  ([tolerance-key]
   (get-in tolerances
           [tolerance-key :low]))
  ([FIX1 FIX2]
   'FIX))

#_(= (lower-bound    :co2)
     (lower-bound-v2 :co2)
     (lower-bound-v2 :co2 tolerances)
     (lower-bound-v2 :co2 {:co2 {:low 0.1}}))


(comment
  ;; BONUS EXERCISE
  ;; Do the same for `upper-bound-v2`
  #_(defn upper-bound-v2
      'FIX
      'FIX)

  #_(= (upper-bound    :co2)
       (upper-bound-v2 :co2)
       (upper-bound-v2 :co2 tolerances)
       (upper-bound-v2 :co2 {:co2 {:high 5.0}}))
  )


;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; A tiny bit of "De-structuring"
;; - For convenient access to items in collections.
;;
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(comment
  ;; If we _shape_ our domain information as a data "structure",
  ;; can we use our knowledge of the shape to pull it apart;
  ;; i.e. "destructure" it?

  ;; Yes.

  ;; We use destructuring:
  ;; - In `let` bindings, to cleanly reach into data
  ;; - In function arguments, to make the API clean and expressive
  ;;
  ;; Here are a couple of commonly-used ways to do it.
  )

;; "Positional" De-structuring
;;
;; - Pull apart sequential, ordered data structures like
;;   lists, vectors, and any other sequence with linear access
;;
;; - Follow the structure of the collection, and mechanically
;;   bind values to symbols by position.


;; Evaluate and see what happens. Then form a theory of what might be going on.
(def planet-names
  (map :pname p/target-planets))


(let [[pname1 pname2] planet-names]
  (str pname1 " is the 1st planet, and "
       pname2 " is the 2nd planet."))


(let [[:as pnames]  planet-names]
  pnames)


(let [[m v e :as pnames] (map :pname p/target-planets)]
  {:useless-trivia (str e " is the third rock from the Sun.")
   :planets-names pnames})


;; "Associative" De-structuring
;;
;; - Syntax to reach into associative data structures
;;   (having key-value semantics), in arbitrary ways.
;;
;; -  Note: Clojure "Records" and vectors are associative too
;;
;; - Follow the structure of the collection, and mechanically
;;   bind values to symbols by key name.

;; Evaluate one by one and see what happens.
;; Then form a theory of what might be going on.


(let [[mercury] p/target-planets]
  (str (:pname mercury) " has mass " (:mass mercury)))


(let [[{:keys [pname mass]}] p/target-planets]
  (str pname " has mass " mass))


(let [[{:keys [pname mass] :as mercury}] p/target-planets]
  (assoc mercury
         :useless-trivia (str pname " has mass " mass)))



;; And, putting it all together, a function with a
;; more self-documented API:

(defn add-useless-trivia
  "Be Captain Obvious. Given a planet, add some self-evident trivia to it."
  [{:keys [pname mass] :as planet}]
  (assoc planet
         :useless-trivia (str pname " has mass " mass)))


(map add-useless-trivia
     p/target-planets)

#_(clojure.repl/doc add-useless-trivia)


;; EXERCISE:
;; Review the de-structured argument list in `add-useless-trivia`;
;; then try to recall and reinforce a key concept.
;; Hints:
;; - Relate it to our preferred way to model the world.
;; - What is the "world" here?
;; - What are we using to model/describe what property of what?
;;   (Yes, that's three 'what's :-)


;; EXERCISE:
;;
;; - Use de-structuring to refactor the following functions
;;   that we have copied over from ex03.
;;
;; - Develop a preliminary opinion about where and when
;;   it might makes sense to de-structure, and where and when
;;   it might not.


(defn atmosphere-present?
  [planet]
  (not (empty? (:atmosphere planet))))


(defn atmosphere-present?-refactored
  [FIXME]
  FIXME)

#_(= (map :pname
          (filter atmosphere-present? p/target-planets))
     (map :pname
          (filter atmosphere-present?-refactored
                  p/target-planets)))

(defn co2-tolerable?
  [planet]
  (let [co2 (get-in planet
                    [:atmosphere :carbon-dioxide])]
    (when co2
      (<= (lower-bound :co2)
          co2
          (upper-bound :co2)))))


(defn co2-tolerable?-refactored
  [FIXME]
  FIXME)

#_(= (map :pname
          (filter co2-tolerable? p/target-planets))
     (map :pname
          (filter co2-tolerable?-refactored
                  p/target-planets)))


;; EXERCISE:
;; - Fix the body of the refactored function
;; - Carefully review the function APIs, and develop a preliminary
;;   opinion whether the refactored version is better than the original.


(defn surface-temp-tolerable?
  [planet]
  (let [temp (:surface-temp-deg-c planet)
        low  (:low temp)
        high (:high temp)]
    (when (and low high)
      (<= (lower-bound :surface-temp-deg-c)
          low
          high
          (upper-bound :surface-temp-deg-c)))))


(defn surface-temp-tolerable?-refactored
  [{:keys [FIXME] :as planet}]
  FIXME)


(defn surface-temp-tolerable?-refactored-v2
  [{{:keys [low high]} :surface-temp-deg-c
    :as planet}]
  'FIXME)


#_(= (map :pname
          (filter surface-temp-tolerable?
                  p/target-planets))
     (map :pname
          (filter surface-temp-tolerable?-refactored
                  p/target-planets))
     (map :pname
          (filter surface-temp-tolerable?-refactored-v2
                  p/target-planets)))


;; RECAP:
;;
;; - We model the world by composing data structures and then use
;;   "de-structuring" to conveniently reach into those structures.
;; - We can design function apis to accept more than one arity,
;;   and then define custom logic for each arity.
;; - When, where, and how much to de-structure is a matter of
;;   taste; a design choice. There is no One True Way.
;; - There are _many_ many ways of de-structuring.
;;   Here's a really nice post detailing it:
;;   cf. http://blog.jayfields.com/2010/07/clojure-destructuring.html

;; 4clojure Drills: Problems you could try now.
;;
;; - #protip: Write the solutions as proper named functions in your code base,
;;   without code-golfing or hacks. Then translate to anonymous function form
;;   that 4clojure requires.
;;
(comment
  (map (fn [problem-no] (str "http://www.4clojure.com/problem/"
                             problem-no))
       [35, 36, 68, 145, 52,
        156, 22, 23, 32, 30,
        34, 28, 33, 40, 83,
        61, 99, 120, 56, 55,
        43, 67, 74, 80, 69, 75]))
