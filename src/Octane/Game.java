package Octane;

public abstract class Game {


    private boolean playing = true;
    private GameTime gameTime;
    private RenderingEngine renderingEngine;

    protected abstract void update();

    protected abstract void draw(Canvas bufferEngine);

    protected abstract  void initialize();


    public Game() {
        renderingEngine = new RenderingEngine();
        initialize();
    }

    public final void start() {
        initialize();
        run();
    }

    private void run() {
        renderingEngine.start();
        gameTime = new GameTime();
        while (playing) {
            update();
            draw(renderingEngine.buildCanvas());
            renderingEngine.drawBufferOnScreen();
            gameTime.synchronize();
        }
    }





}


