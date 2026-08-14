package src;

import javax.swing.Timer;

import src.Character.State;

public class Delay {
    static public void executeDelayCode(Enemy enemy, Player player, GameManager gameManager, Runnable onTurnFinished,
            Runnable actionRunnable) {
        Inteligence intelligence = new Inteligence(player, enemy);
        String actionEnemy = intelligence.decideAction();
        if (actionConditionAttack(actionEnemy, enemy, player, gameManager, onTurnFinished, intelligence)) {
            return;
        }
        Timer timer = new Timer(1000, e -> {
            if (gameManager.getGameOver() || gameManager.getPlayerTurn()) {
                return;
            }
            executeCode(enemy, player, gameManager, onTurnFinished, actionRunnable, intelligence, actionEnemy);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void executeCode(Enemy enemy, Player player, GameManager gameManager, Runnable onTurnFinished,
            Runnable actionRunnable, Inteligence intelligence, String actionEnemy) {
        if (player.getLastAction() != null && player.getLastAction().equals("dodge")) {
            dodgePlayerAction(actionRunnable, enemy, player, intelligence);
        } else {
            actionDecided(actionEnemy, enemy, player);
        }
        Timer timer = new Timer(1000, e2 -> {
            if (gameManager.getGameOver()) {
                return;
            }
            gameManager.nextTurn();
            onTurnFinished.run();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private static boolean actionConditionAttack(String actionEnemy, Enemy enemy, Player player,
            GameManager gameManager,
            Runnable onTurnFinished, Inteligence intelligence) {
        if (player.getLastAction() != null && player.getLastAction().equals("attack") && !actionEnemy.equals("dodge")) {
            player.attack(enemy);
            AudioManager.getInstance().playEffect("attack");
            return false;
        } else if (player.getLastAction() != null && player.getLastAction().equals("attack")
                && actionEnemy.equals("dodge")) {
            Timer timer = new Timer(1000, e -> {
                if (gameManager.getGameOver()) {
                    return;
                }
                dodgeEnemyAction(enemy, player, intelligence);
                if (!gameManager.getGameOver()) {
                    gameManager.nextTurn();
                }
                onTurnFinished.run();
            });
            timer.setRepeats(false);
            timer.start();
            return true;
        }
        return false;
    }

    private static void dodgePlayerAction(Runnable actionRunnable, Enemy enemy, Player player,
            Inteligence intelligence) {
        if (intelligence.isDodgeAttack()) {
            if (actionRunnable != null) {
                enemy.setState(State.ATTACK);
                AudioManager.getInstance().playEffect("attack");
                actionRunnable.run();
                AudioManager.getInstance().playEffect("dodge");
            }
        } else {
            enemy.setState(State.ATTACK);
            player.receiveDamage(player.getDamage() / 2);
            AudioManager.getInstance().playEffect("attack");
        }
    }

    private static void dodgeEnemyAction(Enemy enemy, Player player,
            Inteligence intelligence) {
        System.out.println("Player last action: " + player.getLastAction());
        if (intelligence.isDodgeAttack()) {
            player.setState(State.ATTACK);
            AudioManager.getInstance().playEffect("attack");
            enemy.Dodge();
            AudioManager.getInstance().playEffect("dodge");
        } else {
            player.setState(State.ATTACK);
            enemy.receiveDamage(enemy.getDamage() / 2);
            AudioManager.getInstance().playEffect("attack");
        }
    }

    private static void actionDecided(String actionEnemy, Enemy enemy, Player player) {
        if (actionEnemy.equals("attack")) {
            enemy.attack(player);
            AudioManager.getInstance().playEffect("attack");
        } else if (actionEnemy.equals("heal")) {
            enemy.heal();
            AudioManager.getInstance().playEffect("heal");
        } else {
            enemy.attack(player);
            AudioManager.getInstance().playEffect("attack");
        }
    }
}
