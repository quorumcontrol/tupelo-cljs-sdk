(ns tupelo.community
  (:require ["tupelo-wasm-sdk" :as sdk])
  (:require-macros [tupelo.internal :refer [defasyncfn]]))

(defn new [node group repo]
  (sdk/Community. node group repo))

(defasyncfn wait-for-start [community]
  (. community waitForStart))

(defasyncfn get-current-state [community did]
  (. community getCurrentState did))

(defasyncfn get-tip [community did]
  (. community getTip did))

(defasyncfn send-token-and-get-payload [community tree tx]
  (. community sendTokenAndGetPayload tree tx))

(defasyncfn play-transactions [community tree transactions]
  (. community playTransactions tree transactions))

(defasyncfn next-update [community]
  (. community nextUpdate))

(defasyncfn start [community]
  (. community start))

(defasyncfn subscribe-to-tips [community]
  (. community subscribeToTips))

(defasyncfn get-default [& [repo]]
  (if repo
    (. sdk/Community getDefault repo)
    (. sdk/Community getDefault)))
