package src;

public class Player extends Character {
    public static final Runnable Dodge = null;
    
    public Player(String name, String description, int health, int damage, int potion, Animator<State> animator) {
        super(name, description, health, damage, potion, animator);
    }
}
