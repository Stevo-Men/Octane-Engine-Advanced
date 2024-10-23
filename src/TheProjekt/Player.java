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

    public Player(MovementController controller) {
        super(controller);
        setDimensions(32,32);
        setSpeed(3);
        load();
    }

    private void load() {
        loadSpriteSheet();
        loadAnimationFrames();
    }

    private void loadAnimationFrames() {
            downFrames = new Image[3];
            downFrames[0] = image.getSubimage(0, 0, width, height);
            downFrames[1] = image.getSubimage(32, 0, width, height);
            downFrames[2] = image.getSubimage(64, 0, width, height);

            leftFrames = new Image[3];
            leftFrames[0] = image.getSubimage(0, 32, width, height);
            leftFrames[1] = image.getSubimage(32, 32, width, height);
            leftFrames[2] = image.getSubimage(64, 32, width, height);

            rightFrames = new Image[3];
            rightFrames[0] = image.getSubimage(0, 64, width, height);
            rightFrames[1] = image.getSubimage(32, 64, width, height);
            rightFrames[2] = image.getSubimage(64, 64, width, height);

            upFrames = new Image[3];
            upFrames[0] = image.getSubimage(0, 96, width, height);
            upFrames[1] = image.getSubimage(32, 96, width, height);
            upFrames[2] = image.getSubimage(64, 96, width, height);
    }

    private void loadSpriteSheet() {
            try {
                image = ImageIO.read(
                        this.getClass().getClassLoader().getResourceAsStream(SPRITE_PATH)
                );
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                //System.out.println("TOUJOURS EXECUTER");
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
        } else {currentAnimationFrame = 1;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (getDirection() == Direction.RIGHT) {
            canvas.drawImage(rightFrames[currentAnimationFrame], x, y);
        } else if (getDirection() == Direction.LEFT) {
            canvas.drawImage(leftFrames[currentAnimationFrame], x, y);
        } else if (getDirection() == Direction.UP) {
            canvas.drawImage(upFrames[currentAnimationFrame], x, y);
        } else if (getDirection() == Direction.DOWN) {
            canvas.drawImage(downFrames[currentAnimationFrame], x, y);
        }
//        if (GameConfig.isDebugEnabled()) {
//            drawHitBox(canvas);
//        }
    }
}
