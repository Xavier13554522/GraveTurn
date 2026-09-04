package src;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import java.awt.*;

public class ConfigPanel extends JPanel {
    private static final String BUTTON_PATH = Paths.BACKGROUND + "buttons/1/";
    private static final String NORMAL_BUTTON = BUTTON_PATH + "button.png";
    private static final String PRESSED_BUTTON = BUTTON_PATH + "press-button.png";
    private static final String ACTIVE_BUTTON = BUTTON_PATH + "active-button.png";

    private final JSpinner masterVolumeField;
    private final JSpinner musicVolumeField;
    private final JSpinner effectsVolumeField;
    private final JCheckBox fullscreenCheckBox;

    public ConfigPanel(CreateWindow createWindow) {
        setLayout(new BorderLayout());

        ConfigData config = loadConfigData();
        masterVolumeField = new JSpinner(new SpinnerNumberModel(config.masterVolume, 0, 100, 1));
        musicVolumeField = new JSpinner(new SpinnerNumberModel(config.musicVolume, 0, 100, 1));
        effectsVolumeField = new JSpinner(new SpinnerNumberModel(config.effectsVolume, 0, 100, 1));
        fullscreenCheckBox = new JCheckBox("Fullscreen", config.fullscreen);

        BackgroundPanel bg = new BackgroundPanel("background.png", 1280, 720);
        bg.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        LabelsComponents title = new LabelsComponents("Config");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 18, 0);
        bg.add(title, gbc);

        BackgroundPanel textBox = createTextBox(440, 210);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 18, 0);
        bg.add(textBox, gbc);

        addInput(textBox, "Volumen principal", masterVolumeField, 0);
        addInput(textBox, "Música", musicVolumeField, 1);
        addInput(textBox, "Efectos", effectsVolumeField, 2);

        GridBagConstraints checkGbc = new GridBagConstraints();
        checkGbc.gridx = 0;
        checkGbc.gridy = 3;
        checkGbc.anchor = GridBagConstraints.CENTER;
        checkGbc.insets = new Insets(10, 0, 0, 0);
        textBox.add(fullscreenCheckBox, checkGbc);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 18, 0));

        Button cancelButton = new Button("Cancel", null, NORMAL_BUTTON, 170, 50);
        configureButtonStyle(cancelButton);
        cancelButton.addActionListener(e -> createWindow.showPanel("Home"));

        Button acceptButton = new Button("Accept", null, NORMAL_BUTTON, 170, 50);
        configureButtonStyle(acceptButton);
        acceptButton.addActionListener(e -> {
            acceptConfig(createWindow);
        });

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(acceptButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        bg.add(buttonsPanel, gbc);

        add(bg, BorderLayout.CENTER);
    }

    private ConfigData loadConfigData() {
        try {
            return ConfigManager.load();
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ConfigData();
        }
    }

    private BackgroundPanel createTextBox(int width, int height) {
        BackgroundPanel textBox = new BackgroundPanel("textBox.png", width, height);
        textBox.setLayout(new GridBagLayout());
        return textBox;
    }

    private void addInput(BackgroundPanel textBox, String labelText, JComponent field, int row) {
        LabelsComponents label = new LabelsComponents(labelText);
        label.setHorizontalAlignment(SwingConstants.LEFT);

        GridBagConstraints labelGbc = new GridBagConstraints();
        labelGbc.gridx = 0;
        labelGbc.gridy = row;
        labelGbc.anchor = GridBagConstraints.WEST;
        labelGbc.insets = new Insets(12, 22, 4, 10);
        textBox.add(label, labelGbc);

        GridBagConstraints fieldGbc = new GridBagConstraints();
        fieldGbc.gridx = 1;
        fieldGbc.gridy = row;
        fieldGbc.fill = GridBagConstraints.HORIZONTAL;
        fieldGbc.weightx = 1;
        fieldGbc.anchor = GridBagConstraints.EAST;
        fieldGbc.insets = new Insets(10, 0, 4, 22);
        textBox.add(field, fieldGbc);
    }

    private void configureButtonStyle(Button button) {
        button.setMouseEvent(
                () -> button.setBackgroundImage(ACTIVE_BUTTON),
                () -> button.setBackgroundImage(NORMAL_BUTTON));
    }

    private void acceptConfig(CreateWindow createWindow) {
        try {
            ConfigData config = new ConfigData();
            config.masterVolume = getSpinnerValue(masterVolumeField, config.masterVolume);
            config.musicVolume = getSpinnerValue(musicVolumeField, config.musicVolume);
            config.effectsVolume = getSpinnerValue(effectsVolumeField, config.effectsVolume);
            config.fullscreen = fullscreenCheckBox.isSelected();

            ConfigManager.save(config);
            AudioManager.getInstance().setMusicVolume(config.musicVolume / 100.0f);
            AudioManager.getInstance().setEffectsVolume(config.effectsVolume / 100.0f);
            createWindow.applyConfiguration(config);
            createWindow.showPanel("Home");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private int getSpinnerValue(JSpinner spinner, int fallback) {
        Object value = spinner.getValue();
        if (value instanceof Number) {
            int parsed = ((Number) value).intValue();
            return Math.max(0, Math.min(100, parsed));
        }
        return fallback;
    }
}