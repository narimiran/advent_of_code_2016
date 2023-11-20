(ns day20
  (:require [aoc]))


(defn find-allowed [blocked]
  (rest
   (reduce
    (fn [[avail lowest cnt] [lo hi]]
      (let [new-avail (max avail (inc hi))]
        (if (> lo avail)
          [new-avail
           (if (nil? lowest) avail lowest)
           (+ cnt (- lo avail))]
          [new-avail
           lowest
           cnt])))
    [0 nil 0]
    blocked)))


(defn solve [input]
  (-> input
      (aoc/read-input #(aoc/integers % {:negative? false}))
      sort
      find-allowed))


(solve 20)
