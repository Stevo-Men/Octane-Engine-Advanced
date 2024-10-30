package Octane;

import java.awt.*;

public class Canvas {

    private Graphics2D graphics;

    public Canvas(Graphics2D graphics) {
        this.graphics = graphics;
    }

    public void drawString(String text, int x, int y, Paint paint) {
        graphics.setPaint(paint);
        graphics.drawString(text, x, y);
    }

    public void drawCircle(int x, int y, int radius, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillOval(x, y, radius * 2, radius * 2);
    }

    public void drawRectangle(StaticEntity entity, Paint paint) {

        drawRectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight(), paint);
    }

    public void drawRectangle(int x, int y, int width, int height, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRect(x, y, width, height);
    }

    public void drawImage(Image image, int x, int y) {
        graphics.drawImage(image, x, y, null);
    }

    public void drawText(String text, int x, int y) {
        graphics.setFont(new Font("Arial", Font.PLAIN, 24));
        graphics.setColor(Color.WHITE);
        graphics.drawString(text, x, y);
    }

    public void drawText(String text, int x, int y, Font font, Color color) {
        graphics.setFont(font);
        graphics.setColor(color);
        graphics.drawString(text, x, y);
    }

    public Graphics2D getGraphics() {
        return graphics;
    }
}
