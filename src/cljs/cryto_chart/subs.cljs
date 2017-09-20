(ns crypto-chart.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :loading?
 (fn [db]
   (:loading? db)))

(rf/reg-sub
  :error-msg
  (fn [db]
    (:error-msg db)))

(rf/reg-sub
  :coins
  (fn [db]
    (:coins db)))

; panel
(rf/reg-sub
  :panels/state
  (fn [db [_ id]]
    (get-in db [:panels id])))

; profile
(rf/reg-sub
  :profile
  (fn [db [_]]
    (:profile db)))