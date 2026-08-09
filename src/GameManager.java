package src;

class GameManager {
    private boolean playerTurn = true;
    private boolean gameOver = false;
    private int turnCount = 0;
    private String winner;

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
        } else if (enemy.getHealth() <= 0) {
            gameOver = true;
            winner = "Player";
        }
    }

    public String getWinner() {
        return winner;
    }

    public boolean getGameOver() {
        return gameOver;
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

}