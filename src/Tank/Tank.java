package Tank;

import Octane.Canvas;
import Octane.ContrallableEntity;
import Octane.MovementController;

import java.awt.*;

public class Tank extends ContrallableEntity {

    private int cooldown = 0;

    public Tank(MovementController controller) {
        super(controller);
        setDimensions(30,50);
        teleport(300,400);
        setSpeed(2);
    }

    public Missile fire() {
        cooldown = 05;
        return new Missile(this);
    }

    public boolean canFire() {
        return cooldown == 0;
    }
    @Override
    public void update() {
        moveWithController();
        cooldown--;
        if (cooldown < 0) {
            cooldown = 0;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRectangle(this, Color.GREEN);
    }
}
