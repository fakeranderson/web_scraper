(ns web-scraper.core
  (:require [net.cgrand.enlive-html :as html]))


(defn -main
  []
  (def sony-store-url "https://store.playstation.com/en-us/grid/STORE-MSF77008-ALLGAMES/1?direction=desc&gameType=ps4_full_games&releaseDate=last_7_days&smcid=pdc%3Aen-us%3Aexplore-games-ps4-games%3Aprimary%2520nav%3Amsg-shop%3Adigital-games-and-services&sort=release_date")
  ; First grab the link from each title page
  (def website-sony-store (html/html-resource (java.net.URL. sony-store-url)))
  ; (print (html/select website-sony-store [:div.grid-cell__title :span])))
  (def selections (html/select website-sony-store [:a.internal-app-link]))
   (println (partition 3 selections))

  )