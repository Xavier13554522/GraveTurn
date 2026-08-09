package src;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

class PanelActions extends JPanel {

    public PanelActions(Player player, Enemy enemy) {
        this.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(520, 100));
        this.setLayout(new BorderLayout());
        ActionsContainer actionsContainer = new ActionsContainer(player, enemy);
        this.add(actionsContainer, BorderLayout.CENTER);
    }
}

class ActionsContainer extends JPanel {
    private final Button attackButton;
    private final Button dodgeButton;
    private final Button healButton;
    private final GameManager gameManager;

    public ActionsContainer(Player player, Enemy enemy) {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.gameManager = new GameManager();

        BackgroundPanel bg = new BackgroundPanel("action-table.png", 520, 100);
        bg.setLayout(new GridBagLayout());
        bg.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        attackButton = new Button(null, Paths.SelectSword("1.png"));
        attackButton.addActionListener(e -> {
            if (!gameManager.isPlayerTurn()) {
                return;
            }
            player.attack(enemy);
            gameManager.nextTurn();
            updateButtonsState();
            Delay.executeDelayCode(enemy, player, gameManager, this::updateButtonsState, null);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 10);
        bg.add(attackButton, gbc);

        dodgeButton = new Button(null, Paths.SelectShield("1.png"));
        dodgeButton.addActionListener(e -> {
            if (!gameManager.isPlayerTurn()) {
                return;
            }
            gameManager.nextTurn();
            updateButtonsState();
            Delay.executeDelayCode(enemy, player, gameManager, this::updateButtonsState, () -> player.Dodge());

        });

        gbc.gridx = 1;
        bg.add(dodgeButton, gbc);

        healButton = new Button(null, Paths.SelectPotion("1.png"));
        healButton.addActionListener(e -> {
            if (!gameManager.isPlayerTurn()) {
                return;
            }
            player.heal();
            gameManager.nextTurn();
            updateButtonsState();
            Delay.executeDelayCode(enemy, player, gameManager, this::updateButtonsState, null);
        });

        gbc.gridx = 2;
        bg.add(healButton, gbc);

        updateButtonsState();
        this.add(bg, BorderLayout.CENTER);
    }

    private void updateButtonsState() {
        boolean playerTurn = gameManager.isPlayerTurn();
        attackButton.setEnabled(playerTurn);
        dodgeButton.setEnabled(playerTurn);
        healButton.setEnabled(playerTurn);

        attackButton.revalidate();
        attackButton.repaint();
        dodgeButton.revalidate();
        dodgeButton.repaint();
        healButton.revalidate();
        healButton.repaint();
        this.revalidate();
        this.repaint();
    }
}
