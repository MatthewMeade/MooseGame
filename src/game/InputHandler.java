package game;

import java.awt.event.KeyEvent;

import actors.Actor;
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

    private MooseGame mooseGame = null;
    private KeyboardControllable listener = null;
    public Action action;

    /**
     * Constructs an InputHandler
     *
     * @param stg    game window
     * @param player game user
     */
    public InputHandler(MooseGame stg, KeyboardControllable player, Action action) {
        this.mooseGame = stg;
        this.listener = player;
        this.action = action;
    }

    public void setListener(KeyboardControllable listener) {
        this.listener = listener;
    }

    /**
     * Handles events for the press and release of key controls.
     *
     * @param event
     */
    public void handleInput(KeyEvent event) {
        if (action == Action.PRESS) {
            listener.triggerKeyPress(event);
        } else if (action == Action.RELEASE)
            listener.triggerKeyRelease(event);
    }
}