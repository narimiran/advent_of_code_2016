(ns day01
  (:require aoc))


(defn parse-instruction [instr]
  (let [rot (first instr)
        amt (first (aoc/integers instr))]
    {:rot (case rot \L :left \R :right)
     :amt amt}))

(defn rotate [[x y] rot]
  (case rot
    :left  [y (- x)]
    :right [(- y) x]))

(defn step [state]
  (let [{:keys [pos dir hq seen]} state
        new-pos (aoc/pt+ pos dir)]
    {:pos new-pos
     :dir dir
     :hq (if (and (not hq) (seen new-pos))
           new-pos
           hq)
     :seen (conj seen new-pos)}))

(defn move [state amt]
  (-> (iterate step state)
      (nth amt)))

(defn traverse [instructions]
  (let [state {:pos [0 0]
               :dir [0 -1]
               :seen #{[0 0]}
               :hq false}]
    (reduce
     (fn [state {:keys [rot amt]}]
       (let [new-dir (rotate (:dir state) rot)]
         (-> state
             (assoc :dir new-dir)
             (move amt))))
     state
     instructions)))


(defn solve [input]
  (let [instructions (->> (aoc/read-input-line input :words)
                          (map parse-instruction))
        solution (traverse instructions)]
    [(aoc/manhattan (:pos solution))
     (aoc/manhattan (:hq solution))]))


(solve 1)
