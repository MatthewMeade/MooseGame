package game;

import java.awt.event.KeyEvent;
import actors.KeyboardControllable;

/**
 * Creates a thread to process player input
 *
 * @author ghast
 */
public class InputHandler {
    public enum Action {
        PRESS,
        RELEASE
    }

    private KeyboardControllable listener = null;
    public Action action;

    /**
     * Constructs an InputHandler
     *
     * @param stg    game window
     * @param player game user
     */
    public InputHandler(MooseGame stg, KeyboardControllable player, Action action) {
        this.listener = player;
        this.action = action;
    }

    public void setListener(KeyboardControllable listener) {
        this.listener = listener;
    }

    /**
     * Handles key input
     *
     * @param event KeyEvent key event
     */
    public void handleInput(KeyEvent event) {
        if (action == Action.PRESS) {
            listener.triggerKeyPress(event);
        } else if (action == Action.RELEASE)
            listener.triggerKeyRelease(event);
    }
}