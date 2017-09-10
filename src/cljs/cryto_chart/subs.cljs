(ns crypto-chart.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :loading?
 (fn [db]
   (:loading? db)))

(re-frame/reg-sub
  :error-msg
  (fn [db]
    (:error-msg db)))

(re-frame/reg-sub
  :coins
  (fn [db]
    (:coins db)))
