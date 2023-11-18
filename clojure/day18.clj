(ns day18
  (:require aoc))


(defn next-row [row]
  (into [(second row)]
        (map (fn [left right]
               (if (= left right) 1 0))
             row
             (drop 2 (conj row 1)))))

(defn make-tiles [puzzle iters]
  (first
   (reduce
    (fn [[cnt row] _]
      [(+ cnt (apply + row))
       (next-row row)])
    [0 puzzle]
    (range iters))))


(defn parse-char [c]
  (if (= c \.) 1 0))

(defn solve [input]
  (let [puzzle (->> (aoc/read-input-line input :chars)
                    (mapv parse-char))]
    (for [iters [40 400000]]
      (make-tiles puzzle iters))))


(solve 18)
