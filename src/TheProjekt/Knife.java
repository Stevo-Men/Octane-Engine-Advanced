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
    private static final String SPRITE_PATH = "images/knife.png";
    private static final double KNIFE_ACCELERATION = 0.3;
    private static final int INITIAL_SPEED = 10;
    protected static final int DAMAGE = 25;
    private Direction playerDirection;
    private Image image;
    private double currentSpeed;


    public Knife(Player player) {
        setSpeed(INITIAL_SPEED);
        currentSpeed = getSpeed();
        playerDirection = player.getDirection();
        loadSprite();
        initializePosition(player);
        CollidableRepository.getInstance().registerEntity(this);

    }




    @Override
    public void update() {
        currentSpeed -= KNIFE_ACCELERATION;

        switch (playerDirection) {
            case RIGHT -> x += (int) currentSpeed;
            case LEFT -> x -= (int) currentSpeed;
            case DOWN -> y += (int) currentSpeed;
            case UP -> y -= (int) currentSpeed;
        }
    }



    public boolean isOutOfBounds() {
        return x < 16 || x > 1008 || y < 16 || y > 1008;
    }
    public boolean isFlying() {
        return currentSpeed > 0;
    }

    @Override
    public void draw(Canvas canvas, int offsetX, int offsetY) {
        Graphics2D g2d = canvas.getGraphics();
        AffineTransform originalTransform = g2d.getTransform();

        double rotationAngle = switch (playerDirection) {
            case RIGHT -> Math.PI / 2;
            case LEFT -> -Math.PI / 2;
            case DOWN -> Math.PI;
            case UP -> 0.0;
        };

        g2d.rotate(rotationAngle, x + offsetX + getWidth() / 2, y + offsetY + getHeight() / 2);
        g2d.drawImage(image, x + offsetX, y + offsetY, null);
        g2d.setTransform(originalTransform);
    }




    private void initializePosition(Player player) {
        int xPos = player.getX();
        int yPos = player.getY();

        switch (playerDirection) {
            case RIGHT -> teleport(xPos + player.getWidth() + 1, yPos + player.getHeight() / 2 - 1);
            case LEFT -> teleport(xPos - getWidth() - 1, yPos + player.getHeight() / 2 - 1);
            case DOWN -> teleport(xPos + player.getWidth() / 2 - 1, yPos + player.getHeight() + 1);
            case UP -> teleport(xPos + player.getWidth() / 2 - 1, yPos - getHeight() - 1);
        }

        setDimensions(4, 2);
    }

    private void loadSprite() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(SPRITE_PATH));
        } catch (IOException e) {
            System.err.println("Error loading knife sprite: " + e.getMessage());
        }
    }

}


