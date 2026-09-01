package src;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class AudioManager {
    private static AudioManager instance;
    private Clip backgroundClip;
    private boolean musicEnabled = true;
    private boolean effectsEnabled = true;
    private float musicVolume = 1.0f; // 0.0f = silencio, 1.0f = volumen máximo
    private float effectsVolume = 1.0f;

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

        playSound(file, true, musicVolume);
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

        playSound(file, false, effectsVolume);
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

        String[] extensions = { ".wav", ".WAV" };
        for (String ext : extensions) {
            File candidate = new File(folder + baseName + ext);
            if (candidate.exists()) {
                return candidate;
            }
        }

        String[] legacyExtensions = { ".mp3", ".MP3" };
        for (String ext : legacyExtensions) {
            File candidate = new File(folder + baseName + ext);
            if (candidate.exists()) {
                System.out.println("Se encontró un archivo MP3: " + candidate.getPath()
                        + ". Java no lo reproduce por defecto; conviértelo a WAV PCM.");
                return null;
            }
        }

        return null;
    }

    private void playSound(File audioFile, boolean loop, float volume) {
        new Thread(() -> {
            try {
                AudioInputStream stream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                applyVolume(clip, volume);

                if (loop) {
                    backgroundClip = clip;
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    clip.start();
                }
            } catch (Exception ex) {
                if (audioFile.getName().toLowerCase().endsWith(".wav")) {
                    System.out.println(
                            "El WAV no es compatible o no está en formato PCM. Re-exporta el audio como WAV PCM de 16 bits y 44100 Hz.");
                }
                System.out.println("No se pudo reproducir el audio: " + audioFile.getPath() + " -> " + ex.getMessage());
            }
        }).start();
    }

    private void applyVolume(Clip clip, float volume) {
        try {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            float gain = min + (max - min) * Math.min(Math.max(volume, 0f), 1f);
            gainControl.setValue(gain);
        } catch (IllegalArgumentException ignored) {
            // El clip no soporta control de volumen, no hacer nada
        }
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

    public void setMusicVolume(float volume) {
        this.musicVolume = Math.min(Math.max(volume, 0f), 1f);
        if (backgroundClip != null) {
            applyVolume(backgroundClip, this.musicVolume);
        }
    }

    public void setEffectsVolume(float volume) {
        this.effectsVolume = Math.min(Math.max(volume, 0f), 1f);
    }

    public void setMasterVolume(float volume) {
        this.effectsVolume = Math.min(Math.max(volume, 0f), 1f);
        this.musicVolume = Math.min(Math.max(volume, 0f), 1f);
        if (backgroundClip != null) {
            applyVolume(backgroundClip, this.musicVolume);
        }
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public float getEffectsVolume() {
        return effectsVolume;
    }
}
