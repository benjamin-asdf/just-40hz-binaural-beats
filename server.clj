(require '[sci.nrepl.browser-server :as nrepl])
(nrepl/start! {:nrepl-port 1339 :websocket-port 1340})
