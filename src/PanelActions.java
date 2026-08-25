package src;

import java.awt.*;
import javax.swing.JPanel;

class PanelActions extends JPanel {

        public PanelActions(Player player, Enemy enemy, GameManager gameManager,
            EffectsManager effectsManager) {
        this.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(640, 180));
        this.setLayout(new BorderLayout());
        ActionsContainer actionsContainer = new ActionsContainer(
            player, enemy, gameManager, effectsManager);
        this.add(actionsContainer, BorderLayout.CENTER);
    }
}