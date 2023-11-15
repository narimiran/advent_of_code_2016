(ns day15
  (:require aoc))


(defn parse-line [line]
  (let [[nr tot _ pos] (aoc/integers line)]
    [nr tot pos]))


(defn falls-through? [discs time]
  (every?
   (fn [[nr tot pos]]
     (zero? (mod (+ pos time nr) tot)))
   discs))


(defn find-time [discs]
  (loop [time 0]
    (if (falls-through? discs time)
      time
      (recur (inc time)))))


(defn solve [input]
  (let [discs (aoc/read-input input parse-line)]
    [(find-time discs)
     (find-time (conj discs [7 11 0]))]))


(solve 15)
