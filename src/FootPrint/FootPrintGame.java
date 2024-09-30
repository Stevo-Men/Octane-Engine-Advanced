package FootPrint;

import Octane.Canvas;
import Octane.Game;
import Octane.GamePad;

import java.awt.*;

public class FootPrintGame extends Game {
    private GamePad gamePad;
    private Player player;

    @Override
    protected void update() {
       if (gamePad.isQuitPressed()) {
           stopPlaying();
       }
       player.update();
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.drawRectangle(0,0,800,600, Color.BLUE);
        player.draw(canvas);
    }

    @Override
    protected void initialize() {
        gamePad = new GamePad();
        player = new Player(gamePad);
    }
}
