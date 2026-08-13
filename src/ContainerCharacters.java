package src;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.GridLayout;

public class ContainerCharacters extends JPanel {
    Frame frame;
    Frame frameEnemy;

    public ContainerCharacters(Player player, Enemy enemy) {
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(640, 150));
        this.setLayout(new GridLayout(1, 2, 20, 10));

        frame = new Frame(player);
        frameEnemy = new Frame(enemy);

        this.add(frame);
        this.add(frameEnemy);

        Timer timer = new Timer(100, e -> {
            player.update(100);
            enemy.update(100);
            frame.repaint();
            frameEnemy.repaint();
        });
        timer.start();
    }

}
