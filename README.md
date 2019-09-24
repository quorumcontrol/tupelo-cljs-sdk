# Tupelo ClojureScript SDK

A wrapper of the [tupelo-wasm-sdk](https://github.com/quorumcontrol/tupelo-wasm-sdk).

Note: in the words of IPFS: this project is still in Alpha, lots of development is happening, API might change, beware of the Dragons 🐉.

See https://docs.quorumcontrol.com/ for an overview of Tupelo, the whitepaper, other SDKs, etc. For a high-level walkthrough see this video: https://youtu.be/4Oz03l9IQPc which uses the Tupelo ChainTree Explorer described below.

## Examples

We have a collection of examples in the examples/ directory, which should help you get up to speed quickly on various aspects of the SDK.

## Tupelo ChainTree Explorer

We have made a main demo app based on the SDK, the 
[Tupelo ChainTree Explorer](https://github.com/quorumcontrol/wasm-explorer). This lets you explore
ChainTrees in the Tupelo testnet, and should be a great reference for learning how to use
the Tupelo Wasm SDK in depth!

## API

TODO: Generate API docs from source code & comments.

## Getting Started

The following snippet of code is all you need to send a transaction to our Testnet:

```clojure
(ns my-project.core
  (:require [tupelo.community :as community]
            [tupelo.ecdsa-key :as ecdsa-key]
            [tupelo.chaintree :as chaintree]
            [tupelo.transactions :as tx]
            [cljs.core.async :refer [<!] :refer-macros [go]]))

(defn set-data []
  (let [community-chan (community/get-default) ; get connection to default community and Tupelo TestNet
        key-chan (ecdsa-key/generate)] ; generate a new public/private keypair
    (go
     (let [community (<! community-chan)
           key (<! key-chan)]
       ;; create a new empty tree with the new keypair
       (let [ct (<! (chaintree/new-empty-tree (.-blockservice community) key))]
         ;; play a transaction on the testnet
         (<! (tx/set-data community ct "path" true))
           )))))
```

You can also find ChainTrees by their DID and resolve data on them, easily:

```clojure
(ns my-project.core
  (:require [tupelo.community :as community]
            [tupelo.ecdsa-key :as ecdsa-key]
            [tupelo.chaintree :as chaintree]
            [tupelo.transactions :as tx]
            [cljs.core.async :refer [<!] :refer-macros [go]]))

(defn resolve-data []
  (let [community-chan (community/get-default)]
    (go
     (let [community (<! community-chan)
           tip (community/get-tip community "did:tupelo:0xD1a9826f3A06d393368C9949535De802A35cD6b2")
           tree (chaintree/new {:store (.-blockservice community), :tip tip})
           resolved (chaintree/resolve tree ["tree" "data" "path"])]
       (println "Resolved data is:" (.-value resolved))))))

;; prints "Resolved data is: true"
```