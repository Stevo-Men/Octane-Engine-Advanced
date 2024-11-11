package TheProjekt;

import Octane.Canvas;
import Octane.Direction;
import Octane.MovableEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Enemy extends MovableEntity {
    private static final String SPRITE_PATH = "images/enemy_pink.png";
    private static final int ANIMATION_SPEED = 8;
    private static final int FRAME_COUNT = 3;
    private int currentAnimationFrame = 0;
    private int nextFrame = ANIMATION_SPEED;
    private  int ENEMY_MAX_HEALTH = 50;
    private BufferedImage spriteSheet;
    private Map<Direction, Image[]> directionFrames;
    private int health = ENEMY_MAX_HEALTH;
    private final double SCALE_FACTOR = 2.0;
    private int x;
    private int y;
    private int speed;
    private boolean path1 = true;
    private boolean path2 = false;
    private boolean path3 = false;
    private boolean path4 = false;


    public Enemy() {
        setDimensions((int)(32 * SCALE_FACTOR), (int)(32 * SCALE_FACTOR));
        speed = 2;
        load();
    }



    private void moveByPath() {
        if (path1) {
            y += speed;
            setDirection(Direction.DOWN);
            if (y >= 500) {
                path1 = false;
                path2 = true;
            }
        } else if (path2) {
            x += speed;
            setDirection(Direction.RIGHT);
            if (x >= 500) {
                path2 = false;
                path3 = true;
            }
        } else if (path3) {
            y -= speed;
            setDirection(Direction.UP);
            if (y <= 200) {
                path3 = false;
                path4 = true;
            }
        } else if (path4) {
            x -= speed;
            setDirection(Direction.LEFT);
            if (x <= 100) {
                path4 = false;
                path1 = true;
            }
        }
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
            frames[i] = getScaledImage(spriteSheet.getSubimage(i * 32, rowIndex * 32, 32, 32));
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
            spriteSheet = ImageIO.read(
                    this.getClass().getClassLoader().getResourceAsStream(SPRITE_PATH)
            );
        } catch (IOException e) {
            System.err.println("Error loading player sprite sheet: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void drawHealthEnemy(Canvas canvas,int offsetX, int offsetY) {
        canvas.drawHealthNPC(x - 7 + offsetX,y - 1 + offsetY - 10,52,8,Color.BLACK);
        canvas.drawHealthNPC(x - 6 + offsetX,y + offsetY - 10,50,6,Color.RED);
        canvas.drawHealthNPC(x - 6 + offsetX ,y + offsetY - 10 ,this.health,6,Color.GREEN);

    }

    public int getHealth() {
        return health;
    }



    public void isTouched(Knife knife) {
        this.health -= knife.damage;
    }


    @Override
    public boolean hasMoved() {
        return getDirection() != null && (speed > 0);
    }


    @Override
    public void update() {
        super.update();
        moveByPath();

        if (hasMoved()) {
            nextFrame--;
            if (nextFrame == 0) {
                currentAnimationFrame = (currentAnimationFrame + 1) % FRAME_COUNT;
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 0;
        }
    }

    @Override
    public void draw(Canvas canvas, int offsetX, int offsetY) {
        Image[] frames = directionFrames.get(getDirection());
        canvas.drawImage(frames[currentAnimationFrame], x + offsetX, y + offsetY);
        drawHealthEnemy(canvas, offsetX, offsetY);
    }

}
