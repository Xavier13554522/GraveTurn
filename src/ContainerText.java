package src;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.GridLayout;

public class ContainerText extends JPanel {
    LabelsComponents labelPlayerHealth;
    LabelsComponents labelEnemyHealth;
    private final Timer timer;

    public ContainerText(Player player, Enemy enemy) {
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(520, 50));
        this.setLayout(new GridLayout(1, 2, 20, 0));
        this.labelPlayerHealth = new LabelsComponents("Vida: " + player.getHealth());
        this.labelEnemyHealth = new LabelsComponents("Vida: " + enemy.getHealth());

        timer = new Timer(100, e -> {
            labelPlayerHealth.setText("Vida: " + player.getHealth());
            labelEnemyHealth.setText("Vida: " + enemy.getHealth());
        });
        timer.start();
        this.add(labelPlayerHealth);
        this.add(labelEnemyHealth);
    }

    public void stopTimer() {
        timer.stop();
    }
}
