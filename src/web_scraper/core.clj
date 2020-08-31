(ns web-scraper.core
  (:gen-class)
  (:require [cheshire.core :as json])
  (:require [clojure.string :as str])
  (:require [clojure.pprint])
  (:require [net.cgrand.enlive-html :as html]))

(def pp clojure.pprint/pprint)

(def sony-store-root "https://store.playstation.com")
(def sony-store-url "https://store.playstation.com/en-us/grid/STORE-MSF77008-ALLGAMES/1?direction=desc&gameType=ps4_full_games&releaseDate=last_7_days&smcid=pdc%3Aen-us%3Aexplore-games-ps4-games%3Aprimary%2520nav%3Amsg-shop%3Adigital-games-and-services&sort=release_date")

(defn fetch-selections
  []
  (let [website-sony-store (html/html-resource (java.net.URL. sony-store-url))
        selections (html/select website-sony-store [:a.internal-app-link])]
   ;(print (html/select website-sony-store [:div.grid-cell__title :span]))
   (set (for [part (partition 3 selections)
              el part]
          (str sony-store-root (get-in el [:attrs :href]))))))

(defn -main
  []
  (let [selections (fetch-selections)]
    (spit "out.json"
      (json/generate-string
        (for [url selections
              :let [page (html/html-resource (java.net.URL. url))
                    title (first (html/select page [:title]))
                    desc (html/select page [:div.pdp__description :p html/text-node])]
              :when (and title (not-empty desc))]
          {:title (str/trim (first (:content title))) :description (str/join "\n" desc)})))))

