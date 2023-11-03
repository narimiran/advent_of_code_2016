(ns day03
  (:require aoc))


(defn to-cols [rows]
  (->> rows
       aoc/transpose
       flatten
       (partition 3)))

(defn is-triangle? [sides]
  (let [[a b c] (sort sides)]
    (> (+ a b) c)))

(defn solve [input]
  (let [rows (aoc/read-input input :ints)
        cols (to-cols rows)]
    (for [sides [rows cols]]
      (aoc/count-if is-triangle? sides))))


(solve 3)
