package FootPrint;

import Octane.Canvas;
import Octane.ContrallableEntity;
import Octane.GamePad;
import Octane.MovableEntity;

import java.awt.*;


public class Player extends ContrallableEntity {


    public Player(GamePad gamePad) {
        super(gamePad);
        teleport(200,200);
        setDimensions(20,60);
        setSpeed(3);
    }

    @Override
    public void update() {
       moveWithController();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRectangle(this, Color.WHITE);
    }

    public Footprint layFootprint() {
        return new Footprint(x, y);
    }
}