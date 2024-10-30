package TheProjekt;

import Octane.Canvas;
import Octane.Direction;
import Octane.MovableEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Enemy extends MovableEntity {
    private static final String SPRITE_PATH = "images/enemy_pink.png";
    private static final int ANIMATION_SPEED = 8;
    private int currentAnimationFrame = 1;
    private int nextFrame = ANIMATION_SPEED;
    private static int ENEMY_HEALTH = 100;
    private BufferedImage image;
    private Image[] rightFrames;
    private Image[] leftFrames;
    private Image[] upFrames;
    private Image[] downFrames;
    private final double scaleFactor = 2.0;
    private int x;
    private int y;
    private int speed;
    private boolean path1 = true;
    private boolean path2 = false;
    private boolean path3 = false;
    private boolean path4 = false;

    public Enemy() {
        x = 400;
        y = 200;
        setDimensions((int)(32 * scaleFactor), (int)(32 * scaleFactor));
        speed = 2;
        load();
    }

    @Override
    public void update() {
        super.update();
        moveByPath();

        if (hasMoved()) {
            nextFrame--;
            if (nextFrame == 0) {
                currentAnimationFrame++;
                switch (getDirection()) {
                    case RIGHT:
                        if (currentAnimationFrame >= rightFrames.length) {
                            currentAnimationFrame = 0;
                        }
                        break;
                    case LEFT:
                        if (currentAnimationFrame >= leftFrames.length) {
                            currentAnimationFrame = 0;
                        }
                        break;
                    case UP:
                        if (currentAnimationFrame >= upFrames.length) {
                            currentAnimationFrame = 0;
                        }
                        break;
                    case DOWN:
                        if (currentAnimationFrame >= downFrames.length) {
                            currentAnimationFrame = 0;
                        }
                        break;
                }
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 1;
        }
    }


    private void moveByPath() {
        if (path1) {
            y += speed;
            setDirection(Direction.DOWN);
            if (y >= 500) {
                path1 = false;
                path2 = true;
                setDirection(Direction.RIGHT);
            }
        } else if (path2) {
            x += speed;
            setDirection(Direction.RIGHT);
            if (x >= 500) {
                path2 = false;
                path3 = true;
                setDirection(Direction.UP);
            }
        } else if (path3) {
            y -= speed;
            setDirection(Direction.UP);
            if (y <= 200) {
                path3 = false;
                path4 = true;
                setDirection(Direction.LEFT);
            }
        } else {
            x -= speed;
            setDirection(Direction.LEFT);
            if (x <= 100) {
                path4 = false;
                path1 = true;
                setDirection(Direction.DOWN);
            }
        }
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


    //    public void draw(Canvas canvas, int offsetX, int offsetY) {
//        if (getDirection() == Direction.RIGHT) {
//            canvas.drawImage(rightFrames[currentAnimationFrame], x + offsetX, y + offsetY);
//        } else if (getDirection() == Direction.LEFT) {
//            canvas.drawImage(leftFrames[currentAnimationFrame], x + offsetX, y + offsetY);
//        } else if (getDirection() == Direction.UP) {
//            canvas.drawImage(upFrames[currentAnimationFrame], x + offsetX, y + offsetY);
//        } else if (getDirection() == Direction.DOWN) {
//            canvas.drawImage(downFrames[currentAnimationFrame], x + offsetX, y + offsetY);
//        }
//    }

    @Override
    public boolean hasMoved() {
        return getDirection() != null && (speed > 0);
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
        canvas.drawRectangle(x + offsetX,y + offsetY - 10,50,5,Color.RED);
        canvas.drawRectangle(x + offsetX,y + offsetY - 10,ENEMY_HEALTH/2,5,Color.GREEN);
    }
}
