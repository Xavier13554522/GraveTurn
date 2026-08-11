package src;

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
            checkGameOver();
        } else if (enemy.getHealth() <= 0) {
            gameOver = true;
            winner = "Player";
            checkGameOver();
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

    public void checkGameOver() {
        if (gameOver) {
            AudioManager.getInstance().playEffect("dead");
            createWindow.showPanel("GameOver");
        }
    }
}