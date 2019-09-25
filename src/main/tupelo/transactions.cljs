(ns tupelo.transactions
  (:require [tupelo.community :as community]
            ["tupelo-wasm-sdk" :as sdk])
  (:require-macros [tupelo.internal :refer [defasyncfn]]))

(defn set-data [community tree path value]
  (community/play-transactions
   community tree
   [(sdk/setDataTransaction path value)]))

(defn set-ownership [community tree new-owner-keys]
  (community/play-transactions
   community tree
   [(sdk/setOwnershipTransaction (clj->js new-owner-keys))]))

(defn establish-token [community tree name maximum]
  (community/play-transactions
   community tree
   [(sdk/establishTokenTransaction name maximum)]))

(defn mint-token [community tree name amount]
  (community/play-transactions
   community tree
   [(sdk/mintTokenTransaction name amount)]))

(defn send-token [community tree send-id name amount destination-chain-id]
  (community/play-transactions
   community tree
   [(sdk/sendTokenTransaction send-id name amount destination-chain-id)]))

(defn receive-token [community tree send-id tip signature leaves]
  (community/play-transactions
   community tree
   [(sdk/receiveTokenTransaction send-id tip signature leaves)]))

(defn receive-token-from-payload [community tree token-payload]
  (community/play-transactions
   community tree
   [(sdk/receiveTokenTransactionFromPayload token-payload)]))