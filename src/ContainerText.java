package src;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.GridLayout;

public class ContainerText extends JPanel {
    private Timer timer;

    public ContainerText(Player player, Enemy enemy) {
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(1280, 50));
        this.setLayout(new GridLayout(1, 2, 10, 0));
        createHealthBarCharacter(player);
        createHealthBarCharacter(enemy);

    }
    private void createHealthBarCharacter (Character character) {
        JPanel panelHealth = new JPanel();
        panelHealth.setOpaque(false);
        HealthBar pj = new HealthBar(character.getMaxHealth());
        panelHealth.setPreferredSize(new Dimension(600, 50));
        panelHealth.add(pj);
        timer = new Timer(100, e -> {
            pj.updateHealth(character.getHealth(), character.getMaxHealth());

        });
        timer.start();
        this.add(panelHealth);
    }
    public void stopTimer() {
        timer.stop();
    }
}
