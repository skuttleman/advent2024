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

(defn part-2-solution [input]
  (loop [[[cmd ns] :as input] input
         do? true
         result 0]
    (cond
      (empty? input) result
      (= cmd :do) (recur (rest input) true result)
      (or (= cmd :don't) (not do?)) (recur (rest input) false result)
      :else (recur (rest input) true (+ result (apply * ns))))))

(comment
  (def input (utils/read-input 3))
  (part-1-solution (parse-input input))
  (part-2-solution (parse-input input)))
