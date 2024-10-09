package Tank;

import Octane.Canvas;
import Octane.ContrallableEntity;
import Octane.MovementController;

import java.awt.*;

public class Tank extends ContrallableEntity {

    private int cooldown = 0;

    public Tank(MovementController controller) {
        super(controller);
        setDimensions(40,40);
        teleport(300,400);
        setSpeed(2);
    }

    public Missile fire() {
        cooldown = 10;
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
        int cooldownWidth = (cooldown * getWidth()) / 40;
        canvas.drawRectangle(x, y - 5, 20, 4, Color.RED);
        canvas.drawRectangle(x, y - 5, cooldownWidth * 2, 4, Color.YELLOW);

    }
}
