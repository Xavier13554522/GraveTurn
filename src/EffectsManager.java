package src;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EffectsManager {
    private final List<EffectInstance> effects = new ArrayList<>();
    private final Image[] bloodFrames = LoadedEffects.loadBloodFrames();
    private final Image[] dodgeFailedFrames = LoadedEffects.loadDodgeFrames();
    private final Image[] slashPlayerFrames = LoadedEffects.loadSlashsPlayerFrames();
    private final Image[] slashEnemyFrames = LoadedEffects.loadSlashsEnemyFrames();
    private final Image[] slashDeadPlayerFrames = LoadedEffects.loadSlashColor1Frames();
    private final Image[] slashDeadEnemyFrames = LoadedEffects.loadSlashColor3Frames();
    private boolean suppressNextBlood;

    public void playBlood(int centerX, int centerY) {
        add(bloodFrames, centerX, centerY, 50, 2.0);
    }

    public void suppressNextBlood() {
        suppressNextBlood = true;
    }

    public boolean consumeBloodSuppression() {
        boolean suppressed = suppressNextBlood;
        suppressNextBlood = false;
        return suppressed;
    }

    public void playDodgeFailed(int centerX, int centerY) {
        add(dodgeFailedFrames, centerX, centerY, 200, 3.0);
    }

    public void playSlashPlayer(int centerX, int centerY) {
        add(slashPlayerFrames, centerX, centerY, 50, 2.0);
    }

    public void playSlashEnemy(int centerX, int centerY) {
        add(slashEnemyFrames, centerX, centerY, 50, 2.0);
    }

    public void playSlashDeadPlayer(int centerX, int centerY) {
        add(slashDeadPlayerFrames, centerX, centerY, 50, 3.0);
    }

    public void playSlashDeadEnemy(int centerX, int centerY) {
        add(slashDeadEnemyFrames, centerX, centerY, 50, 3.0);
    }

    public void add(Image[] frames, int centerX, int centerY,
            long frameDurationMillis, double scale) {
        if (frames == null || frames.length == 0 || frames[0] == null) {
            return;
        }

        effects.add(new EffectInstance(
                frames, centerX, centerY, frameDurationMillis, scale));
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

    public void draw(Graphics2D graphics) {
        for (EffectInstance effect : effects) {
            effect.draw(graphics);
        }
    }
}