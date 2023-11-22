(ns aoc-viz
  (:require [quil.core :as q]))


(defn scaled-square [x y scale]
  (q/rect (* scale x) (* scale y) scale scale))

(defn scaled-circle [x y scale]
  (q/ellipse (* scale x) (* scale y) scale scale))
