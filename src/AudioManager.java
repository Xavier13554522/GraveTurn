package src;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class AudioManager {
    private static AudioManager instance;
    private Clip backgroundClip;
    private boolean musicEnabled = true;
    private boolean effectsEnabled = true;

    private AudioManager() {
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void playBackgroundMusic(String fileName) {
        if (!musicEnabled) {
            return;
        }

        stopMusic();

        File file = resolveAudioFile(Paths.SOUNDS + "music/", fileName);
        if (file == null) {
            System.out.println("No se encontró la música: " + fileName);
            return;
        }

        playSound(file, true);
    }

    public void playEffect(String fileName) {
        if (!effectsEnabled) {
            return;
        }

        File file = resolveAudioFile(Paths.SOUNDS + "effects/", fileName);
        if (file == null) {
            System.out.println("No se encontró el efecto: " + fileName);
            return;
        }

        playSound(file, false);
    }

    private File resolveAudioFile(String folder, String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return null;
        }

        File direct = new File(folder + fileName);
        if (direct.exists()) {
            return direct;
        }

        String baseName = fileName;
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            baseName = fileName.substring(0, dotIndex);
        }

        String[] extensions = {".wav", ".WAV"};
        for (String ext : extensions) {
            File candidate = new File(folder + baseName + ext);
            if (candidate.exists()) {
                return candidate;
            }
        }

        String[] legacyExtensions = {".mp3", ".MP3"};
        for (String ext : legacyExtensions) {
            File candidate = new File(folder + baseName + ext);
            if (candidate.exists()) {
                System.out.println("Se encontró un archivo MP3: " + candidate.getPath() + ". Java no lo reproduce por defecto; conviértelo a WAV PCM.");
                return null;
            }
        }

        return null;
    }

    private void playSound(File audioFile, boolean loop) {
        new Thread(() -> {
            try {
                AudioInputStream stream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(stream);

                if (loop) {
                    backgroundClip = clip;
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    clip.start();
                }
            } catch (Exception ex) {
                if (audioFile.getName().toLowerCase().endsWith(".wav")) {
                    System.out.println("El WAV no es compatible o no está en formato PCM. Re-exporta el audio como WAV PCM de 16 bits y 44100 Hz.");
                }
                System.out.println("No se pudo reproducir el audio: " + audioFile.getPath() + " -> " + ex.getMessage());
            }
        }).start();
    }

    public void stopMusic() {
        if (backgroundClip != null) {
            backgroundClip.stop();
            backgroundClip.close();
            backgroundClip = null;
        }
    }

    public void setMusicEnabled(boolean enabled) {
        this.musicEnabled = enabled;
        if (!enabled) {
            stopMusic();
        }
    }

    public void setEffectsEnabled(boolean enabled) {
        this.effectsEnabled = enabled;
    }
}
