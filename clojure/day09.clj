(ns day09
  (:require aoc
            [clojure.core.match :refer [match]]))


(def pattern #"([A-Z]*)\((\d+)x(\d+)\)")


(defn find-pattern [compressed]
  (if-let [[patt pre* len* times*] (re-find pattern compressed)]
    (let [pre-len (count pre*)
          len (parse-long len*)
          times (parse-long times*)
          marker-len (count patt)]
      [pre-len len times marker-len])
    (count compressed)))


(defn decompress
  ([compressed bomb?]
   (decompress compressed 0 bomb?))

  ([compressed len-so-far bomb?]
   (match (find-pattern compressed)
     [pre-len len times marker-len]
     (let [remaining (drop marker-len compressed)
           [took left] (map #(apply str %) (split-at len remaining))
           decompressed-len (if bomb? (decompress took true) len)
           seen (+ len-so-far
                   pre-len
                   (* times decompressed-len))]
       (recur left seen bomb?))

     n
     (+ len-so-far n))))


(defn solve [input]
  (let [compressed (aoc/read-input-line input)]
    (for [bomb? [false true]]
      (decompress compressed bomb?))))


(solve 9)
