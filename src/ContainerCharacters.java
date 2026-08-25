package src;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.OverlayLayout;
import java.awt.Dimension;
import java.awt.GridLayout;


public class ContainerCharacters extends JPanel {
    Frame frame;
    Frame frameEnemy;
    private final Timer timer;
    private final EffectsManager effectsManager;
    private final EffectsPanel effectsPanel;
    private int previousPlayerHealth;
    private int previousEnemyHealth;

    public ContainerCharacters(Player player, Enemy enemy) {
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(640, 150));

        JPanel charactersPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        charactersPanel.setOpaque(false);

        frame = new Frame(player);
        frameEnemy = new Frame(enemy);

        charactersPanel.add(frame);
        charactersPanel.add(frameEnemy);

        effectsManager = new EffectsManager();
        effectsPanel = new EffectsPanel(effectsManager);
        previousPlayerHealth = player.getHealth();
        previousEnemyHealth = enemy.getHealth();

        setLayout(new OverlayLayout(this));
        add(charactersPanel);
        add(effectsPanel, 0);

        timer = new Timer(100, e -> {
            player.update(100);
            enemy.update(100);
            detectDamage(player, enemy);
            effectsManager.update(100);
            frame.repaint();
            frameEnemy.repaint();
            effectsPanel.repaint();
        });
        timer.start();
    }

    private void detectDamage(Player player, Enemy enemy) {
        if (player.getHealth() < previousPlayerHealth) {
            if (!effectsManager.consumeBloodSuppression()) {
                addBloodEffect(getWidth() / 4);
            }
        }
        if (enemy.getHealth() < previousEnemyHealth) {
            if (!effectsManager.consumeBloodSuppression()) {
                addBloodEffect(getWidth() * 3 / 4);
            }
        }

        previousPlayerHealth = player.getHealth();
        previousEnemyHealth = enemy.getHealth();
    }

    private void addBloodEffect(int centerX) {
        effectsManager.playBlood(centerX, getHeight() / 2);
    }

    public void stopTimer() {
        timer.stop();
    }

    public EffectsManager getEffectsManager() {
        return effectsManager;
    }

}
