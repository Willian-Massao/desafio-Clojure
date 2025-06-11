(ns datomic.core
  (:require [datomic.api :as d]))

(def uri "datomic:free://localhost:4334/my-db")

(defn init-db []
  (when-not (d/create-database uri)
    (println "Banco de dados já existe"))
  (d/connect uri))

(def schema
  [{:db/ident       :number/uid
    :db/valueType   :db.type/long
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity}
   {:db/ident       :number/value
    :db/valueType   :db.type/long
    :db/cardinality :db.cardinality/one}])

(def conn (init-db))  ;

@(d/transact conn schema)

