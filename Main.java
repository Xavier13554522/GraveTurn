import javax.swing.*;
import src.CreateWindow;
public class Main {
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            CreateWindow window = new CreateWindow();
            window.setVisible(true);
        });
    }
}
