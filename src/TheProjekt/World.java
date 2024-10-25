    package TheProjekt;

    import Octane.Canvas;

    import javax.imageio.ImageIO;
    import java.awt.*;
    import java.io.IOException;

    import java.awt.image.BufferedImage;
    import java.awt.Graphics2D;

    public class World {
        private static final String MAP_PATH = "images/sewer_map.png";
        private Image background;
        private double zoomFactor = 2.0;

        public void load() {
            try {
                BufferedImage originalImage = ImageIO.read(
                        this.getClass().getClassLoader().getResourceAsStream(MAP_PATH)
                );

                int scaledWidth = (int) (originalImage.getWidth() * zoomFactor);
                int scaledHeight = (int) (originalImage.getHeight() * zoomFactor);


                BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, originalImage.getType());

                Graphics2D g2d = scaledImage.createGraphics();
                g2d.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
                g2d.dispose();

                background = scaledImage;

            } catch (IOException e) {
                System.err.println("Error loading or scaling map image: " + e.getMessage());
                e.printStackTrace();
            }
        }

        public void draw(Canvas canvas, int offsetX, int offsetY) {
            canvas.drawImage(background, offsetX, offsetY);
        }
    }
