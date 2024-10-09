package Tank;

import Octane.Canvas;
import Octane.StaticEntity;

import java.awt.*;

public class Brick extends StaticEntity {

    public Brick(int x, int y) {
        setDimensions(16, 16);
        teleport(x,y);
    }



    @Override
    public void draw(Canvas canvas) {
        canvas.drawRectangle(this, Color.WHITE);
    }
}