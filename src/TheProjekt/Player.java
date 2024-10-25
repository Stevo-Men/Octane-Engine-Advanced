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
    private BufferedImage image;
    private Image[] rightFrames;
    private Image[] leftFrames;
    private Image[] upFrames;
    private Image[] downFrames;
    private double scaleFactor = 2.0;

    public Player(MovementController controller) {
        super(controller);
        setDimensions((int)(32 * scaleFactor), (int)(32 * scaleFactor));
        setSpeed(4);
        load();
    }

    private void load() {
        loadSpriteSheet();
        loadAnimationFrames();
    }

    private void loadAnimationFrames() {
        downFrames = new Image[3];
        downFrames[0] = getScaledImage(image.getSubimage(0, 0, 32, 32));
        downFrames[1] = getScaledImage(image.getSubimage(32, 0, 32, 32));
        downFrames[2] = getScaledImage(image.getSubimage(64, 0, 32, 32));

        leftFrames = new Image[3];
        leftFrames[0] = getScaledImage(image.getSubimage(0, 32, 32, 32));
        leftFrames[1] = getScaledImage(image.getSubimage(32, 32, 32, 32));
        leftFrames[2] = getScaledImage(image.getSubimage(64, 32, 32, 32));

        rightFrames = new Image[3];
        rightFrames[0] = getScaledImage(image.getSubimage(0, 64, 32, 32));
        rightFrames[1] = getScaledImage(image.getSubimage(32, 64, 32, 32));
        rightFrames[2] = getScaledImage(image.getSubimage(64, 64, 32, 32));

        upFrames = new Image[3];
        upFrames[0] = getScaledImage(image.getSubimage(0, 96, 32, 32));
        upFrames[1] = getScaledImage(image.getSubimage(32, 96, 32, 32));
        upFrames[2] = getScaledImage(image.getSubimage(64, 96, 32, 32));
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
    public void draw(Canvas canvas) {
        draw(canvas, 0, 0);
    }

    public void draw(Canvas canvas, int offsetX, int offsetY) {
        if (getDirection() == Direction.RIGHT) {
            canvas.drawImage(rightFrames[currentAnimationFrame], x + offsetX, y + offsetY);
        } else if (getDirection() == Direction.LEFT) {
            canvas.drawImage(leftFrames[currentAnimationFrame], x + offsetX, y + offsetY);
        } else if (getDirection() == Direction.UP) {
            canvas.drawImage(upFrames[currentAnimationFrame], x + offsetX, y + offsetY);
        } else if (getDirection() == Direction.DOWN) {
            canvas.drawImage(downFrames[currentAnimationFrame], x + offsetX, y + offsetY);
        }
    }
}
