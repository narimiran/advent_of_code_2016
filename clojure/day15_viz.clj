(ns day15-viz
  (:require aoc
            aoc-viz
            day15
            [quil.core :as q]
            [quil.middleware :as m]))


(def starting-discs (aoc/read-input 15 day15/parse-line))
(def starting-time 122200)
(def largest-disc 19)

(def scale 20)
(def bg-color [15 15 33])
(def fg-color [255 255 96])

(defn update-initial [time [nr tot pos]]
  [nr tot (mod (+ pos time nr (quot tot 2)) tot)])

(defn update-position [[nr tot pos]]
  [nr tot (mod (inc pos) tot)])


(defn setup []
  (q/frame-rate 10)
  (q/smooth)
  (q/background 120)
  (q/stroke 200)
  (q/ellipse-mode :corner)
  (q/text-align :center)
  (q/text-size scale)
  {:discs (map #(update-initial starting-time %) starting-discs)
   :time starting-time})


(defn update-state [{:keys [discs time] :as state}]
  (if (day15/falls-through? starting-discs time)
    (do
      (q/delay-frame 2000)
      (q/exit)
      state)
    {:discs (map update-position discs)
     :time (inc time)}))


(defn draw-discs [discs]
  (q/with-fill bg-color
    (doseq [[nr tot _] discs]
      (let [offset (quot (- largest-disc tot) 2)]
        (doseq [i (range tot)]
          (aoc-viz/scaled-square (+ offset i) nr scale))))))

(defn draw-slots [discs]
  (q/with-fill fg-color
    (doseq [[nr tot pos] discs]
      (let [offset (quot (- largest-disc tot) 2)]
        (aoc-viz/scaled-circle (+ offset pos) nr scale)))))


(defn draw-everything [{:keys [discs time]}]
  (q/background 120)
  (draw-discs discs)
  (draw-slots discs)
  (q/with-fill fg-color
    (q/text (str time)
            (* scale (+ 0.5 (quot largest-disc 2)))
            (* scale (+ 3 (count starting-discs)))))
  #_(q/save-frame "/tmp/imgs/day15-###.jpg"))

(q/sketch
 :size [(* scale largest-disc) (* scale (+ 4 (count starting-discs)))]
 :setup #'setup
 :update #'update-state
 :draw #'draw-everything
 :middleware [m/fun-mode])




; convert -layers optimize -delay 10 /tmp/imgs/day15-(seq -f "%03g" 99).jpg -delay 20 /tmp/imgs/day15-1*.jpg imgs/day15.gif
