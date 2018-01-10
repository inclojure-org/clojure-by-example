(ns clojure-by-example.ex03-data-and-functions)

;; Let's start building more sophisticated things...


;; Let's begin again, with our planets, our moons checker, and `map`
(def planets [{:name "Mercury" :moons 0  :mass 0.0533}
              {:name "Venus"   :moons 0  :mass 0.815}
              {:name "Earth"   :moons 1  :mass 1}
              {:name "Mars"    :moons 2  :mass 0.107}
              {:name "Jupiter" :moons 69 :mass 317.8}
              {:name "Saturn"  :moons 62 :mass 95.2}])


;; Recall: Our little "helper" function, to check if a planet has moons.
(defn planet-has-moons?
  "Given a 'planet' (assume it's a hash-map), return true if
  it has at least one moon."
  [planet]
  (> (:moons planet)
     0))

;; The following results make some sense:

(map :name planets) ; ok, we can now extract just the names of planets

(map :moons planets) ; ok, we can now count total number of moons


;; But what do we do with a bunch of boolean results?

(map planet-has-moons? planets)


;; `filter` out things:

;; EXERCISE:
;;
;; Predict the result of these expressions:

(filter planet-has-moons?
        planets)


(filter (fn [p] (not (planet-has-moons? p)))
        planets)


;; EXERCISE
;;
;; You can define new "helper" function, in terms of earlier helper
;; functions... Fix the function body, and make it work.

(defn planet-without-moon?
  "Planets without moons are exactly opposite to planets with moons."
  [FIX]
  FIX)


;; EXERCISE
;;
;; Now, filter out planets without moons...

#_(FIX FIX FIX)



;; Now, can we write a function to filter planets into two groups?
;; - planets with moons, and
;; - planets without moons?

;; EXERCISE:
;;
;; Will this work? Why? Take a guess... and then evaluate to check.

{:planets-with-moons    (filter planet-has-moons? planets)
 :planets-without-moons (filter planet-without-moon? planets)}


;; EXERCISE:
;;
;; So now, to group planets by moons, we can... do what?

(defn group-planets-by-moons
  [planets]
  ;; FIX: return a data structure here that represents the grouping.
  'FIX)


;; EXERCISE:
;;
;; Use `group-planets-by-moons` to group planets.
;; Write your solution below:



;; Try to When we called the function with `planets`?


;; Now we can further find...

(:planets-with-moons (group-planets-by-moons planets))



;; EXERCISE:
;;
;; Find names of those planets that have moons...

#_('FIX 'FIX 'FIX)



;; REDUCE!

;; Remember we can collect moons from planets?

(map :moons planets)


;; Now, how to "reduce" the collection into one number?

(reduce + 0 (map :moons planets))
;; It computes this: 0 + p1-moons + p2-moons + .... + pN-moons

;; Imagine each step of the above computation, like this:

;; =======================================
;; Accumulator | Input collection (of number of moons)
;; =======================================
;; 0 (start)   | [0 0 1 2]   ; just before first step
;; 0           | [0 1 2]     ; at end of first step
;; 0           | [1 2]
;; 1           | [2]
;; 3           | []          ; reduce detects empty collection
;; ---------------------------------------
;; 3 (return value)          ; reduce spits out the accumulator


;; `reductions` is a convenience function that helps us visualize
;; the "accumulator" at each step of the `reduce` computation:

(reductions + 0 (map :moons planets))



;; Lesson-end Exercises:

;; IMPORTANT:
;;
;; - Don't refer back to the code above unless you're _really_ stuck.
;;
;; - Try to reason from first principles - use the basic ideas we have
;;   acquired so far.
;;
;; - Write your own functions _from scratch_, if you need them.


;; EXERCISE:
;;
;; Calculate the total mass of all `planets`
;; Write your solution below:



;; EXERCISE:
;;
;; Count the number of `planets` that have moons.
;; - Reuse the `planet-has-moons?` function that we already defined.
;; - Use the `count` function to find the counts.

(count [42 43 44 45]) ; try this example

;; Write your solution below:



;; EXERCISE:
;;
;; Calculate the total mass of planets having moons.
;; - Reuse the `planet-has-moons?` function that we already defined.



;; EXERCISE:
;;
;; Calculate the total mass of planets _without_ moons.
;; - Reuse the `planet-without-moons?` function that we defined earlier.



;; EXERCISE:
;;
;; Write a function `massier-than-earth?` which,
;; - given a planet,
;; - returns true if the planet's mass exceeds Earth's mass
;; - returns false otherwise

(defn massier-than-earth?
  [FIX]
  FIX)


;; EXERCISE:
;;
;; Write a function `planetary-stats` that:
;;
;; Takes TWO arguments:
;; 1. a "predicate" function like `massier-than-earth?`
;; 2. a sequence of some planets,
;;
;; Returns the following stats about those input planets
;; that match the filter criteria:
;; - count of the planets
;; - names of the planets
;; - total mass of the planets
;;
;; Important:
;; - The return value must be, an easy-to-query data structure.

;; Uncomment and fix:
#_(defn planetary-stats
    [pred-fn given-planets]
    ;; `let` is a way to define ("bind") function-local variables.
    (let [filtered-planets 'FIX]
      {:count 'FIX
       'FIX1    'FIX
       'FIX2    'FIX}))


;; Now, calculate `planetary-stats` for:
;; - planets having moons:

#_('FIX 'FIX planets)


;; - and, for planets without moons:


;; - and again, for planets without moons, using `complement`:
;;   compare, understand, use:

(planet-has-moons?              {:name "Earth" :moons 1})

((complement planet-has-moons?) {:name "Earth" :moons 1})

;; Type your solution here:

#_(planetary-stats
   'FIX
   planets)



;; EXERCISE:
;;
;; Calculate `planetary-stats' for:
;;
;; - planets "massier" than the earth:



;; - and for planets less "massy" than the earth, using `comp`:
;;   compare, understand, use:
#_(massier-than-earth?            {:name "Jupiter" :mass 317.8})

#_((comp not massier-than-earth?) {:name "Jupiter" :mass 317.8})

;; Type your solution here:



;; EXERCISE:
;;
;; Calculate `planetary-stats' for:
;;
;; - all `planets` (hint: use `identity`):



;; EXERCISE:
;;
;; Write a function `more-planetary-stats` that:
;; - Takes a sequence of planets, and
;; - Returns an easy-to-query data structure containing
;;   the following stats:
;;
;; given planets: count, names, total mass
;; planets with moons: count, names, total mass
;;     count
;;     names
;;     total mass
;; planets without moons: count, names, total mass
;;     count
;;     names
;;     total mass
;; planets with more mass than earth: count, names, total mass
;;     count
;;     names
;;     total mass
;; planets having less mass than earth: count, names, total mass
;;     count
;;     names
;;     total mass


;; Write the `more-planetary-stats` function below:

(defn more-planetary-stats
  [FIX]
  {:given-planets FIX
   :with-moons FIX
   :without-moons FIX
   :massier-than-earth FIX
   :less-massy-than-earth FIX})


;; Check your results. Uncomment and evaluate:
(comment
  (more-planetary-stats planets)

  (more-planetary-stats (take 2 planets))

  (more-planetary-stats (drop 2 planets)))
