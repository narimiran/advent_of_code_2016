(ns day13-viz
  (:require aoc
            aoc-viz
            day13
            [quil.core :as q]
            [quil.middleware :as m]))


(def scale 15)
(def bg-color [15 15 33])
(def fg-color [255 255 96])
(def x-lim 38)
(def y-lim 43)
(def start-x 1)
(def start-y 1)
(def end-x 31)
(def end-y 39)


(defn is-wall? [x y]
  (odd? (aoc/count-if #{\1} (Integer/toBinaryString (day13/formula x y)))))

(def walls
  (into #{}
        (for [x (range x-lim)
              y (range y-lim)
              :when (is-wall? x y)]
          [x y])))


(def initial-state
  {:queue (conj clojure.lang.PersistentQueue/EMPTY
                {:pos [start-x start-y] :steps [[start-x start-y]]})
   :seen #{}})



(defn add-nbs [queue seen]
  (let [{:keys [pos steps]} (peek queue)]
    (reduce
     (fn [queue [x y :as pos]]
       (if (not (or (seen pos)
                    (neg? x)
                    (neg? y)
                    (walls pos)))
         (conj queue {:pos pos :steps (conj steps pos)})
         queue))
     (pop queue)
     (aoc/neighbours pos 4))))




(defn setup []
  (q/frame-rate 20)
  (q/smooth)
  (q/background 120)
  (q/stroke 255 255 96)
  (q/ellipse-mode :corner)
  (q/text-align :center)
  (q/text-size scale)
  initial-state)


(defn update-state [{:keys [queue seen] :as state}]
  (let [{:keys [pos]} (peek queue)
        new-seen (conj seen pos)
        new-queue (add-nbs queue seen)]
    (cond
      (= pos [end-x end-y])
      (do
        (q/delay-frame 2000)
        (q/exit)
        state)
      :else {:queue new-queue
             :seen new-seen})))


(defn draw-walls []
  (q/with-fill bg-color
    (q/with-stroke bg-color
      (doseq [[x y] walls]
        (aoc-viz/scaled-square x y scale)))))


(defn draw-steps [{:keys [queue]}]
  (let [steps (:steps (peek queue))]
    (q/with-fill fg-color
      (doseq [[x y] steps]
        (aoc-viz/scaled-square x y scale)))))


(defn draw-state [state]
  (q/background 120)
  (draw-walls)
  (draw-steps state)
  (q/with-fill [15 200 15]
    (aoc-viz/scaled-circle start-x start-y scale))
  (q/with-fill [200 15 15]
    (aoc-viz/scaled-circle end-x end-y scale))
  #_(q/save-frame "/tmp/imgs/day13-###.jpg"))


(q/sketch
 :size [(* scale x-lim) (* scale y-lim)]
 :setup #'setup
 :update #'update-state
 :draw #'draw-state
 :middleware [m/fun-mode])




; convert -layers optimize -delay 10 /tmp/imgs/day13*.jpg -delay 200 /tmp/imgs/day13-286.jpg imgs/day13.gif
