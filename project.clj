(defproject server "0.0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.pedestal/pedestal.service "0.5.9"]
                 [io.pedestal/pedestal.jetty "0.5.9"]
                 [com.walmartlabs/lacinia "1.2"]
                 [org.clojure/data.json "2.5.0"]
                 [com.datomic/datomic-free "0.9.5697"]
                 [com.h2database/h2 "2.1.214"]]
  :repositories [["datomic" "https://my.datomic.com/repo"]]
  :main ^:skip-aot server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
