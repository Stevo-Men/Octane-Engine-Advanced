package Viking;

import Octane.Blockade;
import Octane.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Tree extends StaticEntity {

    private static final String SPRITE_PATH = "images/tree.png";
    private Image image;
    private Blockade blockade;


    public Tree(int x, int y) {
        teleport(x,y);
        blockade = new Blockade();
        blockade.setDimensions(30,16);
        load();

    }



    public void blockadeFromTop() {
        blockade.teleport(x+16,y+16);
    }

    public void blockadeFromBottom() {
        blockade.teleport(x+16,y+48);
    }

    private void load() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(SPRITE_PATH));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void draw(Octane.Canvas canvas) {
        canvas.drawImage(image, x, y);
        canvas.drawRectangle(this,Color.RED);
    }
}