(ns veryslide.pages.sign-up
  (:require [reagent.core :as r]
            [veryslide.fb.auth :refer [update-profile]]
            [secretary.core :as secretary]))

(defn invalid? [{:keys [username email password-one password-two]}]
  (or (empty? username)
      (empty? email)
      (empty? password-one)
      (not= password-one password-two)))

(defn input-group [key icon type placeholder values]
  [:div.InputGroup
   [icon]
   [:input {:value       (key @values)
            :on-change   #(swap! values assoc key (.. % -target -value))
            :type        type
            :placeholder placeholder
            :required    true}]])

(defn sign-up-form [values]
  (let [error (r/atom nil)]
    (fn []
      [:div
       (when @error
         [:p.Label.Error (.-message @error)])
       [:form {:on-submit (fn [event]
                            (update-profile @values #(secretary/dispatch! "/") #(reset! error %))
                            (.preventDefault event))}
        [input-group :username :i.fas.fa-user "text" "Full Name" values]
        [input-group :email :i.fas.fa-envelope "text" "Email Address" values]
        [input-group :password-one :i.fas.fa-check "password" "Password" values]
        [input-group :password-two :i.fas.fa-check-double "password" "Confirm Password" values]
        [:button.Primary {:disabled (invalid? @values)
                          :type     "submit"} "Sign Up"]]])))

(defn sign-up []
  (let [values (r/atom {:username     ""
                        :email        ""
                        :password-one ""
                        :password-two ""})]
    [:div.Center
     [:div
      [:h2 "Welcome to Veryslide!"]
      [:p "Create an account and bake your ideas into awesome slides."]
      [sign-up-form values]]]))