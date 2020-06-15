(ns veryslide.components.navigation
  (:require [veryslide.state :as state]
            [secretary.core :as secretary]))

(defn navigation-auth []
  [:ul
   [:li
    [:a.Button.Primary {:href "/#/slide/new"}
     [:i.fas.fa-plus-circle] "New Slide"]]
   [:li
    [:a.Button {:href "/#/home"} "My Page"]]
   [:li
    [:a.Button {:href "/#/account"} "Account"]]])

(defn navigation-non-auth []
  [:ul
   [:li
    [:a.Button {:href "/#/sign-in"} "Sign In"]]])

(defn navigation []
  [:div.Navigation
   [:a.Brand {:href "/"} "Veryslide"]
   (if @state/user
     [navigation-auth]
     [navigation-non-auth])])