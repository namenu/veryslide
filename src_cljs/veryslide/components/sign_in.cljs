(ns veryslide.components.sign-in
  (:require [veryslide.fb.auth :as auth]))

(defn sign-in []
  [:p "Already have an account? "
   [:a {:on-click #(auth/sign-in)} "Sign in"] "."])
