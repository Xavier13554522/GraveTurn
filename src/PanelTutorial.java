package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelTutorial extends JPanel {
    private static final String BUTTON_PATH = Paths.BACKGROUND + "buttons/1/";
    private static final String NORMAL_BUTTON = BUTTON_PATH + "button.png";
    private static final String PRESSED_BUTTON = BUTTON_PATH + "press-button.png";
    private static final String ACTIVE_BUTTON = BUTTON_PATH + "active-button.png";

    public PanelTutorial(CreateWindow createWindow) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        BackgroundPanel bg = new BackgroundPanel("background.png", 1280, 720);
        bg.setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Tutorial");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(title);
        content.add(Box.createVerticalStrut(18));

        JTextArea rules = new JTextArea();
        rules.setEditable(false);
        rules.setOpaque(false);
        rules.setForeground(Color.WHITE);
        rules.setFont(new Font("Arial", Font.PLAIN, 18));
        rules.setLineWrap(true);
        rules.setWrapStyleWord(true);
        rules.setText(
                "- Attack: deals damage to the enemy.\n"
                        + "- Heal: restores health and consumes a potion.\n"
                        + "- Dodge: tries to avoid the enemy attack. If it succeeds, the attack is fully avoided.\n"
                        + "- If dodge fails, the failed parry icon appears and you only take half the damage.\n"
                        + "- If the enemy dodges your attack, your attack misses and the enemy avoids it completely.\n"
                        + "- Each action has its icon in the items folder: sword, dodge, potion and shield.\n"
                        + "- The sword is attack, the dodge icon is dodge, the potion is heal, and the shield represents defense."
        );
        content.add(rules);
        content.add(Box.createVerticalStrut(18));

        JPanel cards = new JPanel(new GridLayout(1, 4, 18, 0));
        cards.setOpaque(false);
        cards.add(createActionCard("Attack", Paths.SelectSword("1.png"), "Deals damage directly to the enemy."));
        cards.add(createActionCard("Dodge", Paths.SelectDodge("1.png"), "If successful, the attack is completely avoided."));
        cards.add(createActionCard("Failed parry", Paths.SelectDodge("2.png"), "If the player fails the parry, this icon appears and only half the damage is taken."));
        cards.add(createActionCard("Heal", Paths.SelectPotion("1") + "3.png", "Restores health and spends one potion."));
        content.add(cards);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottom.setOpaque(false);
        Button backButton = new Button("Back", null, NORMAL_BUTTON, 180, 50);
        configureButtonStyle(backButton);
        backButton.addActionListener(e -> {
            backButton.setBackgroundImage(PRESSED_BUTTON);
            createWindow.showPanel("Home");
        });
        bottom.add(backButton);
        content.add(Box.createVerticalStrut(22));
        content.add(bottom);

        bg.add(content, BorderLayout.CENTER);
        add(bg, BorderLayout.CENTER);
    }

    private JPanel createActionCard(String title, String iconPath, String description) {
        JPanel card = new JPanel();
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel iconLabel = new JLabel();
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaled = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaled));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea desc = new JTextArea(description);
        desc.setEditable(false);
        desc.setOpaque(false);
        desc.setForeground(Color.WHITE);
        desc.setFont(new Font("Arial", Font.PLAIN, 14));
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setFocusable(false);
        desc.setHighlighter(null);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        desc.setMargin(new Insets(0, 0, 0, 0));
        desc.setBackground(new Color(0, 0, 0, 0));
        desc.setCaretPosition(0);

        card.add(iconLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(desc);

        return card;
    }

    private void configureButtonStyle(Button button) {
        button.setMouseEvent(
                () -> button.setBackgroundImage(ACTIVE_BUTTON),
                () -> button.setBackgroundImage(NORMAL_BUTTON));
    }
}
