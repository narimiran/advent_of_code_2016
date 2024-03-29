(ns day04
  (:require aoc
            [clojure.string :as str]))


(defn valid-room? [[encrypted _ checksum]]
  (->> encrypted
       frequencies
       (sort-by (juxt (comp - val) key))
       (take 5)
       (map key)
       (apply str)
       (= checksum)))

(defn part-1 [rooms]
  (transduce
   (comp
    (filter valid-room?)
    (map second))
   +
   rooms))


(defn decrypt [[encrypted sector-id _]]
  (->> (map
          (fn [x]
            (-> (int x)
                (- 97)
                (+ sector-id)
                (mod 26)
                (+ 97)
                char))
          encrypted)
       (apply str)
       (conj [sector-id])))

(defn part-2 [rooms]
  (->> rooms
       (map decrypt)
       (aoc/find-first #(str/starts-with? (second %) "northpole"))
       first))


(defn parse-line [line]
  (let [[_ encrypted* id checksum] (re-find #"(\S+)-(\d+)\[(\w+)\]" line)
        encrypted (str/replace encrypted* "-" "")
        sector-id (parse-long id)]
    [encrypted sector-id checksum]))

(defn solve [input]
  (let [rooms (aoc/read-input input parse-line)]
    ((juxt part-1 part-2) rooms)))


(solve 4)
