(ns clojure-by-example.ex04-control-flow)


;; The logical base for logic:

;; Boolean

(true? true)  ; `true` is boolean true

(true? false) ; `false` is boolean false


;; Falsey

;; `nil` is the only non-boolean "falsey" value


;; Truthy
;; - basically any non-nil value is truthy

42    ; truthy
:a    ; truthy
"foo" ; truthy
[7 3] ; truthy


;; Truthy/Falsey are NOT Boolean

(true? 42)   ; Is Truthy 42 a boolean true?

(false? nil) ; Is Falsey nil a boolean false?


;; Truthy/Falsey can be cast to boolean true/false

(boolean nil) ; coerce nil to `false`

(map boolean
     [42 :a "foo" [1 2 3 4]]) ; coerce non-nils to `true`



;; Clojure control structures understand truthy and falsey values too:

;; `if` treats `false` and `nil` as falsey, everything else as truthy

(if false   ; if     condition
  :hello    ; "then-expression"
  :bye-bye) ; "else-expression"


(if nil :hello :bye-bye)


;; Truthy:

(if true  :hello :bye-bye)

(if "Oi"  :hello :bye-bye)

(if 42    :hello :bye-bye)

(if [1 2] :hello :bye-bye)



;; `when` is half an `if`, and always returns `nil` for falsey condition

(when 42
  :hello)

(when false :bye-bye)

(when nil :bye-bye)

(when (nil? nil) :bye-bye)


;; MENTAL EXERCISES
;;
;; Mental exercises to develop your intuition for how we use
;; "proper" booleans as well as truthy/falsey-ness.


;; EXERCISE:
;;
;; Predict what will happen...

(map (fn [x] (if x :hi :bye))
     [1 2 nil 4 5 nil 7 8])



;; EXERCISE:
;;
;; Predict what will happen...

(reduce (fn [acc x] (if x (inc acc) acc))
        0 ; initial accumulator
        [1 2 nil 4 5 nil 7 8])


;; EXERCISE:
;;
;; Predict and compare the result of these two...

(filter nil?     [1 2 nil 4 5 nil 7 8])

(filter false?   [1 2 nil 4 5 nil 7 8])


;; EXERCISE:
;;
;; Predict and compare these three...

(map    identity
        [1 2 nil 4 5 nil 7 8])

(filter (comp not nil?) ; recall: `comp` composes functions into a pipeline
        [1 2 nil 4 5 nil 7 8])

(filter identity
        [1 2 nil 4 5 nil 7 8]) ;; Ha! What happened here?!


;; INTERLUDE...
;;
;; The logic and ill-logic of `nil` in Clojure


;; Good - `filter` knows `nil` is falsey

(filter identity
        [1 2 nil 4 5 nil 7 8])


;; Evil - `even?` cannot handle nothing... so, this fails:

#_(filter even?
        [1 2 nil 4 5 nil 7 8])


;; So... Guard functions like `even?` against the evil of nil

(filter (fn [x] (when x (even? x)))
        [1 2 nil 4 5 nil 7 8])


;; `fnil` is also handy, to "patch" nil input to a function

;; We might use it as a guard for `even?` like this:
((fnil even? 1) nil) ; pass 1 to `even?`, instead of nil
((fnil even? 2) nil) ; pass 2 to `even?`, instead of nil

;; Since we want `nil` to be non-even, we can nil-patch `even?` as:
(filter (fnil even? 1)
        [1 2 nil 4 5 nil 7 8])


;; DEMO
;; - How might someone use `nil` to advantage?


(def planets [{:name "Venus" :moons 0}
              {:name "Mars" :moons 2}
              {:name "Jupiter" :moons 69}])

;; Using `when` ... we might design a function:

(defn moon-or-nothing
  [planet]
  ;; Recall: we can "let-bind" local variables
  (let [num-moons (:moons planet)]
    (when (> num-moons 0)
      {:sent-rockets num-moons
       :to-moons-of (:name planet)})))

(moon-or-nothing {:name "Venus" :moons 0})


;; Later, someone may ask us...
(defn good-heavens-what-did-you-do?
  [rocket-info]
  (if rocket-info ; we will treat rocket-info as truthy/falsey
    ;; do/return this if true...
    (format "I sent %d rockets to the moons of %s! Traa la laaa..."
            (:sent-rockets rocket-info)
            (:to-moons-of rocket-info))
    ;; do/return this if false...
    "Nothing."))


;; And we will answer...
(map good-heavens-what-did-you-do?
     (map moon-or-nothing planets))



;; But suppose, using `if` ... we design a function:

(defn moon-or-bust [planet]
  (let [num-moons (:moons planet)]
    (if (> num-moons 0)
      {:sent-rockets num-moons
       :to-moons-of (:name planet)}
      "Bust!")))


;; And later, somebody wants to know from us...

#_(defn good-heavens-what-did-you-do-again???
     [rocket-info]
   ;; Fix to ensure the same output as we produced earlier.
   (if 'FIX
     'FIX
     'FIX))


;; We should be able to provide the same answers as before...

#_(map good-heavens-what-did-you-do-again???
     (map moon-or-bust planets))



;; `case` and `cond`
;; - are also available to do branching logic:

(map (fn [num-moons]
       (cond
         (nil? num-moons) "Do nothing!"
         (zero? num-moons)   "Send zero rockets."
         (= num-moons 1)   "Send a rocket."
         :else (str "Send " num-moons " rockets!")))

     [nil 0 1 42])


(map (fn [num-moons]
       (case num-moons
         nil "Do nothing!"
         0   "Send zero rockets."
         1   "Send a rocket."
         (str "Send " num-moons " rockets!"))) ; default expression

     [nil 0 1 42])



;; Clojure hash-sets can be used as predicates!

(= #{:a :b :c}  ; A hash-set of three things, :a, :b, and :c.
   (hash-set :a :b :b :c :a :c :c :c))


;; A hash-set behaves like a function to test for set membership.

(#{:a :b :c} :a) ; Does the set contain :a? Truthy.

(#{:a :b :c} :z) ; Does the set contain :z? Falsey.


;; How do Clojure programmers use sets as predicates?

(def colonize-it? #{"Earth" "Mars"})


((comp colonize-it? :name) {:name "Earth"})


((comp colonize-it? :name) {:name "Venus"})


(filter (comp colonize-it? :name)
        [{:name "Mercury"}
         {:name "Venus"}
         {:name "Earth"}
         {:name "Mars"}
         {:name "Jupiter"}])



;; Lesson-end exercise


;; LET'S COLONIZE PLANETS!!!!
;; \\//_


(def target-planets
  [{:name "Mercury"
    :mass 0.055 :radius 0.383 :moons 0
    :atmosphere {}} ; empty hash map means no atmosphere

   {:name "Venus"
    :mass 0.815 :radius 0.949 :moons 0
    :atmosphere {:carbon-dioxide 96.45 :nitrogen 3.45
                 :sulphur-dioxide 0.015 :traces 0.095}}

   {:name "Earth" :mass 1 :radius 1 :moons 1
    :atmosphere {:nitrogen 78.08 :oxygen 20.95 :carbon-dioxide 0.4
                 :water-vapour 0.10 :argon 0.33 :traces 0.14}}

   {:name "Mars" :mass 0.107 :radius 0.532 :moons 2
    :atmosphere {:carbon-dioxide 95.97 :argon 1.93 :nitrogen 1.89
                 :oxygen 0.146 :carbon-monoxide 0.056 :traces 0.008}}

   {:name "Chlorine Planet"
    :mass 2.5 :radius 1.3 :moons 4
    :atmosphere {:chlorine 100.0}}

   {:name "Insane Planet"
    :mass 4.2 :radius 1.42 :moons 42
    :atmosphere {:sulphur-dioxide 80.0 :carbon-monoxide 10.0
                 :chlorine 5.0 :nitrogen 5.0}}])


;; EXERCISE:
;;
;; Define a set of `poison-gases`
;; - Let's say :chlorine, :sulphur-dioxide, :carbon-monoxide are poisons

(def poison-gases 'FIX)

;; Is the gas poisonous?
#_(poison-gases :oxygen)
#_(poison-gases :chlorine)


;; EXERCISE:
;;
;; Write a "predicate" function to check if a given planet is "Earth".
;;

(defn earth?
  [planet]
  'FIX)


;; EXERCISE:
;;
;; Write a predicate function to check if a planet has
;; at least 0.1% :carbon-dioxide in its atmosphere.

(defn carbon-dioxide?
  [planet]
  'FIX)


#_(map :name
     (filter carbon-dioxide? target-planets))


;; EXERCISE:
;;
;; Having no atmosphere is a bad thing, you know.
;;
;; Write a "predicate" function that returns truthy
;; if a planet has no atmosphere. It should return falsey
;; if the planet _has_ an atmosphere.
;;
;; Call it `no-atmosphere?`
;;
;; Use `empty?` to check if the value of :atmosphere is empty.
(empty? {}) ; is true. It's an empty hash-map.
;;
;; Type your solution below

(defn no-atmosphere?
  [planet]
  'FIX)


;; Quick-n-dirty test
#_(filter no-atmosphere? target-planets)



;; EXERCISE:
;;
;; Let's say the air is too poisonous if the atmosphere contains
;; over 1.0 percent of _any_ poison gas.
;;
;; Write a "predicate" function that checks this, given a planet.
;;
;; Call it `air-too-poisonous?`.
;;
;; Use the following ideas:
;;
;; - The `poison-gases` set we defined previously can be used
;;   as a truthy/falsey predicate:
#_(poison-gases :oxygen)
#_(poison-gases :chlorine)
;;
;; - A hash-map is a _collection_ of key-value _pairs_ / "kv" tuples.
(map identity {:a 1 :b 2 :c 3}) ; see?!
;;
;; - Given a `kv` pair from a hash-map, (first kv) will always be
;;   the key part, and (second kv) will always be the value part.
(map first    {:a 1 :b 2 :c 3})
(map second   {:a 1 :b 2 :c 3})
;;
;; - So, we can do something like this:
(filter (fn [kv]
          (when (#{:a :b :c} (first kv))
            (even? (second kv))))
        {:a 1 :b 2 :c 4 :d 8})
;;
;; - Finally, recall that we can test for empty? collections:
(empty? [])
(not (empty? []))
;;
;;
;; Now, combine these ideas to fix the function below:

#_(defn air-too-poisonous?
    [planet]
    (let [atmosphere 'FIX]
      (not ('FIX (filter (fn [kv] (when ('FIX (first kv))
                                    (> 'FIX 1.0)))
                         atmosphere)))))


;; Quick-n-dirty test
#_(map :name
       (filter air-too-poisonous? target-planets))



;; EXERCISE:
;;
;; Understand the next few functions.


(defn planet-has-some-good-conds?
  "Does a given planet satisfy at least one 'good condition'?"
  [good-condition-fns planet]
  (some (fn [good?] (good? planet))
        good-condition-fns))

;; Quick-n-dirty test:
;; - Let's say it's good to be Earth (yay), and
;; - It's good to have carbon dioxide in the atmosphere.

#_(filter (fn [planet]
            (planet-has-some-good-conds?
              [earth? carbon-dioxide?]
              planet))
          target-planets)

;; OR we could use `partial`:

#_(filter (partial planet-has-some-good-conds?
                   [earth? carbon-dioxide?])
          target-planets)

;; What does `partial` do?

#_(clojure.repl/doc partial)

;; Then fix these to make them work:

#_((partial (fn [a b c] (+ a b c))
            1)
   'FIX 'FIX)

#_((partial (fn [a b c] (+ a b c))
            1 2)
   'FIX)


;; EXERCISE:
;;
;; Given a collection of bad conditions, and a planet, return true if
;; the planet has _no_ bad conditions.

(defn planet-has-no-bad-conds?
  [bad-condition-fns planet]
  ('FIX (fn [bad?] 'FIX)
   bad-condition-fns))


;; Quick-n-dirty test:
#_(filter (partial planet-has-no-bad-conds?
                   [air-too-poisonous? no-atmosphere?])
          target-planets)

#_(filter (complement ; Aha! Remember `complement`?
            (partial planet-has-no-bad-conds?
                     [air-too-poisonous? no-atmosphere?]))
          target-planets)



;; EXERCISE:
;;
;; Let's say that a habitable planet has some good conditions,
;; and NO bad conditions.
;;
;; Define a function that checks true/truthy for this, given
;; good-condition-fns, bad-condition-fns, and a planet.
;;
;; Re-use:
;; - `planet-has-some-good-conds?`, and
;; - `planet-has-no-bad-conds?`

(defn habitable-planet?
  [good-condition-fns FIX1 FIX2] ; <- Fix args
  'FIX)


;; DEMO:
;;
;; Understand this:
;;
;; And finally, we write a function that groups a given collection of
;; planets into :habitable, and :inhospitable.
;;
;; The function must internally know what functions will check for
;; for good conditions, what functions check for bad conditions.
;;
;; - Assume it's good to be earth, OR to have carbon-dioxide in the air.
;; - Assume it's bad to have no atmosphere, or to have poison gases.
;;

#_(defn group-by-habitable
    [planet]
    (let [habitable? (partial habitable-planet?
                              [earth? carbon-dioxide?]
                              [air-too-poisonous? no-atmosphere?])]
      {:habitable (filter habitable?
                          target-planets)
       :inhospitable (filter (complement habitable?)
                             target-planets)}))


;; Quick-n-dirty test:
#_(defn colonize-habitable-planets!
    [planets]
    (let [send-rockets! (fn [p]
                          (str "Send rockets to " (:name p) " now!"))]
      ((comp (partial map send-rockets!)
             :habitable
             group-by-habitable)
       planets)))


#_(colonize-habitable-planets! target-planets)
