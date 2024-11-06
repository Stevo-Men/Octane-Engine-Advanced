package Octane;

import java.awt.*;

public class Blockade extends StaticEntity {

    public Blockade() {
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void draw(Canvas canvas, int offsetX, int offsetY) {
        canvas.drawRectangle(this, new Color(255, 0, 0, 100));
    }
}