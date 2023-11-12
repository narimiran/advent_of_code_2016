(ns day12
  (:require aoc
            [clojure.string :as str]
            [clojure.core.match :refer [match]]))


(defn read-param [param]
  (if-let [p (and (some? param)
                  (parse-long param))]
    p
    (keyword param)))


(defn parse-line [line]
  (let [[a b c] (str/split line #" ")]
    [(keyword a) (read-param b) (read-param c)]))


(defn play-instructions [instructions c]
  (loop [i 0
         regs {:a 0 :b 0 :c c :d 0}]
    (match (get instructions i)
      nil (regs :a)
      [:cpy what where] (recur (inc i) (assoc regs where (or (regs what) what)))
      [:jnz val jump] (if (zero? (or (regs val) val))
                        (recur (inc i) regs)
                        (recur (+ i jump) regs))
      [:inc reg nil] (recur (inc i) (update regs reg inc))
      [:dec reg nil] (recur (inc i) (update regs reg dec)))))


(defn solve [input]
  (let [instructions (vec (aoc/read-input input parse-line))]
    (for [c (range 2)]
      (play-instructions instructions c))))


(solve 12)
