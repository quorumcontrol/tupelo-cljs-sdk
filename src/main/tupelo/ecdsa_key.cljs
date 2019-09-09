(ns tupelo.ecdsa-key
  (:require ["tupelo-wasm-sdk" :as sdk])
  (:require-macros [tupelo.internal :refer [defasyncfn]]))

(defn new [public-key-bits private-key-bits]
  (sdk/EcdsaKey. public-key-bits private-key-bits))

(defasyncfn generate []
            (. sdk/EcdsaKey generate))

(defasyncfn pass-phrase-key [phrase salt]
            (. sdk/EcdsaKey passPhraseKey phrase salt))

(defasyncfn from-bytes [bytes]
            (. sdk/EcdsaKey fromBytes bytes))

(defasyncfn key-addr [key]
            (. key keyAddr))