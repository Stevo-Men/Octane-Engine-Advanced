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
        if (currentSpeed < 0) {
            currentSpeed = 0; // Prevent negative speed
        }

        // Move based on direction and current speed
        switch (playerDirection) {
            case RIGHT -> x += (int) currentSpeed;
            case LEFT -> x -= (int) currentSpeed;
            case DOWN -> y += (int) currentSpeed;
            case UP -> y -= (int) currentSpeed;
        }
    }

    public boolean isOutOfBounds(int worldWidth, int worldHeight) {
        return x < 0 || x > worldWidth || y < 0 || y > worldHeight;
    }

    public boolean isFlying() {
        return currentSpeed > 0;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    public void draw(Canvas canvas, Camera camera) {
        Graphics2D g2d = canvas.getGraphics();
        AffineTransform originalTransform = g2d.getTransform();

        // Rotate knife based on its direction
        double rotationAngle = switch (playerDirection) {
            case RIGHT -> Math.PI / 2;
            case LEFT -> -Math.PI / 2;
            case DOWN -> Math.PI;
            case UP -> 0.0;
        };

        // Adjust rendering position based on camera
        int renderX = x - camera.getX();
        int renderY = y - camera.getY();

        g2d.rotate(rotationAngle, renderX + getWidth() / 2, renderY + getHeight() / 2);
        g2d.drawImage(image, renderX, renderY, null);
        g2d.setTransform(originalTransform);
    }

    private void initializePosition(Player player) {
        int xPos = player.getX();
        int yPos = player.getY();

        // Position the knife relative to the player
        switch (playerDirection) {
            case RIGHT -> teleport(xPos + player.getWidth(), yPos + player.getHeight() / 2 - 1);
            case LEFT -> teleport(xPos - getWidth(), yPos + player.getHeight() / 2 - 1);
            case DOWN -> teleport(xPos + player.getWidth() / 2 - 1, yPos + player.getHeight());
            case UP -> teleport(xPos + player.getWidth() / 2 - 1, yPos - getHeight());
        }

        setDimensions(16, 16); // Ensure knife has appropriate dimensions
    }

    private void loadSprite() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(SPRITE_PATH));
        } catch (IOException e) {
            System.err.println("Error loading knife sprite: " + e.getMessage());
        }
    }
}
