package src;

public class SaveData {
    //Player
    public int playerHealth;
    public int playerDamage;
    public int playerPotion;
    public int playerMaxHealth;
    //Enemy
    public int enemyHealth;
    public int enemyDamage;
    public int enemyPotion;
    public int enemyMaxHealth;
    public int wins;

    public SaveData() {
    }

    public SaveData(Player player, Enemy enemy, int wins) {
        //player
        this.playerHealth = player.getHealth();
        this.playerDamage = player.getDamage();
        this.playerPotion = player.getPotion();
        this.playerMaxHealth = player.getHealth() + (20 * player.getPotion());
        //Enemy
        this.enemyHealth = enemy.getHealth();
        this.enemyDamage = enemy.getDamage();
        this.enemyPotion = enemy.getPotion();
        this.enemyMaxHealth = enemy.getHealth() + (20 * enemy.getPotion());
        //Gamemanager
        this.wins = wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}