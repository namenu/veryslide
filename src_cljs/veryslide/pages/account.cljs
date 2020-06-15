(ns veryslide.pages.account
  (:require [veryslide.fb.auth :refer [sign-out]]
            [veryslide.state :as state]
            [secretary.core :as secretary]))

(defn account []
  [:div
   [:h2 "Account Page"]
   [:p (str "Account: " (.-email @state/user))]
   [:hr]
   [:h3 "Change Password"]
   #_[:PasswordChangeForm]
   [:hr]
   [:button.Button {:type     "button"
                    :on-click (fn [e]
                                (sign-out #(secretary/dispatch! "/"))
                                (.preventDefault e))}
    "Sign Out"]
   #_[:SignOutButton]])