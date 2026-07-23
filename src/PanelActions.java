package src;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

class PanelActions extends JPanel {
    public PanelActions(Player player, Enemy enemy) {
        this.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(520, 100));
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        // Configurar los botones
        Button button1 = new Button(null,Paths.SelectSword("1.png"));
        button1.addActionListener(e -> {
            player.attack(enemy);
            enemy.receiveDamage(player.getDamage());
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 10); // Espaciado entre botones
        this.add(button1, gbc);

        Button button2 = new Button(null,Paths.SelectShield("1.png"));
        button2.addActionListener(e -> {
            enemy.attack(player);
            player.receiveDamage(enemy.getDamage());
        });
        gbc.gridx = 1;
        this.add(button2, gbc);

        Button button3 = new Button(null,Paths.SelectPotion("1.png"));
        button3.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "has atacado a un chavista" + player.getDescription(), "Chavismo",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        gbc.gridx = 2;
        this.add(button3, gbc);
    }
}
