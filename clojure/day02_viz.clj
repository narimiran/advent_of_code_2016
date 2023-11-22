(ns day02-viz
  (:require aoc
            aoc-viz
            day02
            [quil.core :as q]
            [quil.middleware :as m]))

(def button-size 50)
(def bg-color [15 15 33])
(def fg-color [255 255 96])

(def half-size (/ button-size 2))
(def window-size (* 3 button-size))



(defn press [pos instruction]
  (vec
   (reductions
    (fn [{:keys [pos]} instr]
      (let [new-pos (day02/move pos instr)]
        (if (day02/p1-inbounds? new-pos)
          {:pos new-pos :moved true}
          {:pos pos :moved false})))
    {:pos pos :moved true}
    instruction)))

(defn all-presses [instructions]
  (vec
   (rest
    (reduce
     (fn [pressed-keys instr]
       (let [new-keys (press (:pos (peek (peek pressed-keys))) instr)]
         (conj pressed-keys new-keys)))
     [[{:pos [0 0] :moved true}]]
     instructions))))


(def instructions (aoc/read-input 2 day02/parse-line))
(def all-positions (mapv #(filter :moved %) (all-presses instructions)))
(def state {:all-positions all-positions
            :to-print []})



(defn setup []
  (q/frame-rate 50)
  (q/smooth)
  (q/text-size half-size)
  state)

(defn update-keypad [{:keys [all-positions] :as state}]
  (if (empty? all-positions)
    (do
      (q/delay-frame 3000)
      (q/exit)
      state)
    (if (empty? (rest (first all-positions)))
      (let [pos (:pos (first (first all-positions)))
            kk (day02/coord->key day02/keypad-1 [1 1] pos)]
        (q/delay-frame 500)
        (-> state
            (update :to-print conj kk)
            (update :all-positions (comp vec rest))))
      (-> state
          (update-in [:all-positions 0] (comp vec rest))))))

(defn draw-keypad [{:keys [all-positions to-print]}]
  (let [[r g b] bg-color]
    (q/background r g b))
  (doseq [[i x] (map-indexed vector (range 3))
          [j y] (map-indexed vector (range 3))]
    (q/with-fill bg-color
      (aoc-viz/scaled-square x y button-size))
    (q/with-fill fg-color
      (q/text-align :center :center)
      (q/text (str (+ i 1 (* 3 j)))
              (+ (* button-size x) half-size)
              (+ (* button-size y) half-size))))
  (when (not-empty all-positions)
    (let [[x y] (:pos (first (first all-positions)))]
      (q/with-fill fg-color
        (aoc-viz/scaled-square (inc x) (inc y) button-size))))
  (q/with-fill fg-color
    (q/text-align :left :center)
    (q/text (apply str to-print)
            (* 4 button-size) (+ button-size half-size)))
  #_(q/save-frame "/tmp/imgs/day02-####.jpg"))


(q/sketch
 :size [(* 2 window-size) window-size]
 :setup #'setup
 :draw #'draw-keypad
 :update #'update-keypad
 :middleware [m/fun-mode])





; for f in "0351" "0695" "0976" "1280" "1672";
;     for c in (seq 30);
;         cp /tmp/imgs/day02-$f.jpg /tmp/imgs/day02-$f-$c.jpg;
;     end;
; end

; convert -layers optimize -delay 2 /tmp/imgs/day02*.jpg -delay 200 /tmp/imgs/day02-1673.jpg imgs/day02.gif
