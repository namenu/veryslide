(ns veryslide.state
  (:require [reagent.core :as r]))

(def app-state (r/atom {}))

(def user (r/atom nil))
