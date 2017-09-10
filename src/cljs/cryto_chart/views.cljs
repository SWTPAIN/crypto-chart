(ns crypto-chart.views
  (:require [re-frame.core :as rf]))


(defn err-msg-toastr
  [err-msg]
  (if (nil? err-msg)
    [:div]
    [:div err-msg]
  ))


(defn coin-item
  [{:keys [id rank name symbol price_usd market_cap_usd]}]
  [:tr
    [:td rank]
    [:td name]
    [:td symbol]
    [:td price_usd]
    [:td market_cap_usd]]
  )

(defn coin-list
  []
  (let [coins @(rf/subscribe [:coins])]
    [:tbody
      (for [coin coins]
        ^{:key (:id coin)} [coin-item coin])]
  ))


(defn main-panel
  []
  (let [loading? @(rf/subscribe [:loading?])
        error-msg @(rf/subscribe [:error-msg])]
    [:div#app
      [err-msg-toastr error-msg]
      [:button {:onClick #(rf/dispatch [:reload-coins])} (if loading? "Loading" "Reload")]
      [:table.table.table-hover
       [:thead
        [:tr
         [:td "Rank"]
         [:td "Name"]
         [:td "Symbol"]
         [:td "Price (USD)"]
         [:td "Market Cap (USD)"]]]
       [coin-list]]]))
