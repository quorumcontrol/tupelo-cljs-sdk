(ns tupelo.internal
  (:require [cljs.core.async :as async]))

(defmacro defasyncfn
  "Creates a wrapper fn for tupelo-wasm-sdk functions with get semantics."
  [name args & body]
  `(defn ~name ~args
     (let [c# (async/chan 1)]
       (-> (do ~@body)
           (.then #(async/put! c# %)))
       c#)))