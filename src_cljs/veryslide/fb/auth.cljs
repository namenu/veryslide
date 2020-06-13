(ns veryslide.fb.auth
  (:require ["firebase/app" :as firebase]
            ["firebase/auth"]
            [veryslide.state :as state]))

(defn sign-in []
  )

(defn update-profile [{:keys [username email password-one]} on-error]
  (let [auth (firebase/auth)]
    (-> auth
        (.createUserWithEmailAndPassword email password-one)
        (.then (fn [auth-user]
                 (js/console.log auth-user)
                 (.. auth -currentUser (updateProfile #js {:displayName username}))
                 (reset! state/user {:username username :email email})))
        (.then (fn [auth-user]
                 ))
        (.catch on-error))))

(defn on-auth-state-changed []
  (.onAuthStateChanged
    (firebase/auth)
    (fn [user]
      (if user
        (reset! state/user user)
        (reset! state/user nil)))))