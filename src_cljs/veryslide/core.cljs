(ns veryslide.core
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.history.Html5History)
  (:require [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [reagent.dom :as rdom]
            [veryslide.state :refer [app-state]]
            [veryslide.fb.init :refer [firebase-init]]
            [veryslide.components.navigation :refer [navigation]]
            [veryslide.pages.landing :refer [landing]]
            [veryslide.pages.sign-in :refer [sign-in]]
            [veryslide.pages.sign-up :refer [sign-up]]
            [veryslide.pages.home :refer [home]]
            [veryslide.pages.account :refer [account]]))

(defn hook-browser-navigation! []
  (doto (Html5History.)
    (events/listen
      EventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")

  (defroute "/" []
    (swap! app-state assoc :page :landing))

  (defroute "/sign-in" []
    (swap! app-state assoc :page :sign-in))

  (defroute "/sign-up" []
    (swap! app-state assoc :page :sign-up))

  (defroute "/home" []
    (swap! app-state assoc :page :home))

  (defroute "/account" []
    (swap! app-state assoc :page :account))

  (hook-browser-navigation!))


(defn nav [component]
  [:div.VerySlideWeb
   [navigation]
   [:div.Content
    [component]]])


(defmulti current-page #(@app-state :page))
(defmethod current-page :landing [] [landing])
(defmethod current-page :sign-in [] (nav sign-in))
(defmethod current-page :sign-up [] (nav sign-up))
(defmethod current-page :home [] (nav home))
(defmethod current-page :account [] (nav account))
(defmethod current-page :default []
  [:div "you found a glitch here!"])


(defn ^:dev/after-reload start []
  (app-routes)
  (rdom/render [current-page]
               (.getElementById js/document "app")))

(defn ^:export main []
  (firebase-init)
  (start))

(defn reload! []
  (start))