package src;

import java.awt.Image;
import java.util.Map;
import java.util.HashMap;

/**
 * Animator maneja secuencias de frames por estado (genérico).
 * - Se usa por ejemplo con un enum `State` del `Player`.
 */
public class Animator<S> {
    private final Map<S, Image[]> framesByState;
    private final long frameDurationMillis;
    private final Map<S, Boolean> oneTimeByState = new HashMap<>();

    // estado por-state: índice y temporizador
    private final Map<S, Integer> indices = new HashMap<>();
    private final Map<S, Long> timers = new HashMap<>();
    private final Map<S, Boolean> completed = new HashMap<>();

    public Animator(Map<S, Map<Image[], Boolean>> framesCompletedMap, long frameDurationMillis) {
        this.framesByState = new HashMap<>();
        this.frameDurationMillis = frameDurationMillis;

        for (Map.Entry<S, Map<Image[], Boolean>> entry : framesCompletedMap.entrySet()) {
            S state = entry.getKey();
            Map<Image[], Boolean> framesMap = entry.getValue();
            if (framesMap.isEmpty()) {
                continue;
            }
            Image[] frames = framesMap.keySet().iterator().next();
            Boolean oneTime = framesMap.values().iterator().next();

            this.framesByState.put(state, frames);
            this.oneTimeByState.put(state, oneTime != null && oneTime);
            indices.put(state, 0);
            timers.put(state, 0L);
            completed.put(state, false);
        }
    }

    /**
     * Actualiza el state dado el tiempo transcurrido (ms).
     */
    public void update(long deltaMillis, S state) {
        Image[] frames = framesByState.get(state);
        if (frames == null || frames.length == 0)
            return;

        boolean stateOneTime = oneTimeByState.getOrDefault(state, false);
        if (stateOneTime && completed.getOrDefault(state, false)) {
            // Si ya se completó la animación oneTime, se mantiene en el último frame
            indices.put(state, frames.length - 1);
            timers.put(state, 0L);
            return;
        }

        long t = timers.getOrDefault(state, 0L) + deltaMillis;
        int idx = indices.getOrDefault(state, 0);
        int count = frames.length;

        while (t >= frameDurationMillis) {
            t -= frameDurationMillis;
            idx++;
            if (idx >= count) {
                if (stateOneTime) {
                    // Animación oneTime terminada: marcar y mantener en el último frame
                    completed.put(state, true);
                    idx = count - 1; // Mantener en el último frame
                    t = 0L;
                    break;
                }
                idx = 0;
            }
        }

        timers.put(state, t);
        indices.put(state, idx);
    }

    public Map<S, Boolean> getCompleted() {
        return completed;
    }

    /** Obtiene el frame actual para el estado. */
    public Image getCurrentFrame(S state) {
        Image[] frames = framesByState.get(state);
        if (frames == null || frames.length == 0)
            return null;
        int idx = indices.getOrDefault(state, 0);
        if (idx < 0 || idx >= frames.length)
            idx = 0;
        return frames[idx];
    }

    /** Reinicia la animación de un estado (útil al cambiar de estado). */
    public void reset(S state) {
        indices.put(state, 0);
        timers.put(state, 0L);
        completed.put(state, false);
    }
}
