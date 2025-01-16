(ns advent2024.utils
  (:require
    [clojure.java.io :as io]))

(defn read-input [day]
  (slurp (io/resource (format "day-%d.txt" day))))
