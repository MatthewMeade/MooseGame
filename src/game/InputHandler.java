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

    private MooseGame mooseGame = null;
    private KeyboardControllable player = null;
    public Action action;

    /**
     * Constructs an InputHandler
     *
     * @param stg    game window
     * @param player game user
     */
    public InputHandler(MooseGame stg, KeyboardControllable player) {
        this.mooseGame = stg;
        this.player = player;
    }

    /**
     * Handles events for the press and release of key controls.
     *
     * @param event
     */
    public void handleInput(KeyEvent event) {
        if (action == Action.PRESS) {
            player.triggerKeyPress(event);
        } else if (action == Action.RELEASE)
            player.triggerKeyRelease(event);
    }
}