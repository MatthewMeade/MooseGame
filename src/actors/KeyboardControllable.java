package actors;

import java.awt.event.KeyEvent;

/**
 * Interface to implement keyboard functionality
 */
public interface KeyboardControllable {
    void triggerKeyPress(KeyEvent e);
    void triggerKeyRelease(KeyEvent e);
}
