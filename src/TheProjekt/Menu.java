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
    private static final String MENU_BACKGROUND = "images/menu-background.png";
    private Image menu_background;
    private boolean upPressedPreviously = false;
    private boolean downPressedPreviously = false;
    private boolean enterPressedPreviously = false;


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
        boolean upPressed = gamePad.isUpPressed();
        boolean downPressed = gamePad.isDownPressed();
        boolean enterPressed = gamePad.isEnterPressed();

        // Check if up was not pressed before but is now pressed (transition)
        if (upPressed && !upPressedPreviously) {
            selectedIndex = (selectedIndex - 1 + menuOptions.length) % menuOptions.length;
        }

        // Check if down was not pressed before but is now pressed (transition)
        if (downPressed && !downPressedPreviously) {
            selectedIndex = (selectedIndex + 1) % menuOptions.length;
        }

        // Check if enter was not pressed before but is now pressed (transition)
        if (enterPressed && !enterPressedPreviously) {
            executeOption();
        }

        // Update the previous state of the keys for the next update cycle
        upPressedPreviously = upPressed;
        downPressedPreviously = downPressed;
        enterPressedPreviously = enterPressed;
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
                System.exit(0);
                break;
        }
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.drawImage(menu_background, 0, 0);

        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedIndex) {
                canvas.drawText("> " + menuOptions[i], 525, 550 + i * 40);
            } else {
                canvas.drawText(menuOptions[i], 525, 550 + i * 40);
            }
        }
    }

}
