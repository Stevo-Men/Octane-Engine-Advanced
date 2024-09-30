package FootPrint;

import Octane.Canvas;
import Octane.Game;
import Octane.GamePad;

import java.awt.*;
import java.util.ArrayList;

public class FootPrintGame extends Game {
    private GamePad gamePad;
    private Player player;
    private ArrayList<Footprint> footprints;

    @Override
    protected void update() {
       if (gamePad.isQuitPressed()) {
           stopPlaying();
       }
       player.update();

       if (gamePad.isMoving()) {
           footprints.add(player.layFootprint());
       }
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.drawRectangle(0,0,800,600, Color.BLUE);
        for (Footprint footprint : footprints) {
            footprint.draw(canvas);
        }
        player.draw(canvas);
    }

    @Override
    protected void initialize() {
        gamePad = new GamePad();
        player = new Player(gamePad);
        footprints = new ArrayList<>();
    }
}
