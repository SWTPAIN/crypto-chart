(ns cryto-chart.views
  (:require [re-frame.core :as rf]))

(defn main-panel []
  (let [point (rf/subscribe [:point])]
    (fn []
      [:div
        [:div {:onClick #(rf/dispatch [:add-point])} "Plus"]
        [:div @point]
        [:div {:onClick #(rf/dispatch [:minus-point])} "Minus"]
      ])))
