package src;

import javax.swing.Timer;

import src.Character.State;

public class Delay {
    static public void executeDelayCode(Enemy enemy, Player player, GameManager gameManager, Runnable onTurnFinished,
            Runnable actionRunnable, EffectsManager effectsManager) {
        Inteligence intelligence = new Inteligence(player, enemy);
        String actionEnemy = intelligence.decideAction();
        if (actionConditionAttack(actionEnemy, enemy, player, gameManager, onTurnFinished,
                intelligence, effectsManager)) {
            return;
        }
        gameManager.checkWinner(player, enemy);
        Timer timer = new Timer(1000, e -> {
            if (gameManager.getGameOver() || gameManager.getPlayerTurn()) {
                return;
            }
            executeCode(enemy, player, gameManager, onTurnFinished, actionRunnable,
                    intelligence, actionEnemy, effectsManager);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void executeCode(Enemy enemy, Player player, GameManager gameManager, Runnable onTurnFinished,
            Runnable actionRunnable, Inteligence intelligence, String actionEnemy,
            EffectsManager effectsManager) {
        if (player.getLastAction() != null && player.getLastAction().equals("dodge")) {
            dodgePlayerAction(actionRunnable, enemy, player, intelligence, effectsManager);
        } else {
            actionDecided(actionEnemy, enemy, player, gameManager, effectsManager);
        }
        Timer timer = new Timer(1000, e2 -> {
            if (gameManager.getGameOver()) {
                return;
            }
            gameManager.nextTurn();
            onTurnFinished.run();
            gameManager.checkWinner(player, enemy);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private static boolean actionConditionAttack(String actionEnemy, Enemy enemy, Player player,
            GameManager gameManager,
            Runnable onTurnFinished, Inteligence intelligence, EffectsManager effectsManager) {
        if (player.getLastAction() != null && player.getLastAction().equals("attack") && !actionEnemy.equals("dodge")) {
            player.attack(enemy);
            if (!playDeathSlash(enemy, effectsManager)) {
                effectsManager.playSlashPlayer(enemy);
            }
            AudioManager.getInstance().playEffect("attack");
            return false;
        } else if (player.getLastAction() != null && player.getLastAction().equals("attack")
                && actionEnemy.equals("dodge")) {
            dodgeEnemyAction(enemy, player, intelligence, effectsManager);
            Timer timer = new Timer(1000, e -> {
                if (gameManager.getGameOver()) {
                    return;
                }
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
            Inteligence intelligence, EffectsManager effectsManager) {
        if (intelligence.isDodgeAttack()) {
            if (actionRunnable != null) {
                enemy.setState(State.ATTACK);
                AudioManager.getInstance().playEffect("attack");
                actionRunnable.run();
                AudioManager.getInstance().playEffect("dodge");
            }
        } else {
            enemy.setState(State.ATTACK);
            effectsManager.suppressNextBlood();
            player.receiveDamage(player.getDamage() / 2);
            if (!playDeathSlash(player, effectsManager)) {
                effectsManager.playDodgeFailed(player);
            }
            AudioManager.getInstance().playEffect("attack");
        }
    }

    private static void dodgeEnemyAction(Enemy enemy, Player player,
            Inteligence intelligence, EffectsManager effectsManager) {
        if (intelligence.isDodgeAttack()) {
            player.setState(State.ATTACK);
            AudioManager.getInstance().playEffect("attack");
            enemy.Dodge();
            AudioManager.getInstance().playEffect("dodge");
        } else {
            player.setState(State.ATTACK);
            effectsManager.suppressNextBlood();
            enemy.receiveDamage(enemy.getDamage() / 2);
            if (!playDeathSlash(enemy, effectsManager)) {
                effectsManager.playDodgeFailed(enemy);
            }
            AudioManager.getInstance().playEffect("attack");
        }
    }

    private static void actionDecided(String actionEnemy, Enemy enemy, Player player, GameManager gameManager,
            EffectsManager effectsManager) {
        if (gameManager.getGameOver() || gameManager.getPlayerTurn()) {
            return;
        }
        if (actionEnemy.equals("attack")) {
            enemy.attack(player);
            if (!playDeathSlash(player, effectsManager)) {
                effectsManager.playSlashEnemy(player);
            }
            AudioManager.getInstance().playEffect("attack");
        } else if (actionEnemy.equals("heal")) {
            enemy.heal();
            AudioManager.getInstance().playEffect("heal");
        } else {
            enemy.attack(player);
            if (!playDeathSlash(player, effectsManager)) {
                effectsManager.playSlashEnemy(player);
            }
            AudioManager.getInstance().playEffect("attack");
        }
    }

    private static boolean playDeathSlash(Character character, EffectsManager effectsManager) {
        if (character.getState() != State.DEAD) {
            return false;
        }

        if (character instanceof Player) {
            effectsManager.playSlashDeadPlayer(character);
        } else {
            effectsManager.playSlashDeadEnemy(character);
        }
        return true;
    }
}
