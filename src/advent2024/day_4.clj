(ns advent2024.day-4
  (:require
    [advent2024.utils :as utils]
    [clojure.string :as string]))

(defn ^:private parse-input [input]
  (mapv vec (string/split-lines input)))

(defn ^:private no-change [val _]
  val)

(defn ^:private get-letter [puzzle row col]
  (nth (nth puzzle row []) col nil))

(defn ^:private ->word-dir [row-fn col-fn]
  (fn [puzzle row col len]
    (string/join (for [i (range len)]
                   (get-letter puzzle (row-fn row i) (col-fn col i))))))

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

(defn part-2-solution [puzzle]
  (->> (for [[row line] (map-indexed vector puzzle)
             [col letter] (map-indexed vector line)
             :let [up-left (get-letter puzzle (dec row) (dec col))
                   up-right (get-letter puzzle (dec row) (inc col))
                   down-left (get-letter puzzle (inc row) (dec col))
                   down-right (get-letter puzzle (inc row) (inc col))]]
         (and (= letter \A)
              (or (and (= up-left \M) (= down-right \S))
                  (and (= up-left \S) (= down-right \M)))
              (or (and (= down-left \M) (= up-right \S))
                  (and (= down-left \S) (= up-right \M)))))
       (filter true?)
       count))

(comment
  (def input (utils/read-input 4))
  (part-1-solution (parse-input input))
  (part-2-solution (parse-input input)))
