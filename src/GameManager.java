package src;

import javax.swing.*;

class GameManager {
    private boolean playerTurn = true;
    private boolean gameOver = false;
    private int turnCount = 0;
    private String winner;
    private CreateWindow createWindow;

    public GameManager(CreateWindow createWindow) {
        this.playerTurn = true;
        this.gameOver = false;
        this.turnCount = 0;
        this.winner = null;
        this.createWindow = createWindow;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void nextTurn() {
        playerTurn = !playerTurn;
        turnCount++;
    }

    public void checkWinner(Player player, Enemy enemy) {
        if (player.getHealth() <= 0) {
            gameOver = true;
            winner = "Enemy";
            checkGameOver(enemy);
        } else if (enemy.getHealth() <= 0) {
            gameOver = true;
            winner = "Player";
            checkGameOver(player);
        }
    }

    public String getWinner() {
        return winner;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void resetGame() {
        playerTurn = true;
        gameOver = false;
        winner = null;
        turnCount = 0;
    }

    public boolean getPlayerTurn() {
        return playerTurn;
    }

    public void checkGameOver(Character winnerPlayer) {
        if (gameOver) {
            AudioManager.getInstance().playEffect("dead");
            createWindow.setPanel(new PanelGameOver(createWindow, this, winnerPlayer, winner), "GameOver");
            Timer timer = new Timer(2000, e -> {
                createWindow.showPanel("GameOver");
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
}