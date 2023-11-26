(ns day12-viz
  (:require aoc
            day12
            [clojure.core.match :refer [match]]
            [quil.core :as q]
            [quil.middleware :as m]))


(def instructions (vec (aoc/read-input 12 day12/parse-line)))

(def bg-color [15 15 33])
(def fg-color [255 255 96])
(def line-height 20)

(def state {:line 0
            :regs {:a 0 :b 0 :c 0 :d 0}
            :len (count instructions)
            :done false})

(defn draw-triangle [line]
  (let [y2 (* (inc line) line-height)
        y1 (- y2 (/ line-height 3))
        y3 (+ y2 (/ line-height 3))
        x1 line-height
        x2 (* 2 line-height)]
    (q/with-fill fg-color
      (q/with-translation [0 (/ line-height 10)]
        (q/triangle x1 y1 x2 y2 x1 y3)))))

(defn draw-instructions [instructions]
  (let [[r g b] bg-color]
    (q/background r g b))
  (doseq [[line [instr a b]] (map-indexed vector instructions)]
    (let [y-pos (* (inc line) line-height)]
      (q/with-fill fg-color
        (q/text (name instr) (* 4 line-height) y-pos)
        (q/text (if (keyword? a) (name a) (str a)) (* 5.5 line-height) y-pos)
        (when b
          (q/text (if (keyword? b) (name b) (str b)) (* 7 line-height) y-pos))))))

(defn draw-registers [regs]
  (q/with-fill fg-color
    (doseq [[l c] (zipmap (range 9 20 2) ["a" "b" "c" "d"])]
      (q/text (str "reg " c ":") (* 12 line-height) (* l line-height))
      (q/text (str ((keyword c) regs)) (* 15 line-height) (* l line-height)))))

(defn draw-out [out]
  (q/with-fill fg-color
    (q/text "out:" (* 12 line-height) (* 17 line-height))
    (q/text (apply str out) (* 15 line-height) (* 18 line-height))))


(defn setup []
  (q/frame-rate 25)
  (q/smooth)
  (q/background 15 15 33)
  (q/text-font (q/create-font "Iosevka Extended" (* 0.7 line-height)))
  (q/text-align :right :center)
  state)

(defn state-update [{:keys [line regs len] :as state}]
  (if (< -1 line len)
    (match (instructions line)
      [:cpy what where]
      (-> state
          (update :line inc)
          (assoc-in [:regs where] (or (regs what) what)))

      [:jnz val jump]
      (cond
        (= line 12)
        (-> state
            (update-in [:regs :a] + (regs :b))
            (assoc-in [:regs :b] 0)
            (update :line inc))

        (= line 20)
        (-> state
            (update-in [:regs :a] + (regs :d))
            (assoc-in [:regs :d] 0)
            (update :line inc))

        :else
        (if (zero? (or (regs val) val))
          (update state :line inc)
          (assoc state :line (+ line jump))))

      [:inc reg nil]
      (-> state
          (update :line inc)
          (update-in [:regs reg] inc))

      [:dec reg nil]
      (-> state
          (update :line inc)
          (update-in [:regs reg] dec)))

    (assoc state :done true)))

(defn draw-table [{:keys [line regs done]}]
  (draw-instructions instructions)
  (draw-registers regs)
  (draw-triangle line)
  #_(q/save-frame "/tmp/imgs/day12-####.jpg")
  (when done
    (q/delay-frame 2000)
    (q/exit)))


(q/sketch
 :size [(* 16 line-height) (* 25 line-height)]
 :setup #'setup
 :update #'state-update
 :draw #'draw-table
 :middleware [m/fun-mode])




; convert -layers optimize -delay 4 /tmp/imgs/day12*.jpg -delay 200 /tmp/imgs/day12-0304.jpg imgs/day12.gif
