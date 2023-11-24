(ns day08-viz
  (:require aoc
            aoc-viz
            day08
            [quil.core :as q]
            [quil.middleware :as m]))


(def scale 10)
(def width 50)
(def height 6)
(def bg-color [15 15 33])
(def fg-color [255 255 96])


(def instructions (aoc/read-input 8 day08/parse-line))

(defn put-pixels [instructions]
  (reductions
   (fn [screen [command a b]]
     (case command
       :rect (day08/create screen a b)
       (day08/rotate screen command a b)))
   (repeat height (repeat width " "))
   instructions))

(def all-screens (put-pixels instructions))


(defn setup []
  (q/frame-rate 10)
  (q/smooth)
  (q/background 15 15 33)
  all-screens)

(defn frame-update [screens]
  (if (empty? screens)
    (do
      (q/delay-frame 3000)
      (q/exit))
    (rest screens)))

(defn draw-screen [screens]
  (let [screen (first screens)]
    (doseq [[y line] (map-indexed vector screen)
            [x char] (map-indexed vector line)]
      (q/with-fill (case char
                     " " bg-color
                     "#" fg-color)
        (aoc-viz/scaled-square x y scale)
        #_(q/save-frame "/tmp/imgs/day08-####.jpg")))))


(q/sketch
 :size [(* scale width) (* scale height)]
 :setup #'setup
 :update #'frame-update
 :draw #'draw-screen
 :middleware [m/fun-mode])


; convert -layers optimize -delay 10 /tmp/imgs/day08*.jpg -delay 300 /tmp/imgs/day08-0154.jpg imgs/day08.gif
