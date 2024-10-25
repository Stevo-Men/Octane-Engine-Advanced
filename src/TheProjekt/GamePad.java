package TheProjekt;

import Octane.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {

    private int quitKey = KeyEvent.VK_ESCAPE;

    private int fireKey = KeyEvent.VK_SPACE;
    private int enterKey = KeyEvent.VK_ENTER;



    public GamePad() {
        bindKey(quitKey);
        bindKey(fireKey);
        bindKey(enterKey);

    }

    public boolean isFirePressed() {
        return isKeyPressed(fireKey);
    }

    public boolean isQuitPressed() {
        return isKeyPressed(quitKey);
    }

    public boolean isEnterPressed() { return isKeyPressed(enterKey); }



}
