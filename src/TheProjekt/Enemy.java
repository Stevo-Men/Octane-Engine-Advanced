package TheProjekt;

import Octane.Canvas;
import Octane.CollidableRepository;
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
    private final int ENEMY_MAX_HEALTH = 50;
    private BufferedImage spriteSheet;
    private Map<Direction, Image[]> directionFrames;
    private int health = ENEMY_MAX_HEALTH;
    private final double SCALE_FACTOR = 2.0;
    private MovableEntity target; // The target to follow
    private int speed = 2;

    public Enemy(MovableEntity target, int x, int y) {
        this.target = target;
        teleport(x, y);
        setDimensions((int)(32 * SCALE_FACTOR), (int)(32 * SCALE_FACTOR));
        load();
    }

    private void moveToTarget() {
        if (target == null) return;

        // Calculate direction to target
        int deltaX = target.getX() - this.x;
        int deltaY = target.getY() - this.y;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX > 0) {
                setDirection(Direction.RIGHT);
                x += speed;
            } else {
                setDirection(Direction.LEFT);
                x -= speed;
            }
        } else {
            if (deltaY > 0) {
                setDirection(Direction.DOWN);
                y += speed;
            } else {
                setDirection(Direction.UP);
                y -= speed;
            }
        }
    }



    public void moveWithOffset(int offsetX, int offsetY) {
        this.x += offsetX;
        this.y += offsetY;
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
            System.err.println("Error loading enemy sprite sheet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void drawHealthBar(Canvas canvas, int x, int y) {
        canvas.drawHealthNPC(x - 7, y - 1 - 10, 52, 8, Color.BLACK);
        canvas.drawHealthNPC(x - 6, y - 10, 50, 6, Color.RED);
        canvas.drawHealthNPC(x - 6, y- 10, this.health, 6, Color.GREEN);
    }

    public int getHealth() {
        return health;
    }

    public void isTouched(Knife knife) {
        this.health -= knife.DAMAGE;
    }

    @Override
    public boolean hasMoved() {
        return getDirection() != null && (speed > 0);
    }

    @Override
    public void update() {
        super.update();
        moveToTarget(); // Move toward the target

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
    public void draw(Canvas canvas) {
        Image[] frames = directionFrames.get(getDirection());
        canvas.drawImage(frames[currentAnimationFrame], x, y);
       // drawHealthEnemy(canvas);
    }


    public void draw(Canvas canvas, Camera camera) {
        int renderX = x - camera.getX(); // Adjust based on camera
        int renderY = y - camera.getY();

        Image[] frames = directionFrames.get(getDirection());
        canvas.drawImage(frames[currentAnimationFrame], renderX, renderY);

        drawHealthBar(canvas, renderX, renderY);
    }

}
