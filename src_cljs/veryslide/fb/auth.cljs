(ns veryslide.fb.auth
  (:require ["firebase/app" :as firebase]
            [veryslide.state :as state]))

(defn sign-in []
  (firebase/auth.Goo))

(defn on-auth-state-changed []
  (.onAuthStateChanged
    (firebase/auth)
    (fn [user]
      (if user
        (reset! state/user user)
        (reset! state/user nil)))))