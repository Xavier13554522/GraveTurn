package src;

import java.util.Random;

public class Inteligence {
    private Random random = new Random();
    private Character player;
    private int playerHealth;
    private int playerDodgeChance;

    private Character ai;
    private int aiHealth;
    private int aiPotion;

    public Inteligence(Character player, Character ai) {
        this.player = player;
        this.playerHealth = player.getHealth();
        this.playerDodgeChance = player.getDodgeChance();

        this.ai = ai;
        this.aiHealth = ai.getHealth();
        this.aiPotion = ai.getPotion();
    }

    public String decideAction() {
        // Si la salud del AI es baja y tiene pociones, prioriza curarse
        if (aiHealth <= 30 && aiPotion > 0) {
            return "heal";
        }

        // Si la salud del jugador es baja, prioriza atacar
        if (playerHealth <= 30) {
            return "attack";
        }

        // Si el jugador tiene una alta probabilidad de esquivar, considera esquivar
        if (playerDodgeChance > 50) {
            return "dodge";
        }

        // Si ninguna de las condiciones anteriores se cumple, elige aleatoriamente
        // entre atacar o esquivar
        int action = random.nextInt(2); // 0 para atacar, 1 para esquivar
        return action == 0 ? "attack" : "dodge";
    }

    public boolean isDodgeAttack() {
        int chance = random.nextInt(100);
        boolean chanceProbably = chance < playerDodgeChance;
        if (chanceProbably) {
            return true; // El jugador esquiva el ataque
        } else {
            return false; // El jugador no esquiva el ataque
        }
    }

    public void updateAllStats(Character player, Character ai) {
        this.player = player;
        this.playerHealth = player.getHealth();
        this.playerDodgeChance = player.getDodgeChance();

        this.ai = ai;
        this.aiHealth = ai.getHealth();
        this.aiPotion = ai.getPotion();
    }
}
