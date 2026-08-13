package src;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class Label extends JLabel {
    public Label(String text) {
        super(text);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 24));
    }
}
