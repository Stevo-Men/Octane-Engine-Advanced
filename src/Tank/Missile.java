package Tank;

import Octane.Canvas;
import Octane.Direction;
import Octane.MovableEntity;

import java.awt.*;

public class Missile extends MovableEntity {
    private final Direction tankDirection;

    public Missile(Tank tank) {
        setSpeed(10);
        tankDirection = tank.getDirection();
        initialize(tank);
    }



    private void initialize(Tank tank) {
        if (tankDirection == Direction.RIGHT) {
            setDimensions(8,4);
            teleport(tank.getX() + tank.getWidth() + 1, tank.getY() + tank.getHeight()/2 - getHeight()/2);
        } else if (tankDirection == Direction.LEFT) {
            setDimensions(8,4);
            teleport(tank.getX()  - 1, tank.getY() + tank.getHeight()/2 - getHeight()/2);
        } else if (tankDirection == Direction.UP) {
            setDimensions(4,8);
            teleport(tank.getX() + 15 - 2, tank.getY() + 30 + 1);
        } else if (tankDirection == Direction.DOWN) {
            setDimensions(4,8);
            teleport(tank.getX() + 15 - 2, tank.getY() - 9);
        }

    }
    @Override
    public void update() {
        move(tankDirection);
        if (x > 820) {
            x = -20;
        }
        if (y >= 620) {
            y = -20;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRectangle(this, Color.ORANGE);
    }
}
