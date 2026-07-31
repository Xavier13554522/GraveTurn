package src;
import javax.swing.Timer;
public class Delay {
    static public void executeDelayCode(Enemy enemy,Player player) {
    Timer timer = new Timer(2000, e -> {
        executeCode(enemy,player);
    });
    timer.start();
    timer.setRepeats(false); // que se ejecute solo una vez
    }

    public static void executeCode(Enemy enemy,Player player){
        enemy.attack(player);
    }

}
