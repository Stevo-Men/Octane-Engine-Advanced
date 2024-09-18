import java.awt.*;

public final class BouncingBallGame extends Game{

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
        ball = new Ball(1);
    }


    @Override
    public void update() {
        ball.update();
        if (ball.hasTouched()) {
            score += 10;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        ball.draw(canvas);
        canvas.drawString("Score: " + score, 10, 20, Color.RED);

    }



}
