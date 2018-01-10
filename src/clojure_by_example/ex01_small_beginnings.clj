(ns clojure-by-example.ex01-small-beginnings)

;; Our Earth

;; "name"   "Earth"
;; "mass"   1 ; if Earth mass is 1, Jupiter's mass is 317.8 x Earth
;; "radius" 1 ; if Earth radius is 1, Jupiter's radius is 11.21 x Earth
;; "moons"  1
;; "atmosphere" "nitrogen"     78.08
;;              "oxygen"       20.95
;;              "CO2"           0.40
;;              "water-vapour"  0.10
;;              "other-gases"   "argon"  0.33
;;                              "traces" 0.14




;; Our Earth as a Clojure "hash-map":

{"name" "Earth"
 "mass" 1
 "radius" 1
 "moons" 1
 "atmosphere" {"nitrogen"     78.08
               "oxygen"       20.95
               "CO2"           0.40
               "water-vapour"  0.10
               "other-gases"  {"argon"  0.33
                               "traces" 0.14}}}



;; Globally reference the hash-map, and call it `earth`:

(def earth
  {"name" "Earth"
   "mass" 1
   "radius" 1
   "moons" 1
   "atmosphere" {"nitrogen"     78.08
                 "oxygen"       20.95
                 "CO2"           0.40
                 "water-vapour"  0.10
                 "other-gases"   {"argon"  0.33
                                  "traces" 0.14}}})


;; _Now_ let's query the 'earth' global...

;; Top-level access:

(get earth "name")

;; EXERCISE:
;;
;; How to get number of moons?
;; - Uncomment, and fix the expression below:

#_(get earth 'FIX)


;; EXERCISE:
;;
;; What does the atmosphere contain?
;; - Type your expression below:

#_('F 'I 'X)


;; Nested access:

;; EXERCISE:
;;
;; Now, how to find what the other gases are, in the atmosphere?

#_(get ('FIX 'FIX 'FIX) "other-gases")


;; EXERCISE:
;;
;; Now, try to go even deeper, to find how much argon we have?

;; (get 'FIX "argon")



;; A Simple "Function", to access any "third" level value...

(defn get-level-3 ; function name
  [planet level1-key level2-key level3-key] ; arguments list
  ;; function "body":
  (get (get (get planet level1-key)
            level2-key)
       level3-key)) ; Compare with the previous expression.

;; Now we can...
(get-level-3 earth "atmosphere" "other-gases" "argon")
(get-level-3 earth "atmosphere" "other-gases" "traces")



;; Keywords as Keys of Hash-maps

"moons" ; a string

:moons  ; a keyword



;; Defining an alternative hash-map, with keywords as keys:

(def earth-alt {:name "Earth"
                :mass 1
                :radius 1
                :moons 1
                :atmosphere {:nitrogen 78.08
                             :oxygen 20.95
                             :CO2 0.4
                             :water-vapour 0.10
                             :other-gases {:argon 0.33
                                           :traces 0.14}}})

;; Easier top-level access

;; EXERCISE:
;;
;; What will these return?

(:name earth-alt)

(:mass earth-alt)


;; EXERCISE:
;;
;; How to find the atmosphere? Uncomment,fix, and evaluate:

#_('FIX earth-alt)


;; EXERCISE:
;;
;; What are the other gases, in the atmosphere?
;; Hint: Remember, we can nest expressions inside expressions.

#_('FIX 'FIX)


;; EXERCISE:
;;
;; How much argon is present in the atmosphere?
;; Hint: once again, more nested expressions.

#_('FIX 'FIX)



;; Clojure provides `get-in` for nested access

(get-in earth-alt [:atmosphere])
(get-in earth-alt [:atmosphere :other-gases])
(get-in earth-alt [:atmosphere :other-gases :argon])
;;                '--> imagine this as a "path" to the value


;; By the way, the function we defined earlier, is general enough
;; to work with keywords too!
(get-level-3 earth-alt :atmosphere :other-gases :argon)


;; EXERCISE:
;;
;; Does `get-in` work for strings too?

#_(get-in earth 'FIX)



;; EXERCISE:
;;
;; Use `get-in` to query other gases from the `earth` hash-map.



;; EXERCISE:
;;
;; Use `get-in` to query argon from `earth`'s atmosphere




;; Clojure "Vectors" (indexed collections, like Arrays):

["atmosphere"] ; is a vector of one string

[:atmosphere :other-gases]  ; is a vector of two keywords


;; EXERCISE:
;;
;; What will this return?

(get [:foo :bar :baz] 0)


;; EXERCISE:
;;
;; What will this return?

(get-in [:foo [:bar :baz]] [1 1])


;; Same as:

(nth [:foo :bar :baz] 0)

(nth (nth [:foo [:bar :baz]] 1)
     1)



;; DEMO: Basic Data Modeling:


;; What if we model the earth as a vector, instead of a hash-map?
(def earth-as-a-vector
  "Docstring: This vector represents Earth this way:
  [Name     Radius  Mass  Moons]"
  ["Earth"  1       1     1])


;; Now, how do we query Earth?


(defn get-earth-name [] ; zero arguments means we don't need to pass it anything
  (get earth-as-a-vector 0))

(get-earth-name)


(defn get-earth-radius []
  (get earth-as-a-vector 1))

(get-earth-radius)


(defn get-earth-moons []
  (get earth-as-a-vector 2)) ; Uh, was it 2 or 3?

(get-earth-moons) ; did this return Mass, or Moons?


;; Compare with:
(:moons earth-alt)


;; What lessons do we glean from the above exercises?



;; Lesson-end Exercises:

;; EXERCISE:
;;
;; Define another planet `mercury`, using keywords as keys.
;; Use the information below.
;;
#_(;; FIXME
   :name "Mercury"    ; has...
   :moons 0
   :mass 0.0553  ; recall we assume Earth mass is 1
   :radius 0.383 ; recall we assume Earth radius is 1
   :atmosphere   ; % of total volume
      :oxygen      42.0
      :sodium      29.0
      :hydrogen    22.0
      :helium      6.0
      :potassium   0.5
      :other-gases 0.5)



;; EXERCISE:
;;
;; Query the planet `mercury` in 3 ways:
;; - with nested `get`


;; - with get-in


;; - with keywords



;; EXERCISE:
;;
;; Write a custom function to do a two-level deep query on `mercury`.
;; - It should be able to query earth, and earth-alt as well.

#_(defn get-level-2
    ['FIX ...]
    'FIX)
