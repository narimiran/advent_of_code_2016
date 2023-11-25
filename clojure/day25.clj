(ns day25
  (:require aoc
            day12
            [clojure.core.match :refer [match]]))


(defn run-instruction [{:keys [instrs len line regs] :as state}]
  (if-not (< -1 line len)
    (assoc state :done? true)
    (match (instrs line)
      [:cpy what where]
      (cond
        (= line 1)
        (-> state
            (update-in [:regs :d] + (* 7 362))
            (update :line + 7))

        (= line 12)
        (-> state
            (update-in [:regs :a] + (quot (regs :b) 2))
            (assoc-in [:regs :c] (- 2 (rem (regs :b) 2)))
            (assoc-in [:regs :b] 0)
            (update :line + 8))

        :else
        (-> state
            (update :line inc)
            (assoc-in [:regs where] (or (regs what) what))))

      [:jnz val jump]
      (if (zero? (or (regs val) val))
        (update state :line inc)
        (assoc state :line (+ line (or (regs jump) jump))))

      [:inc reg nil]
      (-> state
          (update :line inc)
          (update-in [:regs reg] inc))

      [:dec reg nil]
      (-> state
          (update :line inc)
          (update-in [:regs reg] dec))

      [:out val nil]
      (let [current (or (regs val) val)
            prev (peek (:out state))]
        (if (or (not (#{0 1} current))
                (= prev current))
          (-> state
              (assoc :done? true))
          (-> state
              (update :out conj current)
              (update :line inc)))))))


(defn run-computer [state]
  (loop [initial-state state
         a 0]
    (let [state (update initial-state :regs conj {:a a})
          res (loop [state state]
                (if (or (:done? state)
                        (> (count (:out state)) 10))
                  (:out state)
                  (recur (run-instruction state))))]
      (if (> (count res) 10)
        a
        (recur initial-state (inc a))))))


(defn solve [input]
  (let [instructions (vec (aoc/read-input input day12/parse-line))
        init-state {:instrs instructions
                    :len (count instructions)
                    :line 0
                    :done? false
                    :out []
                    :regs {:b 0 :c 0 :d 0}}]
    (run-computer init-state)))


(solve 25)
