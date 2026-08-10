package src;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;

class PanelGameplay extends JPanel {
    public PanelGameplay(Player player, Enemy enemy, GameManager gameManager) {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(520, 220));
        this.setLayout(new BorderLayout());

        ContainerText containerText = new ContainerText(player, enemy);
        ContainerCharacters containerCharacters = new ContainerCharacters(player, enemy);
        containerCharacters.setOpaque(false);
        containerText.setOpaque(false);
        //
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setOpaque(false);
        LabelsComponents labelTurn = new LabelsComponents("Turno: " + (gameManager.isPlayerTurn() ? "Jugador" : "Enemigo"));
        container.add(labelTurn, BorderLayout.CENTER);

        Timer timer = new Timer(100, e -> {
            labelTurn.setText("Turno: " + (gameManager.isPlayerTurn() ? "Jugador" : "Enemigo"));
        });
        timer.start();
        //
        BackgroundPanel backgroundPanel = new BackgroundPanel("midnight.png", 520, 220);
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(containerText, BorderLayout.NORTH);
        backgroundPanel.add(containerCharacters, BorderLayout.CENTER);
        backgroundPanel.add(container, BorderLayout.SOUTH);

        this.add(backgroundPanel, BorderLayout.CENTER);
    }
}

class ContainerText extends JPanel {
    LabelsComponents labelPlayerHealth;
    LabelsComponents labelEnemyHealth;

    public ContainerText(Player player, Enemy enemy) {
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(520, 30));
        this.setLayout(new GridLayout(1, 2, 20, 0));
        this.labelPlayerHealth = new LabelsComponents("Vida: " + player.getHealth());
        this.labelEnemyHealth = new LabelsComponents("Vida: " + enemy.getHealth());

        Timer timer = new Timer(100, e -> {
            labelPlayerHealth.setText("Vida: " + player.getHealth());
            labelEnemyHealth.setText("Vida: " + enemy.getHealth());
        });
        timer.start();
        this.add(labelPlayerHealth);
        this.add(labelEnemyHealth);
    }
}

class ContainerCharacters extends JPanel {
    Frame frame;
    Frame frameEnemy;

    public ContainerCharacters(Player player, Enemy enemy) {
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(520, 190));
        this.setLayout(new GridLayout(1, 2, 20, 10));

        frame = new Frame(player);
        frameEnemy = new Frame(enemy);

        this.add(frame);
        this.add(frameEnemy);

        Timer timer = new Timer(100, e -> {
            player.update(100);
            enemy.update(100);
            frame.repaint();
            frameEnemy.repaint();
        });
        timer.start();
    }

}

class LabelsComponents extends JLabel {
    public LabelsComponents(String text) {
        super(text, JLabel.CENTER);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 16));
    }
}

class Frame extends JPanel {
    private final Character character;
    private final int frameWidth;
    private final int frameHeight;

    public Frame(Character character) {
        this.character = character;

        Image currentFrame = character.getCurrentFrame();
        this.frameWidth = (currentFrame != null ? currentFrame.getWidth(null) : 0) * 2;
        this.frameHeight = (currentFrame != null ? currentFrame.getHeight(null) : 0) * 2;

        this.setPreferredSize(new Dimension(Math.max(frameWidth, 1), Math.max(frameHeight, 1)));
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image frame = character.getCurrentFrame();
        if (frame != null) {
            int x = (getWidth() - frameWidth) / 2;
            int y = (getHeight() - frameHeight) / 2;
            g.drawImage(frame, x, y, frameWidth, frameHeight, this);
        }
    }
}
