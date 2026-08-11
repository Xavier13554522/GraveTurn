package src;
import javax.swing.Timer;

import src.Character.State;

public class Delay {
    static public void executeDelayCode(Enemy enemy, Player player, GameManager gameManager, Runnable onTurnFinished,Runnable actionRunnable) {
        Timer timer = new Timer(1000, e -> {
            if(gameManager.getGameOver()) {
                return;
            }
            executeCode(enemy, player, gameManager, onTurnFinished, actionRunnable);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void executeCode(Enemy enemy, Player player, GameManager gameManager, Runnable onTurnFinished,Runnable actionRunnable) {
        if(actionRunnable != null) {
            enemy.setState(State.ATTACK);
            AudioManager.getInstance().playEffect("attack");
            actionRunnable.run();
            AudioManager.getInstance().playEffect("dodge");
        }
        else{
            enemy.attack(player);
            AudioManager.getInstance().playEffect("attack");
        }
        gameManager.nextTurn();
        if (onTurnFinished != null) {
            onTurnFinished.run();
        }
    }
}
