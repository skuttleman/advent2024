(ns advent2024.day-3
  (:require
    [advent2024.utils :as utils]))

(defn ^:private parse-input [input]
  (for [line (re-seq #"((mul)\(\d{1,3},\d{1,3}\)|(do(n't)?)\(\))" input)
        :let [ns (map parse-long (re-seq #"\d{1,3}" (first line)))
              cmd (keyword (first (filter some? (drop 2 line))))]]
    [cmd ns]))

(defn part-1-solution [input]
  (transduce (comp (filter (comp #{:mul} first))
                   (map #(apply * (second %))))
             +
             0
             input))

(comment
  (def input (utils/read-input 3))
  (part-1-solution (parse-input input)))
