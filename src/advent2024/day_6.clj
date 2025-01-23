(ns advent2024.day-6
  (:require
    [advent2024.utils :as utils]
    [clojure.string :as string]))

(defn ^:private parse-input [input]
  (let [guard (promise)
        grid (vec (for [[row line] (map-indexed vector (string/split-lines input))]
                    (vec (for [[col cell] (map-indexed vector line)]
                           (do (when (= \^ cell)
                                 (deliver guard [row col]))
                               (when (= \# cell)
                                 cell))))))]
    {:grid      grid
     :guard     @guard
     :direction :up}))

(def ^:private turn-right
  {:up    :right
   :right :down
   :down  :left
   :left  :up})

(defn ^:private move [pos direction]
  (case direction
    :up (update pos 0 dec)
    :down (update pos 0 inc)
    :left (update pos 1 dec)
    :right (update pos 1 inc)))

(defn ^:private movable? [{:keys [grid guard direction] :as state}]
  (let [next-pos (move guard direction)]
    (nil? (get-in grid next-pos))))

(defn ^:private in-bounds? [{:keys [grid guard] :as state}]
  (let [[row col] guard]
    (and (< -1 row (count grid))
         (< -1 col (count (first grid))))))

(defn ^:private steps [state]
  (iterate (fn [{:keys [direction] :as state}]
             (if (movable? state)
               (update state :guard move direction)
               (update state :direction turn-right)))
           state))

(defn part-1-solution [state]
  (->> (steps state)
       (take-while in-bounds?)
       (map :guard)
       distinct
       count))

(comment
  (def input (utils/read-input 6))
  (part-1-solution (parse-input input)))
