(ns veryslide.components.sign-in)

(defn sign-in []
  [:p "Already have an account? "
   [:a {:to "{ROUTES.SIGNIN}"} "Sign in"] "."])
