package TheProjekt;

import Octane.*;
import Octane.Canvas;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;

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
    private ArrayList<Knife> knives;
    private ArrayList<Enemy> enemies;
    ArrayList<StaticEntity> killedElements = new ArrayList<>();


    @Override
    protected void initialize() {
        gamePad = new GamePad();
        player = new Player(gamePad);
        player.teleport(200, 200);
        world = new World();
        world.load();
        camera = new Camera(cameraWidth, cameraHeight,2048,2048);
        enemies = new ArrayList<>();
        knives = new ArrayList<>();
        enemies.add(new Enemy());


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

        camera.update(player);

        if (soundCooldown > 0) {
            soundCooldown--;
        }


        if (gamePad.isFirePressed() && soundCooldown == 0) {
            knives.add(player.throwKnife());
            SoundEffect.FIRE.play();
            soundCooldown = SOUND_COOLDOWN_DURATION;
        }

        for (Enemy enemy : enemies) {
//            if (enemy.canAttack(player)) {
//                enemy.isAttacking = true;
//                enemy.attack(player);
//            } else {
//                enemy.isAttacking = false;
//            }
//            if (enemy.isChasing(player)) {
//                player.detectedState = npc.isChasing(player);
//            }
            //enemy.update(player);
            enemy.update();
        }


        for (Knife knife : knives) {
            if (knife.isOutOfBounds() || !knife.isFlying()) {
                killedElements.add(knife);
            }
              knife.update();

            for (Enemy enemy : enemies) {
                if (knife.hitBoxIntersectWith(enemy)) {
                    killedElements.add(knife);
                    enemy.isTouched(knife);


                    if (enemy.getHealth() <= 0) {
                        killedElements.add(enemy);
                    }
                }
            }
        }

        cleanUpEntities();
    }

    private void cleanUpEntities() {
        for (StaticEntity killedElement : killedElements) {
            if (killedElement instanceof Enemy) {
                enemies.remove(killedElement);
            } else if (killedElement instanceof Knife) {
                knives.remove(killedElement);
            }
        }
        CollidableRepository.getInstance().unregisterEntities(killedElements);
        killedElements.clear();
    }


    @Override
    protected void draw(Canvas canvas) {
        world.draw(canvas, -camera.getX(), -camera.getY());
        player.draw(canvas, -camera.getX(), -camera.getY());


        for (Enemy enemy : enemies) {
            enemy.draw(canvas, -camera.getX(), -camera.getY());
            canvas.drawRectangle(enemy, Color.RED);
        }

        for (Knife knife : knives) {
            knife.draw(canvas, -camera.getX(), -camera.getY());
            canvas.drawRectangle(knife, Color.RED);
        }

        canvas.drawRectangle(player,Color.BLUE);
    }
}