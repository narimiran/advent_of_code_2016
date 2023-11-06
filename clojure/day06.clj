(ns day06
  (:require aoc))


(defn sort-by-freq [letters]
  (->> letters
       frequencies
       (sort-by val >)
       (map key)))

(defn decrypt [f cols]
  (apply str (map f cols)))

(defn solve [input]
  (let [cols (->> input
                  aoc/read-input
                  aoc/transpose
                  (map sort-by-freq))]
    (for [method [first last]]
      (decrypt method cols))))


(solve 6)
