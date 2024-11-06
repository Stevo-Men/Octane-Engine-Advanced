package TheProjekt;

import Octane.Canvas;
import Octane.ContrallableEntity;
import Octane.Direction;
import Octane.MovementController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends ContrallableEntity {
    private static final String SPRITE_PATH = "images/player2.png";
    private static final int ANIMATION_SPEED = 8;
    private int currentAnimationFrame = 1;
    private int nextFrame = ANIMATION_SPEED;
    private static final int FRAME_COUNT = 3;
    private BufferedImage image;
    private Image[] rightFrames;
    private Image[] leftFrames;
    private Image[] upFrames;
    private Image[] downFrames;
    private double scaleFactor = 2.0;
    private static int PLAYER_HEALTH = 100;
    int knifeMunition = 10;
    public  int playerHealth = 100;
    private int cooldown = 0;



    public Player(MovementController controller) {
        super(controller);
        setDimensions((int)(32 * scaleFactor), (int)(32 * scaleFactor));
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

    private Image[] loadFrames(int rowIndex) {
        Image[] frames = new Image[FRAME_COUNT];
        for (int i = 0; i < FRAME_COUNT; i++) {
            frames[i] = getScaledImage(image.getSubimage(i * 32, rowIndex * 32, 32, 32));
        }
        return frames;
    }

    private void loadAnimationFrames() {
        downFrames = loadFrames(0);
        leftFrames = loadFrames(1);
        rightFrames = loadFrames(2);
        upFrames = loadFrames(3);
    }


    private Image getScaledImage(BufferedImage img) {
        int scaledWidth = (int)(img.getWidth() * scaleFactor);
        int scaledHeight = (int)(img.getHeight() * scaleFactor);
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



        if (hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= leftFrames.length) {
                    currentAnimationFrame = 0;
                }
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 1;
        }
    }

    @Override
    public void draw(Canvas canvas, int offsetX, int offsetY) {


        if (getDirection() == Direction.RIGHT) {
            canvas.drawImage(rightFrames[currentAnimationFrame], cameraX(offsetX), cameraY(offsetY));
        } else if (getDirection() == Direction.LEFT) {
            canvas.drawImage(leftFrames[currentAnimationFrame], cameraX(offsetX), cameraY(offsetY));
        } else if (getDirection() == Direction.UP) {
            canvas.drawImage(upFrames[currentAnimationFrame], cameraX(offsetX), cameraY(offsetY));
        } else if (getDirection() == Direction.DOWN) {
            canvas.drawImage(downFrames[currentAnimationFrame], cameraX(offsetX), cameraY(offsetY));
        }


        canvas.drawRectangle(x + offsetX,y + offsetY - 10,50,5,Color.RED);
        canvas.drawRectangle(x + offsetX,y + offsetY - 10,PLAYER_HEALTH/2,5,Color.GREEN);

    }

    public int cameraX(int offsetX) {
        return x + offsetX;
    }



    public int cameraY(int offsetY) {
        return y + offsetY;
    }





}
