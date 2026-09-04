package src;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class EffectsPanel extends JPanel {
    private final EffectsManager effectsManager;

    public EffectsPanel(EffectsManager effectsManager) {
        this.effectsManager = effectsManager;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics.create();
        effectsManager.draw(graphics2D, this);
        graphics2D.dispose();
    }
}