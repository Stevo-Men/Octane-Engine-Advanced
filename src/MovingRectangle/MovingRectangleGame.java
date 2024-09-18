package MovingRectangle;

import Octane.Canvas;
import Octane.Game;

import java.awt.*;

public class MovingRectangleGame extends Game {

    Player player;
    Npc npc;
    Controller controller;

    @Override
    protected void initialize() {
        player = new Player(controller);
        super.addKeyListener(controller);
        npc = new Npc();
        controller = new Controller();
    }

    @Override
    protected void update() {
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
