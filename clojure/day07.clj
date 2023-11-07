(ns day07
  (:require aoc
            [clojure.string :as str]))


(defn extract [f addr]
  (->> addr
       (keep-indexed #(when (f %1) %2))
       (interpose "|")
       (apply str)))

(defn parse-line [line]
  (let [address (str/split line #"\[|\]")
        supernet (extract even? address)
        hypernet (extract odd? address)]
    [supernet hypernet]))


(defn is-abba? [addr]
  (let [[a b c d] addr]
    (cond
      (nil? d) false
      (and (= a d)
           (= b c)
           (not= a b)) true
      :else (recur (rest addr)))))

(defn is-aba-bab? [[sup hyp]]
  (loop [sup sup]
    (let [[a b c] sup]
      (cond
        (nil? c) false
        (and (= a c)
             (not= a b)
             (str/includes? hyp (str b a b))) true
        :else (recur (rest sup))))))


(defn p1 [[sup hyp]]
  (and (is-abba? sup)
       (not (is-abba? hyp))))

(def p2 is-aba-bab?)

(defn solve [input]
  (let [lines (aoc/read-input input parse-line)]
    (for [part [p1 p2]]
      (aoc/count-if part lines))))


(solve 7)
