package Viking;

import Octane.Canvas;
import Octane.Game;
import Octane.GamePad;


import java.awt.*;
import java.util.ArrayList;

public class VikingGame extends Game {
    private GamePad gamePad;
    private Player player;
    private World world;
    @Override
    protected void initialize() {
        gamePad = new GamePad();
        player = new Player(gamePad);
        world = new World();
        world.load();
    }

    @Override
    protected void update() {
        if (gamePad.isQuitPressed()) {
            stopPlaying();
        }
        player.update();
    }

    @Override
    protected void draw(Canvas canvas) {
        world.draw(canvas);
        player.draw(canvas);
    }
}
