(ns routes.core
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [graphql.core :refer [graphql-handler]])
  (:gen-class))

(def contador (atom 0))

(def routes
  #{["/" :get (fn [_]
                {
                    :status 200
                    :headers {"Content-Type" "text/html", "Content-Security-Policy" "script-src 'nonce-randomNonceValue'"}
                    :body (slurp "resources/public/index.html")
                }) :route-name :welcome]
    ["/graphql" :get (fn [_]
                {
                    :status 200
                    :headers {"Content-Type" "text/html", "Content-Security-Policy" "script-src 'self' 'unsafe-inline'"}
                    :body (slurp "resources/public/graphql.html")
                }) :route-name :graphql-page]
    ["/api/" :get (fn [_]
                {
                    :status 200
                    :body (str @contador)
                }) :route-name :get-count]
    ["/api/increment" :post (fn [_]
                (swap! contador inc)
                {
                    :status 200
                    :body (str @contador)
                }) :route-name :increase-count]
    ["/api/decrease" :post (fn [_]
                (swap! contador dec)
                {
                    :status 200
                    :body (str @contador)
                }) :route-name :decrease-count]
    ["/api/reset" :post (fn [_]
                (reset! contador 0)
                {
                    :status 200
                    :body (str @contador)
                }) :route-name :reset-count]
    ["/graphql" :post graphql-handler :route-name :graphql]})
