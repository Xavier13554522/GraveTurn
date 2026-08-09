package src;
import javax.swing.Timer;

public class Delay {
    static public void executeDelayCode(Enemy enemy, Player player, GameManager gameManager, Runnable onTurnFinished,Runnable actionRunnable) {
        Timer timer = new Timer(2000, e -> {
            executeCode(enemy, player, gameManager, onTurnFinished, actionRunnable);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void executeCode(Enemy enemy, Player player, GameManager gameManager, Runnable onTurnFinished,Runnable actionRunnable) {
        enemy.attack(player);
        if(actionRunnable != null) {
            actionRunnable.run();
        }
        gameManager.nextTurn();
        if (onTurnFinished != null) {
            onTurnFinished.run();
        }
    }
}
