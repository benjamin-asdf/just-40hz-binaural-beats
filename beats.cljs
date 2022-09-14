(ns beats)

(def a-note-freq 152.74)
(def binaural-beat-freq 40)
(def oscillators (atom []))
(defonce ctx (js/window.AudioContext.))

(def panners
  {:left
   (let [left? true]
     (let [panner (. ctx createStereoPanner)]
       (set! (.. panner -pan -value) (if left? -1 1))
       (.connect panner ctx.destination)
       panner))
   :right
   (let [left? nil]
     (let [panner (. ctx createStereoPanner)]
       (set! (.. panner -pan -value) (if left? -1 1))
       (.connect panner ctx.destination)
       panner))})

(defn oscillate [panner hz]
  (let [o (ctx.createOscillator)
        _ (set! (.- o type) "sine")
        _ (set! (.. o -frequency -value) hz)]
    (. o start)
    (. o (connect panner))
    (swap! oscillators conj o)))

(-> panners :right (oscillate (+ a-note-freq binaural-beat-freq)))
(-> panners :left (oscillate a-note-freq))
