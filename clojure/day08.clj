(ns day08
  (:require aoc
            [clojure.string :as str]
            [clojure.core.match :refer [match]]))


(def W 50)
(def H 6)


(defn parse-line [line]
  (let [words (str/split line #"\s|=|x")]
    (match words
      ["rect" a b] [:rect (parse-long a) (parse-long b)]
      [_ "row" _ r _ by] [:row (parse-long r) (parse-long by)]
      [_ "column" _ _ c _ by] [:col (parse-long c) (parse-long by)])))


(defn rot [screen n by]
  (let [row (nth screen n)]
    (concat (take n screen)
            [(concat (take-last by row)
                     (drop-last by row))]
            (drop (inc n) screen))))

(defn rotate [screen axis n by]
  (case axis
    :row (rot screen n by)
    :col (-> screen
             aoc/transpose
             (rot n by)
             aoc/transpose)))

(defn create [screen x y]
  (for [[yy row] (zipmap (range) screen)]
    (if (< yy y)
      (concat (repeat x "#") (drop x row))
      row)))


(defn put-pixels [screen instructions]
  (reduce
   (fn [screen [command a b]]
     (case command
       :rect (create screen a b)
       (rotate screen command a b)))
   screen
   instructions))

(defn print-screen [screen]
  (map #(apply str %) screen))


(defn solve [input]
  (let [instructions (aoc/read-input input parse-line)
        screen (repeat H (repeat W " "))
        lit-screen (put-pixels screen instructions)]
    [(aoc/count-if #{"#"} (flatten lit-screen))
     (print-screen lit-screen)]))


(solve 8)
