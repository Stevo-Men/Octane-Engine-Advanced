package TheProjekt;

import Octane.Canvas;
import Octane.MovableEntity;
import Octane.StaticEntity;

public class Camera {
    private int x;
    private int y;
    private final int width;
    private final int height;



    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
        this.x = 0;
        this.y = 0;
    }

    public void update(Player player) {
        // Center the camera on the player's position
        this.x = player.getX() - width / 2;
        this.y = player.getY() - height / 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}