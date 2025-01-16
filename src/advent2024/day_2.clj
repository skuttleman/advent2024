(ns advent2024.day-2
  (:require
    [advent2024.utils :as utils]
    [clojure.string :as string]))

(defn ^:private parse-input [input]
  (for [line (string/split-lines input)
        :let [report (re-seq #"\d+" line)]]
    (map parse-long report)))

(defn ^:private ordered? [pred report]
  (every? true? (map pred report (rest report))))

(defn ^:private ascending? [report]
  (ordered? (fn [val-1 val-2]
              (> val-2 val-1))
            report))

(defn ^:private descending? [report]
  (ordered? (fn [val-1 val-2]
              (> val-1 val-2))
            report))

(defn ^:private diffs [report]
  (map (fn [val-1 val-2]
         (abs (- val-1 val-2)))
       report
       (rest report)))

(defn ^:private safe? [report]
  (and (or (ascending? report)
           (descending? report))
       (>= 3 (apply max (diffs report)))))

(defn part-1-solution [reports]
  (->> reports
       (filter safe?)
       count))

(comment
  (def input (utils/read-input 2))
  (part-1-solution (parse-input input)))
