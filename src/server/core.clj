(ns server.core
  (:require 
   [io.pedestal.http :as http]
   [io.pedestal.http.route :as route]
   [routes.core :refer [routes]])
  (:gen-class))

(def service 
  {:env                 :prod
   ::http/routes        (route/expand-routes routes)
   ::http/resource-path "/public"
   ::http/type          :jetty
   ::http/host          "0.0.0.0"
   ::http/port          3000})

(defn start-server! []
    (-> service
        http/create-server
        http/start))

(defn -main [& args]
    (println "Starting server on port 3000...")
    (start-server!))
