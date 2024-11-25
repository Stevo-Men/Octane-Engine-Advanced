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
    private int cameraWidth = 1280;
    private int cameraHeight = 800;
    private ArrayList<Knife> knives;
    private ArrayList<Enemy> enemies;
    ArrayList<StaticEntity> killedElements = new ArrayList<>();


    @Override
    protected void initialize() {
        gamePad = new GamePad();
        camera = new Camera(cameraWidth, cameraHeight);
        player = new Player(gamePad);
        player.teleport(640, 400);
        world = new World();
        world.load();
        enemies = new ArrayList<>();
        knives = new ArrayList<>();
        enemies.add(new Enemy(player,200,200));
        enemies.add(new Enemy(player,700,400));



//        try {
//            Clip clip = AudioSystem.getClip();
//            AudioInputStream stream = AudioSystem.getAudioInputStream(
//                    this.getClass().getClassLoader().getResourceAsStream("audio/testMusicIntro.wav"));
//            clip.open(stream);
//            clip.loop(Clip.LOOP_CONTINUOUSLY);
//            clip.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        RenderingEngine.getInstance().getScreen().toggleFullscreen();
        RenderingEngine.getInstance().getScreen().hideCursor();
    }

    @Override
    protected void update() {
        if (gamePad.isQuitPressed()) {
            stopPlaying();
        }

        player.update();


        // Calculate camera offset before updating it
        int previousCameraX = camera.getX();
        int previousCameraY = camera.getY();
        camera.update(player);
        int cameraOffsetX = previousCameraX - camera.getX();
        int cameraOffsetY = previousCameraY - camera.getY();



        if (soundCooldown > 0) {
            soundCooldown--;
        }


        if (gamePad.isFirePressed() && soundCooldown == 0) {
            knives.add(player.throwKnife());
            SoundEffect.FIRE.play();
            soundCooldown = SOUND_COOLDOWN_DURATION;
        }

        // Update enemies and adjust their positions based on camera movement
        for (Enemy enemy : enemies) {
            enemy.update();
          //  enemy.moveWithOffset(cameraOffsetX, cameraOffsetY); // Adjust enemy position directly
        }



        for (Knife knife : knives) {
            if ( !knife.isFlying()) {
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
        player.draw(canvas, camera);




        for (Enemy enemy : enemies) {
            enemy.draw(canvas, camera);
           canvas.drawRectangleMap(enemy, Color.RED);
        }

        for (Knife knife : knives) {
            knife.draw(canvas);
            canvas.drawRectangleMap(knife, Color.RED);
        }

        canvas.drawText("Camera X: "+ camera.getX()+"Camera Y: " + camera.getY(),100,700);
        canvas.drawRectangleMap(player,Color.BLUE);
        canvas.drawText("Player X: " + player.getX() + "Player Y: " + player.getY(),100,800);



    }
}