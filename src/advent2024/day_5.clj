(ns advent2024.day-5
  (:require
    [advent2024.utils :as utils]
    [clojure.set :as set]
    [clojure.string :as string]))

(defn ^:private parse-input [input]
  (let [[rules updates] (string/split input #"\n\n")
        rules (reduce (fn [rules line]
                        (let [[before after] (string/split line #"\|")]
                          (update rules (parse-long after) (fnil conj #{}) (parse-long before))))
                      {}
                      (string/split-lines rules))
        updates (for [update (string/split-lines updates)
                      :let [vals (string/split update #",")]]
                  (map parse-long vals))]
    {:rules   rules
     :updates updates}))

(defn ^:private valid-update? [rules vals]
  (loop [[val & more] vals]
    (cond
      (empty? more) true
      (seq (set/intersection (set more) (get rules val))) false
      :else (recur more))))

(defn ^:private middle [vals]
  (first (drop (dec (/ (count vals) 2)) vals)))

(defn ^:private fix-update [rules vals]
  (loop [idx 0
         vals vals]
    (let [val (get vals idx)
          prev (take idx vals)
          remaining (drop (inc idx) vals)
          rule (get rules val #{})]
      (if-let [out-of-orders (->> remaining
                                  (filter rule)
                                  seq)]
        (recur 0 (vec (concat out-of-orders prev [val] (remove rule remaining))))
        (if (seq remaining)
          (recur (inc idx) vals)
          vals)))))

(defn part-1-solution [{:keys [rules updates]}]
  (->> updates
       (filter (partial valid-update? rules))
       (map middle)
       (reduce + 0)))

(defn part-2-solution [{:keys [rules updates]}]
  (->> updates
       (remove (partial valid-update? rules))
       (map (comp middle (partial fix-update rules) vec))
       (reduce + 0)))

(comment
  (def input (parse-input (utils/read-input 5)))
  (part-1-solution input)
  (part-2-solution input))
