import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Game  {

    private JFrame frame;
    private static final int SLEEP = 25;
    private JPanel panel;
    private Graphics2D bufferEngine;
    private boolean playing = true;
    private BufferedImage bufferedImage;
    private long before;
    private int score;
    private Ball ball;


    public Game() {
        initializedFrame();
        //frame.setUndecorated(true);

        initializedPanel();
        ball = new Ball(25);
    }




    public void start() {
        frame.setVisible(true);
        updateSyncTime();


        while (playing) {
            bufferedImage = new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);
            RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            bufferEngine = bufferedImage.createGraphics();
            bufferEngine.setRenderingHints(hints);


            update();
            drawOnBuffer();
            drawBufferOnScreen();

            long sleep = SLEEP - (System.currentTimeMillis() - before);
            if (sleep < 4) {
                sleep = 4;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            updateSyncTime();

        }
    }

    private void updateSyncTime() {
        before = System.currentTimeMillis();
    }

    private RenderingHints getRenderingHints() {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return hints;
    }



    private void update() {
        ball.update();
        if (ball.hasTouched()) {
            score += 10;
        }
    }

    private void drawOnBuffer() {
        ball.draw(bufferEngine);

        bufferEngine.setPaint(Color.WHITE);
        bufferEngine.drawString("Score: " + score, 10, 20);
    }

    private void drawBufferOnScreen() {
        Graphics2D graphics = (Graphics2D) panel.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, panel);
        Toolkit.getDefaultToolkit().sync();
        graphics.dispose();
    }


    private void initializedPanel() {
        panel = new JPanel();
        panel.setFocusable(true);
        panel.setDoubleBuffered(true);
        frame.add(panel);
    }
    private void initializedFrame() {
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setTitle("Bouncing Balls");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
