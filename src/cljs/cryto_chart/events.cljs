(ns crypto-chart.events
  (:require [re-frame.core :as rf]
            [crypto-chart.db :as db]
            [ajax.core :as ajax]
            [day8.re-frame.http-fx]))

(def cointApiEndpoint "https://api.coinmarketcap.com/v1/ticker/?limit=10")

(rf/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(rf/reg-event-fx
  :reload-coins
  [rf/debug]
  (fn
    [{db :db} _]
    { :http-xhrio {:method  :get
                   :uri     cointApiEndpoint
                   :format  (ajax/json-request-format)
                   :response-format (ajax/json-response-format {:keywords? true})
                   :on-success  [:process-response]
                   :on-failure  [:bad-response]}
      :db          (assoc db :loading? true)
    }))

(rf/reg-event-db
  :process-response
  [rf/debug]
  (fn
    [db [_ response]]
    (-> db
        (assoc :loading? false)
        (assoc :coins (js->clj response))
    )))


(rf/reg-event-db
  :bad-response
  (fn
    [db [_ response]]
    (-> db
        (assoc :loading? false)
        (assoc :error-msg "Failed to load data")
    )))

(rf/reg-event-db
  :timer
  (fn
    [db [_ new-time]]
    (assoc db :time new-time)))

; panel
(rf/reg-event-db
  :panels/toggle
  (fn [db [_ id]]
    (update-in db [:panels id] not)))

; profile
(rf/reg-event-db
  :profile/edit
  (fn [db [_ field-name value]]
    (println "field-name => " field-name)
    (println "value=> " value)
    (assoc-in db [:profile field-name] value)))