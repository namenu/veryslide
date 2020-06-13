(ns veryslide.main
  (:require [reagent.dom :as rdom]
            [veryslide.components.footer :refer [footer]]
            [veryslide.components.sign-in :refer [sign-in]]
            [veryslide.state :as state]))

(defn app []
  [:div.VerySlideWeb
   [:div.Landing
    [:div.Title
     [:div.Hero
      [:h1 "Veryslide"]
      [:h2 "Forge and share versatile slides."]
      (if @state/user
        [:div
         [:p "Welcome back, {authUser.displayName}."]
         [:a.Button.Primary.Large {:to "{ROUTES.HOME}"} "Go to my page" [:i.fas.fa-arrow-circle-right.right]]]
        [:div
         [:a.Button.Primary.Large {:to "{ROUTES.SIGNUP}"} "Sign up" [:i.fas.fa-arrow-circle-right.right]]
         [sign-in]])]]
    [:div.Intro
     [:div.Content
      [:h1 "What is Veryslide?"]
      [:p "Description goes here."]
      [:h1 "Discover slides"]
      [:p "Slides goes here."]]
     [footer]]]])

(defn run []
  (rdom/render [app] (.getElementById js/document "root")))

(defn ^:export main! []
  (run))

(defn reload! []
  (run))
