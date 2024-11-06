package Octane;

import TheProjekt.Camera;

import java.awt.*;

public abstract class StaticEntity {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private Camera camera;

    public abstract void draw(Canvas canvas, int offsetX, int offsetY);

    public boolean intersectWith(StaticEntity entity) {
        return getBounds().intersects(entity.getBounds());
    }

    protected Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void teleport(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    };

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
