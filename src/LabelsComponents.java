package src;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class LabelsComponents extends JLabel {
    public LabelsComponents(String text) {
        super(text, JLabel.CENTER);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 16));
    }
}
