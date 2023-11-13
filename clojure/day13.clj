(ns day13
  (:require aoc))


(def input 1364)


(defn formula [x y]
  (+ (* x x)
     (* 3 x)
     (* 2 x y)
     y
     (* y y)
     input))


(defn add-nbs [queue seen]
  (let [{:keys [pos steps]} (peek queue)]
    (reduce
     (fn [queue [x y]]
       (if (not (or (seen [x y])
                    (neg? x)
                    (neg? y)
                    (odd? (aoc/count-if #{\1} (Integer/toBinaryString (formula x y))))))
         (conj queue {:pos [x y] :steps (inc steps)})
         queue))
     (pop queue)
     (aoc/neighbours pos 4))))


(defn run-through-maze [part]
  (loop [queue (conj clojure.lang.PersistentQueue/EMPTY {:pos [1 1] :steps 0})
         seen #{}]
    (let [{:keys [pos steps]} (peek queue)
          new-seen (conj seen pos)
          new-queue (add-nbs queue seen)]
      (cond
        (and (= part 1) (= pos [31 39])) steps
        (and (= part 2) (> steps 50)) (count seen)
        :else (recur new-queue new-seen)))))


(defn solve []
  (for [part [1 2]]
    (run-through-maze part)))


(solve)
