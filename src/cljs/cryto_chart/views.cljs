(ns crypto-chart.views
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]))


(defn err-msg-toastr
  [err-msg]
  (if (nil? err-msg)
    [:div]
    [:div err-msg]
  ))

(defn sortable-table [header data]
  (let [s (reagent/atom {})]
    (fn [header data]
      (let [column-keywords (map :keyword header)
            key (:sort-key @s)
            dir (:sort-dir @s)
            rows (cond->> data
              key (sort-by #(key %))
              (= :ascending dir) reverse)
            sorts [key dir]]
        [:table {:class "sortable-table"}
          [:thead
            [:tr
              (for [h header]
                (let [keyword (:keyword h)
                      name (:name h)]
                  [:th
                    {:key (:name h)
                    :on-click #(cond
                                (= [keyword :ascending] sorts)
                                (swap! s dissoc :sort-dir :sort-key)
                                (= [keyword :descending] sorts)
                                (swap! s assoc :sort-dir :ascending)
                                :else
                                (swap! s assoc
                                  :sort-key keyword
                                  :sort-dir :descending))}
                    [:div {:style {:display :inline-block}}
                      name]
                    [:div {:style {:display :inline-block
                                   :line-height :1em
                                   :font-size :60%}}
                      [:div
                        {:style {:color (if (= [keyword :descending] sorts)
                                          :black
                                          "#aaa")}}
                        "▲"]
                      [:div
                        {:style {:color (if (= [keyword :ascending] sorts)
                                          :black
                                          "#aaa")}}
                        "▼"]]]))]]
           [:tbody
            (for [row rows]
              [:tr
               {:key (:id row)}
               (for [ck column-keywords]
                 [:td {:key (ck row)} (ck row)])
               ])
            ]
          ]
                ))))

(defn coin-item
  [{:keys [id rank name symbol price_usd market_cap_usd]}]
  [:tr
    [:td rank]
    [:td name]
    [:td symbol]
    [:td price_usd]
    [:td market_cap_usd]]
  )

(def coin-headers
  '({:name "Rank" :keyword :rank}
    {:name "Name" :keyword :name}
    {:name "Symbol" :keyword :symbol}
    {:name "Price (USD)" :keyword :price_usd}
    {:name "Market Cap (USD)" :keyword :market_cap_usd}))

(defn coin-list
  []
  (let [coins @(rf/subscribe [:coins])]
    [sortable-table coin-headers coins]
  ))

(defn panel [id title children]
  (let [s (reagent/atom {:child-height 0})]
    (fn [id title children]
      (let [open? @(rf/subscribe [:panels/state id])
            child-height (:child-height @s)]
      [:div
        [:div
          {:on-click #(rf/dispatch [:panels/toggle id])
           :class "panel-title"}
          [:div
            {:style {:float "right"}}
            (if open? "-" "+")]
            title]
        [:div
          {:class "panel-children-container"
           :style {:max-height (if open? child-height 0)}}
          [:div
            {:ref #(when % (swap! s assoc :child-height (.-clientHeight %)))
             :class "panel-children"}
            children]]
            ]))))

(defn coin-ranking
  []
  (let [loading? @(rf/subscribe [:loading?])
        error-msg @(rf/subscribe [:error-msg])]
    [:div#app
      [err-msg-toastr error-msg]
      [:button {:onClick #(rf/dispatch [:reload-coins])} (if loading? "Loading" "Reload")]
      [coin-list]]))

(defn main-app
  []
  [panel :coin-ranking "Coin Ranking" [coin-ranking]])
