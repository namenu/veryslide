(ns veryslide.pages.sign-in
  (:require [reagent.core :as r]
            [veryslide.fb.auth :as auth]
            [secretary.core :as secretary]))

(defn invalid? [{:keys [email password]}]
  (or (empty? email)
      (empty? password)))

(defn sign-in-form []
  (let [values (r/atom {:email    ""
                        :password ""})
        error  (r/atom nil)]
    (fn []
      [:div
       (when @error
         [:p.Label.Error (.-message @error)])
       [:form {:on-submit (fn [event]
                            (auth/sign-in @values #(secretary/dispatch! "/") #(reset! error %))
                            (.preventDefault event))}
        [:div.InputGroup
         [:i.fas.fa-envelope]
         [:input {:name        "email"
                  :value       (:email @values)
                  :on-change   #(swap! values assoc :email (.. % -target -value))
                  :type        "text"
                  :placeholder "Email Address"
                  :required    true}]]
        [:div.InputGroup
         [:i.fas.fa-key]
         [:input {:name        "password"
                  :value       (:password @values)
                  :on-change   #(swap! values assoc :password (.. % -target -value))
                  :type        "password"
                  :placeholder "Password"
                  :required    true}]]
        [:button.Primary {:disabled (invalid? @values) :type "submit"} "Sign In"]]])))

(defn sign-in []
  [:div.Center
   [:div
    [:h2 "SignIn"]
    [sign-in-form]
    [:hr]
    #_[:PasswordForgetLink]
    #_[:SignUpLink]]])