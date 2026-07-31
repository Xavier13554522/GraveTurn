package src;

import java.awt.Image;

public class Character {
    public enum State {
        IDLE, ATTACK, HURT, DEAD, DODGE
    }

    private String name;
    private String description;
    private int health;
    private int damage;

    private Animator<State> animator;
    private State state = State.IDLE;

    Character(String name, String description, int health, int damage, Animator<State> animator) {
        this.name = name;
        this.description = description;
        this.health = health;
        this.damage = damage;
        this.animator = animator;
    }

    public void receiveDamage(int damage) {
        if (this.health > 0) {
            this.health -= damage;
            setState(State.HURT);
        }
        if (this.health <= 0) {
            setState(State.DEAD);
        }
    }

    public void attack(Character target) {
        if (target.health > 0 && this.health > 0) {
            target.receiveDamage(this.damage);
            setState(State.ATTACK);
        }
        if (target.health <= 0) {
            setState(State.IDLE);
        }
    }

    public void heal(int amount) {
        this.health += amount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public void setAnimator(Animator<State> animator) {
        this.animator = animator;
    }

    public void setState(State newState) {
        if (this.state != newState) {
            this.state = newState;
            if (animator != null)
                animator.reset(newState);
        }
    }

    public State getState() {
        return state;
    }

    /**
     * Actualizar animador (llamar desde el bucle de juego / `Timer`).
     * 
     * @param deltaMillis tiempo en ms desde la última actualización
     */
    public void update(long deltaMillis) {
        if (animator != null)
            animator.update(deltaMillis, state);
        if (state == State.HURT && animator.getCompleted().getOrDefault(State.HURT, false)) {
            setState(State.IDLE);
        }
        if (state == State.ATTACK && animator.getCompleted().getOrDefault(State.ATTACK, false)) {
            setState(State.IDLE);
        }
    }

    public Image getCurrentFrame() {
        if (animator == null)
            return null;
        return animator.getCurrentFrame(state);
    }

    public int getCurrentFrameWidth() {
        Image frame = getCurrentFrame();
        return (frame != null) ? frame.getWidth(null) : 0;
    }

    public int getCurrentFrameHeight() {
        Image frame = getCurrentFrame();
        return (frame != null) ? frame.getHeight(null) : 0;
    }

}
