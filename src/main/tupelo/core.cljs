(ns tupelo.core
  (:require [tupelo.community :as community]
            [tupelo.ecdsa-key :as ecdsa-key]
            [tupelo.chaintree :as chaintree]
            [tupelo.transactions :as tx]
            [cljs.core.async :refer [<!] :refer-macros [go]]))

(defn main! [& args]
  (let [community-chan (community/get-default)
        key-chan (ecdsa-key/generate)]
    (go
     (let [community (<! community-chan)
           key (<! key-chan)]
       (println "Community is" community)
       (println "Key is" key)
       (println "Key addr is" (<! (ecdsa-key/key-addr key)))
       (let [ct (<! (chaintree/new-empty-tree (.-blockservice community) key))]
         (println "ChainTree is" ct)
         (<! (tx/set-data community ct "test" "hello world"))
         (let [data (<! (chaintree/resolve ct ["tree" "data" "test"]))]
           (println "Resolved data is" (.-value data))))))))
