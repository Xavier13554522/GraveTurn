package src;

public class Enemy extends Character {
    public Enemy(String name, String description, int health, int damage, int potion, Animator<State> animator) {
        super(name, description, health, damage, potion, animator);
    }

}
