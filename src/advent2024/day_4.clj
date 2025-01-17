(ns advent2024.day-4
  (:require
    [advent2024.utils :as utils]
    [clojure.string :as string]))

(defn ^:private parse-input [input]
  (mapv vec (string/split-lines input)))

(defn ^:private no-change [val _]
  val)

(defn ^:private ->word-dir [row-fn col-fn]
  (fn [puzzle row col len]
    (string/join (for [i (range len)]
                   (nth (nth puzzle (row-fn row i) []) (col-fn col i) nil)))))

(def ^:private word-up (->word-dir - no-change))
(def ^:private word-up-right (->word-dir - +))
(def ^:private word-right (->word-dir no-change +))
(def ^:private word-down-right (->word-dir + +))
(def ^:private word-down (->word-dir + no-change))
(def ^:private word-down-left (->word-dir + -))
(def ^:private word-left (->word-dir no-change -))
(def ^:private word-up-left (->word-dir - -))

(defn ^:private get-words-from [puzzle row col len]
  [(word-up puzzle row col len)
   (word-up-right puzzle row col len)
   (word-right puzzle row col len)
   (word-down-right puzzle row col len)
   (word-down puzzle row col len)
   (word-down-left puzzle row col len)
   (word-left puzzle row col len)
   (word-up-left puzzle row col len)])

(defn ^:private count-from-pos [puzzle word row col]
  (->> (get-words-from puzzle row col (count word))
       (filter #{word})
       count))

(defn part-1-solution [puzzle]
  (reduce + 0 (for [[row line] (map-indexed vector puzzle)
                    [col letter] (map-indexed vector line)
                    :when (= letter \X)]
                (count-from-pos puzzle "XMAS" row col))))

(comment
  (def input (utils/read-input 4))
  (part-1-solution (parse-input input)))
