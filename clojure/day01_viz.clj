(ns day01-viz
  (:require aoc
            day01
            [quil.core :as q]
            [quil.middleware :as m]))


(def window-size 700)

(def instructions (->> (aoc/read-input-line 1 :words)
                       (map day01/parse-instruction)))

(defn rand-color []
  (take 3 (repeatedly #(+ 100 (rand-int 155)))))


(defn setup []
  (q/frame-rate 60)
  (q/smooth)
  (q/background 15 15 33)
  (q/stroke-weight 3)
  {:cnt 0
   :pos [0 0]
   :color (rand-color)
   :dir [0 -1]
   :instr instructions})

(defn update-state [{:keys [cnt pos dir instr] :as state}]
  (if (empty? instr)
      (do
        (q/delay-frame 2000)
        (q/exit))
      (let [new-pos (aoc/pt+ pos dir)
            new-dir (day01/rotate dir (:rot (first instr)))]
        (cond
          (zero? cnt)
          (-> state
              (update :cnt inc)
              (assoc :dir new-dir))

          (<= cnt (:amt (first instr)))
          (-> state
              (update :cnt inc)
              (assoc :pos new-pos))

          :else
          (-> state
              (assoc :color (rand-color)
                     :cnt 0)
              (update :instr rest))))))

(defn draw-state [{:keys [pos color]}]
  (q/with-translation [(* 3/4 window-size) (* 9/10 window-size)]
    (q/with-stroke color
      (when-let [[x y] pos]
        (q/point (* 3 x) (* 3 y))
        #_(q/save-frame "/tmp/imgs/day01-####.jpg")))))


(q/sketch
 :size [window-size window-size]
 :setup #'setup
 :update #'update-state
 :draw #'draw-state
 :middleware [m/fun-mode])

; convert -layers optimize -delay 2 /tmp/imgs/day01*.jpg imgs/day01.gif
