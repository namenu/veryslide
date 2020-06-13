(ns veryslide.pages.landing
  (:require [veryslide.components.footer :refer [footer]]
            [veryslide.components.sign-in :refer [sign-in]]
            [veryslide.state :as state]))

(defn landing []
  [:div.VerySlideWeb
   [:div.Landing
    [:div.Title
     [:div.Hero
      [:h1 "Veryslide"]
      [:h2 "Forge and share versatile slides."]
      (if @state/user
        [:div
         [:p (str "Welcome back, " (:name @state/user))]
         [:a.Button.Primary.Large {:href "/home"} "Go to my page" [:i.fas.fa-arrow-circle-right.right]]]
        [:div
         [:a.Button.Primary.Large {:href "/#/sign-up"} "Sign up" [:i.fas.fa-arrow-circle-right.right]]
         [sign-in]])]]
    [:div.Intro
     [:div.Content
      [:h1 "What is Veryslide?"]
      [:p "Description goes here."]
      [:h1 "Discover slides"]
      [:p "Slides goes here."]]
     [footer]]]])
