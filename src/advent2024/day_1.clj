(ns advent2024.day-1
  (:require
    [advent2024.utils :as utils]
    [clojure.string :as string]))

(defn ^:private parse-input [input]
  (let [tuples (for [line (string/split-lines input)
                     :let [[_ n1 n2] (re-find #"\s*(\d+)\s+(\d+)" line)]]
                 [(parse-long n1) (parse-long n2)])]
    [(map first tuples)
     (map second tuples)]))

(defn ^:private diff [a b]
  (- (max a b) (min a b)))

(defn part-1-solution [[xs-1 xs-2]]
  (->> (map diff (sort xs-1) (sort xs-2))
       (reduce + 0)))

(comment
  (def input (utils/read-input 1))
  (part-1-solution (parse-input input)))
