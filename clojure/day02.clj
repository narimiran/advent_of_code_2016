(ns day02
  (:require aoc))


(def keypad-1 [[1 2 3]
               [4 5 6]
               [7 8 9]])

(def keypad-2 [[0 0 1 0 0]
               [0 2 3 4 0]
               [5 6 7 8 9]
               [0 \A \B \C 0]
               [0 0 \D 0 0]])

(defn coord->key [keypad [dx dy] [x y]]
  ((keypad (+ y dy)) (+ x dx)))


(defn move [[x y] dir]
  (case dir
    :left  [(dec x) y]
    :right [(inc x) y]
    :up    [x (dec y)]
    :down  [x (inc y)]))


(defn p1-inbounds? [[x y]]
  (and (< (abs x) 2)
       (< (abs y) 2)))

(defn p2-inbounds? [[x y]]
  (<= (+ (abs x) (abs y)) 2))

(defn press [pos instruction in-keypad?]
  (reduce
   (fn [pos instr]
     (let [new-pos (move pos instr)]
       (if (in-keypad? new-pos) new-pos pos)))
   pos
   instruction))

(defn type-password [instructions part]
  (let [{:keys [keypad valid? start offset]}
        (case part
          1 {:keypad keypad-1 :valid? p1-inbounds? :start [0 0]  :offset [1 1]}
          2 {:keypad keypad-2 :valid? p2-inbounds? :start [-2 0] :offset [2 2]})]
    (->> instructions
         (reduce (fn [pos instr]
                   (conj pos (press (peek pos) instr valid?)))
                 [start])
         rest
         (map (partial coord->key keypad offset))
         (apply str))))


(defn parse-line [line]
  (map {\L :left
        \R :right
        \U :up
        \D :down}
       (vec line)))


(defn solve [input]
  (let [instructions (aoc/read-input input parse-line)]
    [(type-password instructions 1)
     (type-password instructions 2)]))


(solve 2)
