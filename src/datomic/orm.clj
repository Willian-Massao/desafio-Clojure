(ns datomic.orm
  (:require [datomic.api :as d]))

(defn create-number [conn uid value]
  @(d/transact conn
    {:tx-data [{:entidade/uid uid
                :entidade/number value}]}))

(defn read-number [conn uid]
  (let [db (d/db conn)]
    (or (d/q '[:find ?v .
               :in $ ?uid
               :where
               [?e :entidade/uid ?uid]
               [?e :entidade/number ?v]]
             db uid)
        0)))

(defn update-number [conn uid value]
  (let [db (d/db conn)
        entid (ffirst
                (d/q '[:find ?e
                       :in $ ?uid
                       :where
                       [?e :entidade/uid ?uid]]
                     db uid))]
    (if entid
      (do
        (println "Atualizando entidade:" entid "com valor:" value)
        @(d/transact conn
           [{:db/id entid
             :entidade/number value}]))
      (println "Nenhuma entidade encontrada com uid:" uid))))

(defn create-if-not-exists [conn uid value]
  (let [db (d/db conn)
        entid (d/q '[:find ?e .
                     :in $ ?uid
                     :where
                     [?e :entidade/uid ?uid]]
                   db uid)]
    (if entid
      (do
        (println "Já existe, id:" entid)
        entid)
      (let [tx @(d/transact conn [{:entidade/uid uid
                                   :entidade/number value}])
            eid (-> tx :tempids vals first)]
        (println "Criado com id:" eid)
        eid))))
