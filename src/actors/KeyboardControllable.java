package actors;

import java.awt.event.KeyEvent;

/**
 *
 * @author eric.stock
 */
public interface KeyboardControllable {
    public void triggerKeyPress(KeyEvent e);
    public void triggerKeyRelease(KeyEvent e);
}
