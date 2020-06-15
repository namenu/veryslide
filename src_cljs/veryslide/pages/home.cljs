(ns veryslide.pages.home
  (:require [veryslide.state :as state]))


(defn slide-component [slide]
  )

(defn home []
  (let [slides []]
    [:div
     [:h2 "My slides"]
     [:ul
      (cond
        (nil? slides)
        [:p "Loading..."]

        (zero? (count slides))
        [:p "No slide found."]

        :else
        (for [slide slides]
          (let [url "_" #_(generate-path)]
            [:li {:key (:id slide)}
             [:a {:href url} [slide-component slide]]
             [:a {:on-click #(js/console.log (str "delete-slide" (:id slide)))}
              [:i.fas.fa-trash-alt]]])))]]))