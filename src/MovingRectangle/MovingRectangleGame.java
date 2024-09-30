package MovingRectangle;

import Octane.Canvas;
import Octane.Game;
import Octane.GamePad;

import java.awt.*;

public class MovingRectangleGame extends Game {

    Player player;
    Npc npc;
    GamePad gamePad;

    @Override
    protected void initialize() {
        player = new Player(gamePad);
        npc = new Npc();
        gamePad = new GamePad();
    }

    @Override
    protected void update() {
        if (gamePad.isQuitPressed()) {
            stopPlaying();
        }
        player.update();
        npc.update();

    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.drawRectangle(0,0,800,600, Color.ORANGE);
        player.draw(canvas);
        npc.draw(canvas);
    }

}
