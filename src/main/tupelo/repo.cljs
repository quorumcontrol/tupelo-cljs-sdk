(ns tupelo.repo
  (:require ["tupelo-wasm-sdk" :as sdk]
            ["interface-datastore" :refer [MemoryDatastore]]
            [cljs.core.async :refer [take!] :require-macros [go]])
  (:require-macros [tupelo.internal :refer [defasyncfn]]))

(defn assoc-if-nil [m k v]
  (if (nil? (get m k))
    (assoc m k v)
    m))

(defn memory-config [base-opts]
  (-> base-opts
     (dissoc :storage)
     (assoc-if-nil :lock "memory")
     (update :storageBackends
             #(-> %
                  (assoc-if-nil :root MemoryDatastore)
                  (assoc-if-nil :blocks MemoryDatastore)
                  (assoc-if-nil :keys MemoryDatastore)
                  (assoc-if-nil :datastore MemoryDatastore)))))

(defn new [name opts]
  (let [sdk-opts (clj->js (if (= (:storage opts) :memory)
                            (memory-config opts)
                            opts))]
    (sdk/Repo. name sdk-opts)))

(defasyncfn init [repo]
  (. repo init #js {}))

(defasyncfn open [repo]
  (. repo open))

(defn init-and-open [name opts]
  (let [repo (new name opts)]
    (go
     (-> repo
         init
         (take! open)
         (take! (fn [_] repo))))))
