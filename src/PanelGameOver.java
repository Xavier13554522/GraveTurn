package src;

import java.awt.*;
import javax.swing.JPanel;

public class PanelGameOver extends JPanel {
    private static final String BUTTON_PATH = Paths.BACKGROUND + "buttons/1/";
    private static final String NORMAL_BUTTON = BUTTON_PATH + "button.png";
    private static final String PRESSED_BUTTON = BUTTON_PATH + "press-button.png";
    private static final String ACTIVE_BUTTON = BUTTON_PATH + "active-button.png";

    public PanelGameOver(CreateWindow createWindow, GameManager gameManager, Character winner, String winnerName) {
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(640, 480));
        AudioManager.getInstance().stopMusic();

        BackgroundPanel bg = new BackgroundPanel("backgroundGameOver.jpg", 640, 480);
        bg.setLayout(new GridBagLayout());

        BackgroundPanel winnerBox = createTextBox(250, 100);
        LabelsComponents winnerTitle = new LabelsComponents("Game Over! Winner:");
        LabelsComponents winnerLabel = new LabelsComponents(winnerName);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        addText(winnerBox, winnerTitle, 0, 5);
        addText(winnerBox, winnerLabel, 1, 0);
        addToBackground(bg, winnerBox, 0, 0, 1, new Insets(0, 5, 8, 5));

        BackgroundPanel statsBox = createTextBox(350, 225);
        LabelsComponents statsTitle = new LabelsComponents("Winner stats:");
        addText(statsBox, statsTitle, 0, 5);

        addText(statsBox, new LabelsComponents("Total turns: " + gameManager.getTurnCount()), 1, 0);
        addText(statsBox, new LabelsComponents("accDamage: " + winner.getAccDamage()), 2, 0);
        addText(statsBox, new LabelsComponents("accPotion: " + winner.getAccPotion()), 3, 0);
        addText(statsBox, new LabelsComponents("accDodge: " + winner.getAccDodge()), 4, 0);
        addText(statsBox, new LabelsComponents("accReceiveDamage: " + winner.getAccReceiveDamage()), 5, 0);
        addText(statsBox, new LabelsComponents("Health left: " + winner.getHealth()), 6, 0);
        addToBackground(bg, statsBox, 0, 1, 1, new Insets(0, 5, 8, 5));

        Button buttonBack = new Button("Back", null, NORMAL_BUTTON, 200, 60);
        buttonBack.setMouseEvent(
                () -> buttonBack.setBackgroundImage(ACTIVE_BUTTON),
                () -> buttonBack.setBackgroundImage(NORMAL_BUTTON));
        buttonBack.addActionListener(e -> {
            buttonBack.setBackgroundImage(PRESSED_BUTTON);
            createWindow.showPanel("Home");
        });
        addToBackground(bg, buttonBack, 1, 0, 2, new Insets(0, 0, 0, 0));
        this.add(bg, BorderLayout.CENTER);
    }

    private BackgroundPanel createTextBox(int width, int height) {
        BackgroundPanel textBox = new BackgroundPanel("textBox.png", width, height);
        textBox.setLayout(new GridBagLayout());
        return textBox;
    }

    private void addText(BackgroundPanel textBox, LabelsComponents label, int row, int topInset) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(topInset, 0, 4, 0);
        textBox.add(label, gbc);
    }

    private void addToBackground(
            JPanel background, Component component, int row, int column, int columnWidth, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = column;
        gbc.gridy = row;
        gbc.gridwidth = columnWidth;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = insets;
        background.add(component, gbc);
    }

}
