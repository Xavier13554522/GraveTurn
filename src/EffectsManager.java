package src;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EffectsManager {
    private final List<EffectInstance> effects = new ArrayList<>();
    private final Map<Object, Component> targets = new HashMap<>();
    private final Image[] bloodFrames = LoadedEffects.loadBloodFrames();
    private final Image[] dodgeFailedFrames = LoadedEffects.loadDodgeFrames();
    private final Image[] slashPlayerFrames = LoadedEffects.loadSlashsPlayerFrames();
    private final Image[] slashEnemyFrames = LoadedEffects.loadSlashsEnemyFrames();
    private final Image[] slashDeadPlayerFrames = LoadedEffects.loadSlashColor1Frames();
    private final Image[] slashDeadEnemyFrames = LoadedEffects.loadSlashColor3Frames();
    private boolean suppressNextBlood;

    public void bindTarget(Object owner, Component target) {
        if (owner != null && target != null) {
            targets.put(owner, target);
        }
    }

    private Component resolveTarget(Object owner) {
        return targets.get(owner);
    }

    public void playBlood(Object owner) {
        play(owner, bloodFrames, 50, 2.0, 0, 0);
    }

    public void suppressNextBlood() {
        suppressNextBlood = true;
    }

    public boolean consumeBloodSuppression() {
        boolean suppressed = suppressNextBlood;
        suppressNextBlood = false;
        return suppressed;
    }

    public void playDodgeFailed(Object owner) {
        play(owner, dodgeFailedFrames, 200, 3.0, 0, 0);
    }

    public void playSlashPlayer(Object owner) {
        play(owner, slashPlayerFrames, 50, 2.0, 0, -50);
    }

    public void playSlashEnemy(Object owner) {
        play(owner, slashEnemyFrames, 50, 2.0, 0, -50);
    }

    public void playSlashDeadPlayer(Object owner) {
        play(owner, slashDeadPlayerFrames, 50, 3.0, 0, 0);
    }

    public void playSlashDeadEnemy(Object owner) {
        play(owner, slashDeadEnemyFrames, 50, 3.0, 0, 0);
    }

    private void play(Object owner, Image[] frames, long frameDurationMillis, double scale,
            int offsetX, int offsetY) {
        Component target = resolveTarget(owner);
        if (target == null) {
            return;
        }
        add(frames, target, offsetX, offsetY, frameDurationMillis, scale);
    }

    public void add(Image[] frames, Component target, int offsetX, int offsetY,
            long frameDurationMillis, double scale) {
        if (frames == null || frames.length == 0 || frames[0] == null || target == null) {
            return;
        }

        effects.add(new EffectInstance(
                frames, target, offsetX, offsetY, frameDurationMillis, scale));
    }

    public void update(long deltaMillis) {
        Iterator<EffectInstance> iterator = effects.iterator();
        while (iterator.hasNext()) {
            EffectInstance effect = iterator.next();
            effect.update(deltaMillis);
            if (effect.isFinished()) {
                iterator.remove();
            }
        }
    }

    public void draw(Graphics2D graphics, Component reference) {
        for (EffectInstance effect : effects) {
            effect.draw(graphics, reference);
        }
    }
}