(ns day25-viz
  (:require aoc
            day12
            day12-viz
            day25
            [quil.core :as q]
            [quil.middleware :as m]))



(def instructions (vec (aoc/read-input 25 day12/parse-line)))
(def init-state {:instrs instructions
                 :len (count instructions)
                 :line 0
                 :done? false
                 :out []
                 :regs {:a 196 :b 0 :c 0 :d 0}})

(def line-height 20)


(defn setup []
  (q/frame-rate 15)
  (q/smooth)
  (q/background 15 15 33)
  (q/text-font (q/create-font "Iosevka Extended" (* 0.7 line-height)))
  (q/text-align :right :center)
  init-state)

(defn draw-table [{:keys [instrs line regs out]}]
  (day12-viz/draw-instructions instrs)
  (day12-viz/draw-registers regs)
  (day12-viz/draw-triangle line)
  (day12-viz/draw-out out)
  #_(q/save-frame "/tmp/imgs/day25-####.jpg")
  (when (> (count out) 15)
    (q/exit)))

(q/sketch
 :size [(* 16 line-height) (* 32 line-height)]
 :setup #'setup
 :update #'day25/run-instruction
 :draw #'draw-table
 :middleware [m/fun-mode])






; convert -layers optimize -delay 8 /tmp/imgs/day25*.jpg imgs/day25.gif
