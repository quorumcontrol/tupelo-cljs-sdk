(ns tupelo.chaintree
  (:require ["tupelo-wasm-sdk" :as sdk])
  (:require-macros [tupelo.internal :refer [defasyncfn]])
  (:refer-clojure :exclude [resolve]))

(defn new [opts]
  (sdk/ChainTree. (clj->js opts)))

(defasyncfn new-empty-tree [block-service key]
  (. sdk/ChainTree newEmptyTree block-service key))

(defasyncfn id [tree]
  (. tree id))

(defasyncfn resolve [tree path]
  (. tree resolve (clj->js path)))

