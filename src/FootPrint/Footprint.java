package FootPrint;

import Octane.Canvas;
import Octane.StaticEntity;

import java.awt.*;
import java.util.Random;

public class Footprint extends StaticEntity {
    private int x;
    private int y;
    private int width;
    private int height;

    public Footprint(int x, int y) {
        teleport(x,y);
        setDimensions(5,5);
    }

    public void draw(Canvas canvas) {
        Color color = new Color(getRandomValue(), getRandomValue(),getRandomValue());
        canvas.drawRectangle(this, color);
    }

    private int getRandomValue() {
        return new Random().nextInt(256);
    }
}
