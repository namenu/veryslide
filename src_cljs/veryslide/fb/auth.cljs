(ns veryslide.fb.auth
  (:require ["firebase/app" :as firebase]
            [secretary.core :as secretary]
            [veryslide.state :as state]))

(defn current-user []
  (let [uid (.. (firebase/auth) -currentUser -uid)]
    (-> (firebase/firestore)
        (.collection "users")
        (.doc uid))))

(defn update-profile [{:keys [username email password-one]} on-error]
  (let [auth (firebase/auth)]
    (-> (.createUserWithEmailAndPassword auth email password-one)
        (.then #(.. auth -currentUser (updateProfile #js {:displayName username})))
        (.then #(.set (current-user) #js {:username username :email email}))
        (.then #(secretary/dispatch! "/"))
        (.catch on-error))))

(defn on-auth-state-changed []
  (.onAuthStateChanged
    (firebase/auth)
    (fn [user]
      (if user
        (reset! state/user user)
        (reset! state/user nil)))))