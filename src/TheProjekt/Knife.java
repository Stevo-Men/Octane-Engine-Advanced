package TheProjekt;



import Octane.Canvas;
import Octane.CollidableRepository;
import Octane.Direction;
import Octane.MovableEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

public class Knife extends MovableEntity {

    private Direction playerDirection;
    private static final String SPRITE_PATH = "images/knife.png";
    private Image image;
    protected int damage = 25;
    private double currentSpeed;
    private double acceleration = 0.3;


    public Knife(Player player) {
        setSpeed(9);
        currentSpeed = getSpeed();
        playerDirection = player.getDirection();
        load();
        initialize(player);
        CollidableRepository.getInstance().registerEntity(this);
    }



    @Override
    public void update() {
        currentSpeed -= acceleration;

        if (playerDirection == Direction.RIGHT) {
            x += (int) currentSpeed;
        } else if (playerDirection == Direction.LEFT) {
            x -= (int) currentSpeed;
        } else if (playerDirection == Direction.DOWN) {
            y += (int) currentSpeed;
        } else if (playerDirection == Direction.UP) {
            y -= (int) currentSpeed;
        }
        if (currentSpeed <= 0) {
            currentSpeed = 0;
        }
    }

    public void updatedThrowKnife() {

    }

    public boolean isOutOfBounds() {
        return x < 16 || x > 1008 || y < 16 || y > 1008;
    }
    public boolean isFlying() {
        return currentSpeed > 0;
    }

    @Override
    public void draw(Canvas canvas) {
        Graphics2D g2d = canvas.getGraphics();
        AffineTransform originalTransform = g2d.getTransform();

        double rotationAngle = switch (playerDirection) {
            case RIGHT -> Math.PI / 2;
            case LEFT -> -Math.PI / 2;
            case DOWN -> Math.PI;
            case UP -> 0.0;
        };
        g2d.rotate(rotationAngle, x + getWidth() / 2, y + getHeight() / 2);
        drawUpdated(g2d,0,0);
        g2d.setTransform(originalTransform);
    }

    public void drawUpdated(Graphics2D g2d, int offsetX, int offsetY) {
        g2d.drawImage(image, x - offsetX, y - offsetY, null);
    }


    private void initialize(Player player) {
        if (playerDirection == Direction.RIGHT) {
            teleport(player.getX() + player.getWidth() + 1,
                    player.getY() + 15 - 2);
            setDimensions(4, 2);
        } else if (playerDirection == Direction.LEFT) {
            teleport(player.getX() - 9, player.getY() + 15 - 2);
            setDimensions(4, 2);
        } else if (playerDirection == Direction.DOWN) {
            teleport(player.getX() + 15 - 2,
                    (player.getY() + player.getHeight() + 1));
            setDimensions(4, 2);
        } else if (playerDirection == Direction.UP) {
            teleport(player.getX() + 15 - 2, player.getY() - 9);
            setDimensions(4, 2);
        }
    }

    private void load() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(SPRITE_PATH));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}


