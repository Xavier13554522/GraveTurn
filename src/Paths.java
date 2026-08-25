package src;

public class Paths {
    //RAIZ
    public static final String ASSETS = "assets/";
    public static final String SPRITES = ASSETS + "sprites/";
    public static final String BACKGROUND = ASSETS + "background/";
    public static final String ITEMS = ASSETS + "items/";
    public static final String SOUNDS = ASSETS + "sounds/";
    //SPRITES
    public static final String PLAYER = SPRITES + "characters/";
    public static final String ENEMIES = SPRITES + "enemies/";
    //ITEMS
    public static final String POTIONS = ITEMS + "potions/";
    public static final String SHIELDS = ITEMS + "shields/";
    public static final String SWORD = ITEMS + "swords/";
    public static final String DODGE = ITEMS + "dodges/";
    //Data
    public static final String DATA = "data/";
    //Save
    public static final String SAVE = "data/save/";
    //Config
    public static final String CONFIG = "data/config/";
    //THAT'S FUNCTIONS ARE USED TO SELECT THE PATH OF THE ASSETS
    public static String SelectBackground (String backgroundName){
        return BACKGROUND + backgroundName;
    }
    public static String SelectCharacter (String characterName){
        return PLAYER + characterName + "/";
    }
    public static String SelectEnemy (String enemyName){
        return ENEMIES + enemyName + "/";
    }
    public static String SelectPotion (String potionName){
        return POTIONS + potionName + "/";
    }
    public static String SelectShield (String shieldName){
        return SHIELDS + shieldName + "/";
    }
    public static String SelectSword (String swordName){
        return SWORD + swordName + "/";
    }
    public static String SelectDodge(String dodgeName){
        return DODGE + dodgeName + "/";
    }
}
