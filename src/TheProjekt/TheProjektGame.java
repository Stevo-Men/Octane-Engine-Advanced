package TheProjekt;

import Octane.Canvas;
import Octane.Game;
import Octane.RenderingEngine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class TheProjektGame extends Game {

    private Player player;
    private GamePad gamePad;
    private World world;
    private Enemy enemy;
    private int soundCooldown;
    private static final int SOUND_COOLDOWN_DURATION = 30;
    private Camera camera;
    private int cameraWidth = 1200;
    private int cameraHeight = 900;

    @Override
    protected void initialize() {
        gamePad = new GamePad();
        player = new Player(gamePad);
        player.teleport(200, 200);
        world = new World();
        world.load();
        camera = new Camera(cameraWidth, cameraHeight);
        enemy = new Enemy();


        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(
                    this.getClass().getClassLoader().getResourceAsStream("audio/testMusicIntro.wav"));
            clip.open(stream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RenderingEngine.getInstance().getScreen().toggleFullscreen();
        RenderingEngine.getInstance().getScreen().hideCursor();
    }

    @Override
    protected void update() {
        if (gamePad.isQuitPressed()) {
            stopPlaying();
        }


        player.update();
        enemy.update();
        camera.update(player);

        if (soundCooldown > 0) {
            soundCooldown--;
        }

        if (gamePad.isFirePressed() && soundCooldown == 0) {
            SoundEffect.FIRE.play();
            soundCooldown = SOUND_COOLDOWN_DURATION;
        }
    }

    @Override
    protected void draw(Canvas canvas) {
        world.draw(canvas, -camera.getX(), -camera.getY());
        player.draw(canvas, -camera.getX(), -camera.getY());
        enemy.draw(canvas, -camera.getX(), -camera.getY());
    }
}