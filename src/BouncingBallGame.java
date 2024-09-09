import java.awt.*;

public class BouncingBallGame extends Game{

    private static final int SLEEP = 25;
    private boolean playing = true;

    private long before;
    private RenderingEngine renderingEngine;
    private int score;
    private Ball ball;


    public BouncingBallGame() {
        //frame.setUndecorated(true);

    }


    @Override
    protected void initialize() {
        ball = new Ball(25);
    }


    @Override
    public void update() {
        ball.update();
        if (ball.hasTouched()) {
            score += 10;
        }
    }

    @Override
    public void drawOnBuffer(Graphics2D bufferEngine) {
        ball.draw(bufferEngine);

        bufferEngine.setPaint(Color.WHITE);
        bufferEngine.drawString("Score: " + score, 10, 20);
    }



}
