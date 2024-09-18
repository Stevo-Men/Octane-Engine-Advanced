package MovingRectangle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public abstract class Controller implements KeyListener {

    private final HashMap<Integer, Boolean> pressedKeys;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    public Controller() {
        pressedKeys = new HashMap<>();
    }

    protected void bindKey(int keyCode) {
        pressedKeys.put(keyCode, false);
    }

    protected void unbindKey(int keyCode) {
        pressedKeys.remove(keyCode);
    }

    protected  void clearKeys() {
        pressedKeys.clear();
    }

    public boolean isKeyPressed(int keycode) {
        return pressedKeys.containsKey(keycode) && pressedKeys.get(keycode);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (pressedKeys.containsKey(keyCode)) {
            pressedKeys.put(keyCode, true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (pressedKeys.containsKey(keyCode)) {
            pressedKeys.put(keyCode, false);
        }
    }
}
