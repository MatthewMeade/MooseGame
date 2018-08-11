package game;

import java.awt.event.KeyEvent;

import actors.KeyboardControllable;

/**
 * creates a thread to process player input
 *
 * @author ghast
 */
public class InputHandler {
    public enum Action {
        PRESS,
        RELEASE
    }

    private Stage stage = null;
    private KeyboardControllable player = null;
    public Action action;

    public InputHandler(Stage stg, KeyboardControllable player) {
        this.stage = stg;
        this.player = player;
    }

    public void handleInput(KeyEvent event) {
        if (action == Action.PRESS) {
            player.triggerKeyPress(event);
        } else if (action == Action.RELEASE)
            player.triggerKeyRelease(event);
    }
}