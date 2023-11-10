(ns day10
  (:require aoc
            [clojure.core.async :refer [<! >! <!! chan go]]
            [clojure.core.match :refer [match]]
            [clojure.string :as str]))


(def bots-nr 220)
(def outputs-nr 22)

(defn kw [& xs]
  (keyword (apply str xs)))


(def bots
  (into {}
        (for [n (range bots-nr)]
          (let [in (chan 2)
                lo (chan 1)
                hi (chan 1)]
            (go (let [a (<! in)
                      b (<! in)]
                  (when (and (#{17 61} a)
                             (#{17 61} b))
                    (println n))
                  (>! lo (min a b))
                  (>! hi (max a b))))
            {(kw "bot" n)
             {:in in
              :lo lo
              :hi hi}}))))

(def outputs
  (into {}
        (for [n (range outputs-nr)]
          {(kw "output" n)
           {:in (chan 1)}})))

(def factory (merge bots outputs))


(defn pass-to [boro n]
  ((factory (kw boro n)) :in))

(defn pass-from [n chan]
  ((factory (kw "bot" n)) chan))

(defn populate-factory [line]
  (match (str/split line #" ")
    ["value" v _ _ _ nr]
    (go (>! (pass-to "bot" nr) (parse-long v)))

    ["bot" b _ _ _ boro-lo lo _ _ _ boro-hi hi]
    (go (>! (pass-to boro-lo lo) (<! (pass-from b :lo)))
        (>! (pass-to boro-hi hi) (<! (pass-from b :hi))))))


(defn solve [filename]
  (let [input (aoc/read-input filename)]
    (doall (map populate-factory input)))
  (apply *
         (for [n (range 3)]
           (<!! ((factory (kw "output" n)) :in)))))


(solve 10)
