package TheProjekt;

import Octane.Canvas;
import Octane.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Menu extends Game {
    private int selectedIndex = 0;
    private String[] menuOptions = {"Start Game", "Options", "Quit"};
    private GamePad gamePad;
    private static final String MENU_BACKGROUND = "images/sewer_map.png";
    private Image menu_background;

    @Override
    protected void initialize() {
        load();
        gamePad = new GamePad();
    }

    public void load() {
        try {
            menu_background = ImageIO.read(
                    this.getClass().getClassLoader().getResourceAsStream(MENU_BACKGROUND)
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            //System.out.println("TOUJOURS EXECUTER");
        }
    }
    @Override
    protected void update() {

        if (gamePad.isUpPressed()) {
            selectedIndex = (selectedIndex - 1 + menuOptions.length) % menuOptions.length;
        }
        if (gamePad.isDownPressed()) {
            selectedIndex = (selectedIndex + 1) % menuOptions.length;
        }

        if (gamePad.isEnterPressed()) {
            executeOption();
        }
    }



    private void executeOption() {
        switch (selectedIndex) {
            case 0:
                TheProjektGame game = new TheProjektGame();
                game.start();
                break;
            case 1:
                System.out.println("Options selected");
                break;
            case 2:
                // Quit the application
                System.exit(0);
                break;
        }
    }

    @Override
    protected void draw(Canvas canvas) {
        // Draw background (assume backgroundImage is loaded)
        canvas.drawImage(menu_background, 0, 0);

        // Draw menu options
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedIndex) {
                canvas.drawText("> " + menuOptions[i], 300, 200 + i * 40);
            } else {
                canvas.drawText(menuOptions[i], 300, 200 + i * 40);
            }
        }
    }

}
