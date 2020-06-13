(ns veryslide.pages.sign-up
  (:require [reagent.core :as r]))



(defn sign-up-form []
  (let [error (r/atom nil)]
    (fn []
      [:div
       (when @error
         [:p.Label.Error (.-message @error)])
       [:form {:on-submit (fn []
                            (js/console.log "{this.onSubmit}"))}
        [:div.InputGroup
         [:i.fas.fa-user]
         [:input {:name "username" :value "{username}" :onChange "{this.onChange}" :type "text" :placeholder "Full Name" :required "true"}]]
        [:div.InputGroup
         [:i.fas.fa-envelope]
         [:input {:name "email" :value "{email}" :onChange "{this.onChange}" :type "text" :placeholder "Email Address" :required "true"}]]
        [:div.InputGroup
         [:i.fas.fa-check]
         [:input {:name "passwordOne" :value "{passwordOne}" :onChange "{this.onChange}" :type "password" :placeholder "Password" :required "true"}]]
        [:div.InputGroup
         [:i.fas.fa-check-double]
         [:input {:name "passwordTwo" :value "{passwordTwo}" :onChange "{this.onChange}" :type "password" :placeholder "Confirm Password" :required "true"}]]
        [:button.Primary :disabled "{isInvalid}" :type "submit" "Sign Up"]]])))

(defn sign-up []
  [:div.Center
   [:div
    [:h2 "Welcome to Veryslide!"]
    [:p "Create an account and bake your ideas into awesome slides."]
    [sign-up-form]]])