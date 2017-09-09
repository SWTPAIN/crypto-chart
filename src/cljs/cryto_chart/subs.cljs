(ns cryto-chart.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :point
 (fn [db]
   (:point db)))
