(ns veryslide.components.navigation)

(defn navigation []
  [:div.Navigation
   [:a.Brand {:href "/"} "Veryslide"]
   #_[:AuthUserContext.Consumer "{authUser =>
        authUser ?" [:NavigationAuth] ":" [:NavigationNonAuth] "}"]])