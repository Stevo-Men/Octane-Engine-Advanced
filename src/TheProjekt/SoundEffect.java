
package TheProjekt;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public enum SoundEffect {

    FIRE("audio/fire.wav",100),
    MURLOC("audio/murloc.wav",50);
    private int maxCooldown;
    private int cooldown;
    private String path;
    private AudioInputStream stream;

    SoundEffect(String path, int maxCooldown) {
        this.maxCooldown = maxCooldown;
        this.path = path;
    }

    public void play() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream(path));
            clip.open(stream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

