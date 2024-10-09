package Octane;

import java.awt.event.KeyListener;

public abstract class Game {


    private boolean playing = true;

    private RenderingEngine renderingEngine;

    protected abstract  void initialize();

    protected abstract void update();

    protected abstract void draw(Canvas bufferEngine);




    public Game() {
        renderingEngine = RenderingEngine.getInstance();
        initialize();
    }

    public void addKeyListener(KeyListener keyListener) {
        renderingEngine.addKeyListener(keyListener);
    }

    public final void start() {
        initialize();
        run();
    }

    public void stopPlaying() {
        playing = false;
    }

    private void run() {
        renderingEngine.start();
        GameTime gameTime = new GameTime();
        while (playing) {
            update();
            draw(renderingEngine.buildCanvas());
            renderingEngine.drawBufferOnScreen();
            gameTime.synchronize();
        }
        renderingEngine.stop();
    }





}


