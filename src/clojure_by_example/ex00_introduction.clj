(ns clojure-by-example.ex00-introduction)

;; Clojure is a "Lisp"
;; - Clojure code is composed of "expressions"


;; These "literal" values are Clojure "expressions"

"hello"                                 ; strings
:hello                                  ; keywords
'hello                                  ; symbols
42                                      ; numbers
22/7                                    ; fractional numbers


;; "Built-in" functions are also "expressions"

+                                       ; addition
map                                     ; map over a collection
filter                                  ; filter from a collection
reduce                                  ; transform a collection


;; Collection "literals" are expressions too:

[1 2 3 4 5]                             ; a vector
{:a 1 :b 2}                             ; a hash-map
#{1 2 3 4 5}                            ; a hash-set
'(1 2 3 4 5)                            ; a list


;; Any Clojure code that evaluates to a value is _also_ an "expression":

(+ 1 2)

(+ (+ 1 2) (+ 1 2))

(+ (+ (+ 1 2) (+ 1 2))
   (+ (+ 1 2) (+ 1 2)))


;; In fact, ALL Clojure code is just "expressions"
;; - And, all Clojure expressions evaluate to a value.


;; DEMO: Clojure expression syntax rules:
;;
;; - Literals:

;; - Collection Literals:

;; - "Code expressions":


;; Clojure "code expressions" are called "s-expressions", because:
(+ 1 2 3 4
 ) ; see, open paren + closed paren looks like an "S"?



;; DEMO: Clojure Code Evaluation Rules



;; DEMO: Why is Clojure a "List Processing" language?

'(+ 1 2) ; list of expressions

(+ 1 2)  ; executable s-expression


;; Here is a function definition.

(defn hie
  [person message]
  (str "Hie, " person " : " message))


;; What does it look like?
