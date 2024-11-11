package TheProjekt;

public class Camera {
    private int x;
    private int y;
    private final int width;
    private final int height;
    private final int worldWidth;
    private final int worldHeight;

    public Camera(int width, int height, int worldWidth, int worldHeight) {
        this.width = width;
        this.height = height;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.x = 0;
        this.y = 0;
    }

    public void update(Player player) {
        // Center the camera on the player
        this.x = player.getX() - width / 2;
        this.y = player.getY() - height / 2;

        // Ensure camera stays within world boundaries
        x = Math.max(0, Math.min(x, worldWidth - width));
        y = Math.max(0, Math.min(y, worldHeight - height));
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
