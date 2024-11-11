package TheProjekt;

import Octane.Canvas;
import Octane.ContrallableEntity;
import Octane.Direction;
import Octane.MovementController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Player extends ContrallableEntity {
    private static final String SPRITE_PATH = "images/player2.png";
    private static final int ANIMATION_SPEED = 8;
    private int currentAnimationFrame = 1;
    private int nextFrame = ANIMATION_SPEED;
    private static final int FRAME_COUNT = 3;
    private BufferedImage image;
    private Map<Direction, Image[]> directionFrames;
    private double SCALE_FACTOR= 2.0;
    private static int PLAYER_MAX_HEALTH = 100;
    int knifeMunition = 10;
    public  int playerHealth = 100;
    private int cooldown = 0;



    public Player(MovementController controller) {
        super(controller);
        setDimensions((int)(32 * SCALE_FACTOR), (int)(32 * SCALE_FACTOR));
        setSpeed(4);
        load();
    }

    public Knife throwKnife() {
        cooldown = 50;
        knifeMunition--;
        //soundEffect.KNIFE_THROW.play();
        return new Knife(this);
    }


    public boolean canThrow() {
        return cooldown == 0 && knifeMunition > 0;
    }


    private void load() {
        loadSpriteSheet();
        loadAnimationFrames();
    }



    private void loadAnimationFrames() {
        directionFrames = new HashMap<>();
        directionFrames.put(Direction.DOWN, loadFrames(0));
        directionFrames.put(Direction.LEFT, loadFrames(1));
        directionFrames.put(Direction.RIGHT, loadFrames(2));
        directionFrames.put(Direction.UP, loadFrames(3));
    }


    private Image[] loadFrames(int rowIndex) {
        Image[] frames = new Image[FRAME_COUNT];
        for (int i = 0; i < FRAME_COUNT; i++) {
            frames[i] = getScaledImage(image.getSubimage(i * 32, rowIndex * 32, 32, 32));
        }
        return frames;
    }

    private Image getScaledImage(BufferedImage img) {
        int scaledWidth = (int)(img.getWidth() * SCALE_FACTOR);
        int scaledHeight = (int)(img.getHeight() * SCALE_FACTOR);
        BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, img.getType());

        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(img, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        return scaledImage;
    }

    private void loadSpriteSheet() {
        try {
            image = ImageIO.read(
                    this.getClass().getClassLoader().getResourceAsStream(SPRITE_PATH)
            );
        } catch (IOException e) {
            System.err.println("Error loading player sprite sheet: " + e.getMessage());
            e.printStackTrace();
        }
    }



    @Override
    public void update() {
        super.update();
        moveWithController();

        if (cooldown > 0) {
            cooldown--;
        }

        if (hasMoved()) {
            nextFrame--;
            if (nextFrame == 0) {
                currentAnimationFrame = (currentAnimationFrame + 1) % FRAME_COUNT;
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 0; // Reset to idle frame when not moving
        }
    }


    @Override
    public void draw(Canvas canvas, int offsetX, int offsetY) {
        Image[] frames = directionFrames.get(getDirection());
        canvas.drawImage(frames[currentAnimationFrame], cameraX(offsetX), cameraY(offsetY));
        drawHealthBar(canvas, offsetX, offsetY);
    }

    private void drawHealthBar(Canvas canvas, int offsetX, int offsetY) {
        int barWidth = 50;
        canvas.drawRectangle(x + offsetX, y + offsetY - 10, barWidth, 5, Color.RED);
        int healthWidth = (playerHealth * barWidth) / PLAYER_MAX_HEALTH;
        canvas.drawRectangle(x + offsetX, y + offsetY - 10, healthWidth, 5, Color.GREEN);
    }

    public int cameraX(int offsetX) {
        return x + offsetX;
    }



    public int cameraY(int offsetY) {
        return y + offsetY;
    }





}
