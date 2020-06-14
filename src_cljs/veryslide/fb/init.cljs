(ns veryslide.fb.init
  (:require ["firebase/app" :as firebase]
            ["firebase/auth"]
            ["firebase/firestore"]
            [veryslide.fb.auth :refer [on-auth-state-changed]]))

(defn firebase-init []
  (firebase/initializeApp
    #js {:apiKey "AIzaSyDSZtbmdUFBp2wEQXvbdIDZKpmU8EIk2J4"
         :authDomain "veryslide-d0643.firebaseapp.com"
         :databaseURL "https://veryslide-d0643.firebaseio.com"
         :projectId "veryslide-d0643"
         :storageBucket "veryslide-d0643.appspot.com"
         :messagingSenderId "264493638390"
         :appId "1:264493638390:web:71e104d400952700af02d7"})
  (on-auth-state-changed))
