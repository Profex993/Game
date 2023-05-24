package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * this class is for sounds
 */

public class SoundManager {

    //I used sound from the game Half-life, hope Valve does not find out :)
    Clip clip;
    URL[] soundUrl = new URL[10];

    /**
     * constructor for SoundManager class
     */
    public SoundManager() {
        soundUrl[0] = getClass().getClassLoader().getResource("sound/ost.wav");
        soundUrl[1] = getClass().getClassLoader().getResource("sound/button.wav");
        soundUrl[2] = getClass().getClassLoader().getResource("sound/death.wav");
        soundUrl[3] = getClass().getClassLoader().getResource("sound/explode.wav");
        soundUrl[4] = getClass().getClassLoader().getResource("sound/mortar.wav");
        soundUrl[5] = getClass().getClassLoader().getResource("sound/gun.wav");
        soundUrl[6] = getClass().getClassLoader().getResource("sound/gunDE.wav");
    }

    /**
     * this method selects an audio clip and starts playing it
     * @param opt is for selecting the audio clip
     */
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

    /**
     * this method selects an audio clip and starts to play it in loop
     * @param opt is used for selecting the audio clip
     */
    public void loop(int opt){
        try {
            AudioInputStream aIS = AudioSystem.getAudioInputStream(soundUrl[opt]);
            clip = AudioSystem.getClip();
            clip.open(aIS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        clip.loop(100);
    }
}