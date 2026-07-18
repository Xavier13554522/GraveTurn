package src;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints; 
import java.awt.Insets;             

public class PanelInitialize extends JPanel {
    
    public PanelInitialize(CreateWindow createWindow) {
        this.setBackground(Color.BLACK);
        this.setLayout(new GridBagLayout());
        
        // Objeto para dar las instrucciones de posición
        GridBagConstraints gbc = new GridBagConstraints();
        
        // 1. CONFIGURACIÓN DEL LABEL (Fila 0)
        Label label = new Label("Juegazo");
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 0; // Fila 0 (Arriba)
        gbc.insets = new Insets(0, 0, 20, 0);
        
        this.add(label, gbc);
        
        // 2. CONFIGURACIÓN DEL BOTÓN (Fila 1)
        Button button = new Button("Play");
        
        button.addActionListener(e -> {
            createWindow.showPanel("Game");
        });
        
        gbc.gridx = 0; 
        gbc.gridy = 1; 
        gbc.insets = new Insets(0, 0, 20, 0); 
        
        this.add(button, gbc);

        // 3. CONFIGURACIÓN DEL PANEL (Fila 2)
        Button buttonExit = new Button("Exit");
        buttonExit.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(buttonExit, "¿Estás seguro de que quieres salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);

        this.add(buttonExit, gbc);
    }
}

class Button extends JButton {
    public Button(String text) {
        super(text);
        this.setPreferredSize(new java.awt.Dimension(100, 50));
        this.setFont(new Font("Arial", Font.BOLD, 18));
        this.setBackground(Color.GRAY);
        this.setForeground(Color.WHITE);
        this.setFocusable(false);
    }
}

class Label extends JLabel {
    public Label(String text) {
        super(text);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 24));
    }
}