package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundManager {
    Clip clip;
    URL[] soundUrl = new URL[10];

    public SoundManager() {
        soundUrl[0] = getClass().getClassLoader().getResource("sound/ost.wav");
        soundUrl[1] = getClass().getClassLoader().getResource("sound/button.wav");
        soundUrl[2] = getClass().getClassLoader().getResource("sound/death.wav");
        soundUrl[3] = getClass().getClassLoader().getResource("sound/explode.wav");
        soundUrl[4] = getClass().getClassLoader().getResource("sound/mortar.wav");
        soundUrl[5] = getClass().getClassLoader().getResource("sound/gun.wav");
        soundUrl[6] = getClass().getClassLoader().getResource("sound/gunDE.wav");
    }

    public void play(int opt) {
        try {
            AudioInputStream aIS = AudioSystem.getAudioInputStream(soundUrl[opt]);
            clip = AudioSystem.getClip();
            clip.open(aIS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        clip.start();
    }
}