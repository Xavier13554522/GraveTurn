package src;

import java.awt.*;
import javax.swing.JPanel;

public class ActionsContainer extends JPanel {
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

        BackgroundPanel bg = new BackgroundPanel("action-table.png", 640, 180);
        bg.setLayout(new GridBagLayout());
        bg.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        attackButton = createActionButton(bg, gbc, 0, Paths.SelectSword("1.png"),
                () -> {
                    player.setLastAction("attack");
                }, null);
        dodgeButton = createActionButton(bg, gbc, 1, Paths.SelectShield("1.png"), () -> player.setLastAction("dodge"),
                () -> player.Dodge());
        healButton = createActionButton(bg, gbc, 2, Paths.SelectPotion("1") + "3.png", () -> player.heal(), null);
        updateButtonsState();
        this.add(bg, BorderLayout.CENTER);
    }

    private Button createActionButton(BackgroundPanel bg, GridBagConstraints gbc, int gridx, String iconPath,
            Runnable action, Runnable delayedAction) {
        Button button = new Button(null, iconPath, null,80,80);
        button.setMouseEvent(
            () -> button.setIconColor(new Color(255, 215, 80)),
            () -> button.setIconColor(null));
        button.addActionListener(e -> executePlayerAction(action, delayedAction));
        gbc.gridx = gridx;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(8, 12, 0, 12);
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
        gameManager.checkWinner(player, enemy);
        boolean playerTurn = gameManager.isPlayerTurn();
        boolean playersPotions = player.getPotion() > 0;
        boolean isWinner = gameManager.getGameOver();
        attackButton.setEnabled(playerTurn && !isWinner);
        dodgeButton.setEnabled(playerTurn && !isWinner);
        healButton.setEnabled(playerTurn && !isWinner && playersPotions);

        if (playersPotions) {
            if (player.getPotion() >= 3) {
                healButton.setIcon(Paths.SelectPotion("1") + "3.png");
            } else if (player.getPotion() == 2) {
                healButton.setIcon(Paths.SelectPotion("1") + "2.png");
            } else {
                healButton.setIcon(Paths.SelectPotion("1") + "1.png");
            }

        }
    }
}
