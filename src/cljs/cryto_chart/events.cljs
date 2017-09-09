(ns cryto-chart.events
  (:require [re-frame.core :as re-frame]
            [cryto-chart.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
  :add-point
  (fn [db _]
    (assoc db :point (+ (:point db) 1))))

(re-frame/reg-event-db
  :minus-point
  (fn [db _]
    (assoc db :point (- (:point db) 1))))
