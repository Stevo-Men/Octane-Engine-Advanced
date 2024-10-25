package TheProjekt;

public class Camera {
    private int x;
    private int y;
    private int width;
    private int height;

    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
        this.x = 0;
        this.y = 0;
    }

    public void update(Player player) {
        // Center the camera on the player
        this.x = player.getX() - width / 2;
        this.y = player.getY() - height / 2;


        int worldWidth = 2000;
        int worldHeight = 2000;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x + width > worldWidth) x = worldWidth - width;
        if (y + height > worldHeight) y = worldHeight - height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
