(ns cryto-chart.profile.view
  (:require [reagent.core :as reagent]))

(defn inline-editor [txt on-change]
  (let [s (reagent/atom {})]
    (fn [txt on-change]
      [:span
       (if (:editing? @s)
         [:form {:on-submit #(do
                               (.preventDefault %)
                               (swap! s dissoc :editing?)
                               (on-change (:text @s)))}
          [:input {:type :text :value (:text @s)
                   :on-change #(swap! s assoc
                                      :text (-> % .-target .-value))}]
          [:button "Save"]
          [:button {:on-click #(do
                                 (.preventDefault %)
                                 (swap! s dissoc :editing?))}
           "Cancel"]]
         [:span
          {:on-click #(swap! s assoc
                             :editing? true
                             :text txt)}
          txt [:sup "✎"]])])
    ))