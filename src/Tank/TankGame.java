package Tank;

import Octane.Canvas;
import Octane.Game;
import Tank.GamePad;

import java.util.ArrayList;

public class TankGame extends Game {

    private GamePad gamePad;
    private Tank tank;
    private ArrayList<Missile> missiles;
    @Override
    protected void initialize() {
        gamePad = new GamePad();
        tank = new Tank(gamePad);
        missiles = new ArrayList<>();
    }

    @Override
    protected void update() {
        if (gamePad.isQuitPressed()) {
            stopPlaying();
        }
        if (gamePad.isFirePressed() && tank.canFire()) {
            missiles.add(tank.fire());
        }
        tank.update();
        for (Missile missile : missiles) {
            missile.update();
        }
    }

    @Override
    protected void draw(Canvas canvas) {
        tank.draw(canvas);
        for (Missile missile : missiles) {
            missile.draw(canvas);
        }
    }

}
