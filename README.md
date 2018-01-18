# Clojure By Example

What could one do with just _little_ bit of Clojure?

## Intended usage
  - Support a 1-day guided workshop for programmers new to Clojure (not absolute programming beginners).
  - Also function as at-home learning material for said programmers.
  - The `master` branch is heavily commented, for at-home use
  - A `solutions` branch will be available, as a companion to `master`.
    But don't peek at it in advance!
  - Ignore the `workshop-code` branch. It is only for workshop use,
    and subject to deletion/re-creation.

## Contributions
  - If you find bugs or errors, please send a PR (but please
    don't change the course structure or pedagogy).
 
## Workshop Goals
  - Acquire a "feel" of Clojure, for further self-study/exploration.
  - Learn how Clojurists usually think with Clojure to solve problems.
  - See how it's not so hard to do surprisingly powerful things with a
    mere handful of "primitive" functions, data structures, and ideas.

## Workshop Anti-Goals
  - Try to explain Functional Programming theory or Clojure's innards.
    (Many free and paid tutorials and books do so very well.)
  - Try to fully cover Clojure primitives/features. (That's homework!)
  - Devolve into language wars, editor wars, syntax wars, type wars...
    (Life's too short, people.)
  - Focus too much on tooling or operational things. (At least not 
    while there's fun to be had!) 


# Suggested learning mindset
  - Think of this as an exercise in "constrained creativity".
  - Ignore details, achieve much with as little know-how as possible.
  - Focus on what things do; not what they are, or why they are.
  - Inform your _intuition for doing things_, and then use that to 
    dive deeper into all the juicy details at your own pace, later.

Take what is useful, discard the rest.


# Setup Instructions

It's a liiitle bit of work. But not too bad. 

Just do the follwing one by one, and you should be fine.

## Java 8

You will need Java to work with this Clojure workshop content.

First, make sure you have Java 8. 

  - Run `java -version` in your terminal. 
  - If Java is not installed, please [download and install Java 8 from here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). 
  - Once you are done, `java -version` should show you a Java 1.8.x version. 

Notes:

  - If you have Java 9, that should be OK too.
  - The LightTable editor is known to break with Java 9. Use Java 8 instead.
  - We have not tested this project with Java 7.


## Leiningen

Follow [Leiningen setup instructions here](https://leiningen.org/).

Note:
  
  - [Boot](http://boot-clj.com/) should be fine too, but we have not tested this project with it.


## Code Editor

Set up an editor and figure out how to evaluate Clojure code with it.

If you aren't opinionated about your Editor, yet:

  - [This slick Atom setup will suffice for this workshop.](https://medium.com/@jacekschae/slick-clojure-editor-setup-with-atom-a3c1b528b722)

If you wish to use your favourite editor, you may find instructions
at one of these pages. But there are only so many choices. Ultimately,
you must pick your poison and run with it:

  - ["IDEs and Editors" at dev.clojure.org](https://dev.clojure.org/display/doc/IDEs+and+Editors)
  - ["Essentials" at clojure-doc.org](http://clojure-doc.org/articles/content.html#essentials)
  - [Christopher Bui says...](https://cb.codes/what-editor-ide-to-use-for-clojure/)


# Course Design Philosophy

Just some peoples' opinion. You need not be slave to it ;-)

Almost anyone can hope to do more with more. Up to a point, that is.

Far too often, we end up doing _less_ with more; bogged down by the
complexity and blinding glitter of too much choice, and overabundance.

Figuring out how to do more with less feeds our curiosity, and it often
satisfies and empowers us deeply.

So, may you stay small and achieve important things.

Live long, and prosper.
\\\\//_


# Credits
  - [clj-pune](https://github.com/clj-pune) people, especially [kapilreddy](https://github.com/kapilreddy), and [jaju](https://github.com/jaju) for critique while making ["pratham"](https://github.com/clj-pune/pratham), the precursor to this project.
  - [adityaathalye](https://github.com/adityaathalye), [jysandy](https://github.com/jysandy), and [kapilreddy](https://github.com/kapilreddy) for course design, code reviews, critique, commits, and being the core teaching staff at the first edition of this workshop at IN/Clojure 2018.
  - All the workshop participants, and the many Clojurists who generously donated their time to make it successful.
  - [inclojure-org](https://github.com/inclojure-org) for being the umbrella under which this work happened.

## Copyright and License

Copyright © 2017-2018 [IN/Clojure](http://inclojure.org/). 

Distributed under the [MIT license](https://github.com/inclojure-org/clojure-by-example/blob/master/LICENSE).
