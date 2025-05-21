(ns advent2024.day-7
  (:require
   [advent2024.utils :as utils]
   [clojure.string :as string]))

(defn ^:private parse-input [input]
  (let [lines (string/split-lines input)]
    (for [line lines
          :let [[total & nums] (map parse-long (string/split line #":?\s+"))]]
      [total nums])))

(defn ^:private || [x y]
  (parse-long (str x y)))

(defn ^:private totals? [operators]
  (fn totals*
    ([[total nums]]
     (totals* total (first nums) (rest nums)))
    ([total sub-total [num :as nums]]
     (cond
       (and (empty? nums) (= total sub-total))
       true

       (or (> sub-total total) (empty? nums))
       false

       :else
       (some #(totals* total (% sub-total num) (rest nums))
             operators)))))

(defn part-1-solution [input]
  (->> input
       (filter (totals? [+ *]))
       (map first)
       (reduce + 0)))

(defn part-2-solution [input]
  (->> input
       (filter (totals? [+ * ||]))
       (map first)
       (reduce + 0)))

(comment
 (def input (utils/read-input 7))
 (part-1-solution (parse-input input))
 (part-2-solution (parse-input input)))
