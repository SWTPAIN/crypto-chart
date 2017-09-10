(ns crypto-chart.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [crypto-chart.events]
            [crypto-chart.subs]
            [crypto-chart.views :as views]
            [crypto-chart.config :as config]))

(def POLLING_API_INTERVAL 10000)

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (rf/clear-subscription-cache!)
  (reagent/render [views/main-app]
                  (.getElementById js/document "app")))

(defn dispatch-reload-coins-event
  []
  (rf/dispatch [:reload-coins]))

(defonce time-updater (js/setInterval
  dispatch-reload-coins-event POLLING_API_INTERVAL))

(defn ^:export init []
  (rf/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
