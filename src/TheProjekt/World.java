    package TheProjekt;

    import Octane.Canvas;

    import javax.imageio.ImageIO;
    import java.awt.*;
    import java.io.IOException;

    import java.awt.image.BufferedImage;
    import java.awt.Graphics2D;

    public class World {
        private static final String MAP_PATH = "images/Level_0__Ground_Base.png";
        private static final double ZOOM_FACTOR = 2;
        private Image background;


        public void load() {
            try {
                BufferedImage originalImage = ImageIO.read(
                        this.getClass().getClassLoader().getResourceAsStream(MAP_PATH)
                );
                background = scaleImage(originalImage, ZOOM_FACTOR);
            } catch (IOException e) {
                System.err.println("Error loading or scaling map image: " + e.getMessage());
                e.printStackTrace();
            }
        }
        private Image scaleImage(BufferedImage originalImage, double scaleFactor) {

            int scaledWidth = (int) (originalImage.getWidth() * scaleFactor);
            int scaledHeight = (int) (originalImage.getHeight() * scaleFactor);

            BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, originalImage.getType());
            Graphics2D g2d = scaledImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();

            return scaledImage;
        }

        public void draw(Canvas canvas, int offsetX, int offsetY) {

            canvas.drawImage(background, offsetX, offsetY);
            //canvas.drawRectangle(5000,5000,offsetX,offsetY,Color.BLUE);

        }


    }
