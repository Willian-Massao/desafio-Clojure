(ns routes.core
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [graphql.core :refer [graphql-handler]]
            [datomic.core :refer [conn]]
            [datomic.orm :as db])
  (:gen-class))

(def routes
  #{["/" :get (fn [_]
                    (try
                        {:status 200
                         :headers {"Content-Type" "text/html"
                                "Content-Security-Policy" "script-src 'nonce-randomNonceValue'"}
                         :body (slurp "resources/public/index.html")}
                    (catch Exception e
                        {:status 500
                         :body (str "Erro ao carregar página inicial: " (.getMessage e))})))
     :route-name :welcome]

    ["/graphql" :get (fn [_]
                        (try
                            {:status 200
                             :headers {"Content-Type" "text/html"
                                        "Content-Security-Policy" "script-src 'self' 'unsafe-inline'"}
                             :body (slurp "resources/public/graphql.html")}
                        (catch Exception e
                            {:status 500
                             :body (str "Erro ao carregar página GraphQL: " (.getMessage e))})))
     :route-name :graphql-page]

    ["/api/" :get (fn [_]
            (try
                (let [actual (or (db/read-number conn "teste") 0)]
                (db/create-if-not-exists conn "teste" actual)
                {:status 200
                 :body (str actual)})
            (catch Exception e
                (println "Erro em /api/: " (.getMessage e))
                {:status 500
                 :body (str "Erro ao ler contagem: " (.getMessage e))})))
            :route-name :get-count]

    ["/api/increment" :post (fn [_]
                                (try
                                    (let [actual (db/read-number conn "teste")
                                        new-number (inc actual)]
                                    (db/update-number conn "teste" new-number)
                                    {:status 200
                                     :body (str new-number)})
                                (catch Exception e
                                    {:status 500
                                     :body (str "Erro ao incrementar: " (.getMessage e))})))
     :route-name :increase-count]

    ["/api/decrease" :post (fn [_]
                                (try
                                    (let [actual (db/read-number conn "teste")
                                        new-number (dec actual)]
                                    (db/update-number conn "teste" new-number)
                                    {:status 200
                                     :body (str new-number)})
                                (catch Exception e
                                    {:status 500
                                     :body (str "Erro ao decrementar: " (.getMessage e))})))
     :route-name :decrease-count]

    ["/api/reset" :post (fn [_]
                            (try
                                (db/update-number conn "teste" 0)
                                {:status 200
                                 :body "0"}
                            (catch Exception e
                                {:status 500
                                 :body (str "Erro ao resetar: " (.getMessage e))})))
     :route-name :reset-count]

    ["/graphql" :post graphql-handler :route-name :graphql]})
