package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundManager {
    Clip clip;
    URL[] soundURL = new URL[6];

    public static final int FIRE = 0;
    public static final int THRUST = 1;
    public static final int SMALL_BANG = 2;
    public static final int LARGE_BANG = 3;
    public static final int GAME_OVER = 4;
    public static final int WHOOSH = 5;

    public SoundManager() {
        soundURL[FIRE] = getClass().getResource("/sounds/fire.wav");
        soundURL[THRUST] = getClass().getResource("/sounds/thrust.wav");
        soundURL[SMALL_BANG] = getClass().getResource("/sounds/bangSmall.wav");
        soundURL[LARGE_BANG] = getClass().getResource("/sounds/bangLarge.wav");
        soundURL[GAME_OVER] = getClass().getResource("/sounds/game_over.wav");
        soundURL[WHOOSH] = getClass().getResource("/sounds/whoosh.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audio);
        } catch (Exception _) {
        }
    }

    public void start() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }
}
