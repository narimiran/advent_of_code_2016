(ns day23
  (:require aoc
            day12
            [clojure.core.match :refer [match]]))


(defn toggle [[a b c]]
  [({:cpy :jnz
     :jnz :cpy
     :inc :dec
     :dec :inc
     :tgl :inc} a)
   b
   c])


(defn run-instruction [{:keys [instrs len line regs] :as state}]
  (if-not (< -1 line len)
    (assoc state :done? true)
    (match (instrs line)
      [:cpy what where]
      (cond
        (= line 4)
        (-> state
            (update-in [:regs :a] + (* (regs :b) (regs :d)))
            (assoc-in [:regs :c] 0)
            (assoc-in [:regs :d] 0)
            (update :line + 6))

        (= line 20)
        (-> state
            (update-in [:regs :a] + (* 87 (regs :c)))
            (update :line + 6))

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

      [:tgl reg nil]
      (let [jump (regs reg)
            new-line (+ line jump)]
        (-> state
            (update :line inc)
            (#(if (< -1 new-line len)
                (assoc-in % [:instrs new-line] (toggle (instrs new-line)))
                %)))))))


(defn run-computer [{:keys [done?] :as state}]
  (if done?
    (-> state :regs :a)
    (recur (run-instruction state))))


(defn solve [input]
  (let [instructions (vec (aoc/read-input input day12/parse-line))
        init-state {:instrs instructions
                    :len (count instructions)
                    :line 0
                    :done? false
                    :regs {:b 0 :c 0 :d 0}}]
    (for [a [7 12]]
      (-> init-state
          (update :regs conj {:a a})
          run-computer))))


(solve 23)
