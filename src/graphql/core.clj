(ns graphql.core
  (:require
   [com.walmartlabs.lacinia :as lacinia]
   [com.walmartlabs.lacinia.schema :as schema]
   [clojure.data.json :as json])
  (:gen-class))

(def contador (atom 0))

(def schema
    {:queries
        {:getCount
            {:type 'Int
             :resolve (fn [_ _ _]
                        @contador)}}

   :mutations
    {:increment
        {:type 'Int
         :resolve (fn [_ _ _]
                    (swap! contador inc))}

    :decrease
        {:type 'Int
         :resolve (fn [_ _ _]
                    (swap! contador dec))}

    :reset
        {:type 'Int
         :resolve (fn [_ _ _]
                    (reset! contador 0))}}})

(def compiled-schema (schema/compile schema))

(require '[clojure.data.json :as json])

(defn graphql-handler
    [request]
    (let [body-str (slurp (:body request))
            body-json (json/read-str body-str :key-fn keyword)
            query (:query body-json)
            variables (:variables body-json)
            result (lacinia/execute compiled-schema query variables nil)]
        {:status 200
        :headers {"Content-Type" "application/json"}
        :body (json/write-str result)}))

