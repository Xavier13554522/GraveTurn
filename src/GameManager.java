package src;

import javax.swing.*;

class GameManager {
    private boolean playerTurn = true;
    private boolean gameOver = false;
    private int turnCount = 0;
    private String winner;
    private CreateWindow createWindow;
    private int wins;

    public GameManager(CreateWindow createWindow) {
        try {
            SaveData saveData = SaveManager.load();
            if(!saveData.equals(null)){
                this.wins = saveData.wins;
            }
        } catch (Exception e) {
            this.wins = 1;
            e.printStackTrace();
        }
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
            wins++;
            saveNextRoundStats();
            checkGameOver(player);
        }
    }

    private void saveNextRoundStats() {
        SaveData saveData;
        try {
            saveData = SaveManager.load();
            if (saveData == null) {
                saveData = new SaveData();
                saveData.playerHealth = 100;
                saveData.playerDamage = 10;
                saveData.playerPotion = 3;
                saveData.enemyHealth = 100;
                saveData.enemyDamage = 10;
                saveData.enemyPotion = 3;
            }

            saveData.playerHealth *= saveData.wins;
            saveData.playerDamage *= saveData.wins;
            saveData.enemyHealth *= saveData.wins;
            saveData.enemyDamage *= saveData.wins;
            saveData.wins = wins;
            SaveManager.save(saveData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getWinner() {
        return winner;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public int getWins() {
        return wins;
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