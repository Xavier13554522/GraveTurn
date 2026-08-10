package src;

import java.awt.*;
import javax.swing.JPanel;

class PanelActions extends JPanel {

    public PanelActions(Player player, Enemy enemy, GameManager gameManager) {
        this.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(520, 100));
        this.setLayout(new BorderLayout());
        ActionsContainer actionsContainer = new ActionsContainer(player, enemy, gameManager);
        this.add(actionsContainer, BorderLayout.CENTER);
    }
}

class ActionsContainer extends JPanel {
    private final Button attackButton;
    private final Button dodgeButton;
    private final Button healButton;
    private final GameManager gameManager;
    private final Player player;
    private final Enemy enemy;

    public ActionsContainer(Player player, Enemy enemy, GameManager gameManager) {
        this.player = player;
        this.enemy = enemy;
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.gameManager = gameManager;

        BackgroundPanel bg = new BackgroundPanel("action-table.png", 520, 100);
        bg.setLayout(new GridBagLayout());
        bg.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        attackButton = createActionButton(bg, gbc, 0, Paths.SelectSword("1.png"), () -> player.attack(enemy), null);
        dodgeButton = createActionButton(bg, gbc, 1, Paths.SelectShield("1.png"), null, () -> player.Dodge());
        healButton = createActionButton(bg, gbc, 2, Paths.SelectPotion("1.png"), () -> player.heal(), null);

        updateButtonsState();
        this.add(bg, BorderLayout.CENTER);
    }

    private Button createActionButton(BackgroundPanel bg, GridBagConstraints gbc, int gridx, String iconPath,
            Runnable action, Runnable delayedAction) {
        Button button = new Button(null, iconPath);
        button.addActionListener(e -> executePlayerAction(action, delayedAction));

        gbc.gridx = gridx;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 10);
        bg.add(button, gbc);
        return button;
    }

    private void executePlayerAction(Runnable action, Runnable delayedAction) {
        if (!gameManager.isPlayerTurn()) {
            return;
        }

        if (action != null) {
            action.run();
        }

        gameManager.nextTurn();
        updateButtonsState();
        Delay.executeDelayCode(enemy, player, gameManager, this::updateButtonsState, delayedAction);
    }

    private void updateButtonsState() {
        boolean playerTurn = gameManager.isPlayerTurn();
        boolean playersPotions = player.getPotion() > 0;
        boolean isWinner = gameManager.getGameOver();
        gameManager.checkWinner(player, enemy);
        attackButton.setEnabled(playerTurn && !isWinner);
        dodgeButton.setEnabled(playerTurn && !isWinner);
        healButton.setEnabled(playerTurn && playersPotions && !isWinner);

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
