(ns veryslide.fb.auth
  (:require ["firebase/app" :as firebase]
            [veryslide.state :as state]))

(defn current-user []
  (let [uid (.. (firebase/auth) -currentUser -uid)]
    (-> (firebase/firestore)
        (.collection "users")
        (.doc uid))))

(defn update-profile [{:keys [username email password-one]} on-success on-error]
  (let [auth (firebase/auth)]
    (-> auth
        (.createUserWithEmailAndPassword auth email password-one)
        (.then #(.. auth -currentUser (updateProfile #js {:displayName username})))
        (.then #(.set (current-user) #js {:username username :email email}))
        (.then on-success)
        (.catch on-error))))

(defn on-auth-state-changed []
  (.onAuthStateChanged
    (firebase/auth)
    (fn [user]
      (if user
        (reset! state/user user)
        (reset! state/user nil)))))

(defn sign-in [{:keys [email password]} on-success on-error]
  (-> (firebase/auth)
      (.signInWithEmailAndPassword email password)
      (.then on-success)
      (.catch on-error)))

(defn sign-out [cb]
  (.then (.signOut (firebase/auth))
         cb))