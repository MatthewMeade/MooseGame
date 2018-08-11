package actors;

import game.MooseGame;
import game.Stage;

import java.awt.event.KeyEvent;

/**
 * Class provides creation of player sprite, in-game updates to player and surroundings,
 * key action event handlers.
 */
public class Player extends Actor implements KeyboardControllable {

    private boolean left, right;

    /**
     * Constructor for Player.
     * @param stage game window
     */
    public Player(Stage stage) {
        super(stage);

        sprites = new String[]{"CarFullHealth.png"};
        frame = 0;
        frameSpeed = 35;
        actorSpeed = 10;
        width = 70;
        height = 140;
        posX = Stage.WIDTH / 2;
        posY = 8 * Stage.HEIGHT / 10;
    }

    /**
     * Updates the speed at which the game moves
     */
    public void update() {
        super.update();
        updateSpeed();
    }

    /**
     * Keeps sprite's on-screen movements balanced
     */
    protected void updateSpeed() {
        vx = 0;
        if (left)
            vx = -actorSpeed;
        if (right)
            vx = actorSpeed;

        //don't allow scrolling off the edge of the screen
        if (posX > 0 && vx < 0) {
            posX += vx;
        } else if (posX + width < Stage.WIDTH - 15 && vx > 0) {
            posX += vx;
        }

    }

    /**
     * Gives functionality to in-game keyboard controls when a key is released.
     * @param e Event in game
     */
    public void triggerKeyRelease(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                right = false;
                break;
        }
//        updateSpeed();
    }

    /**
     * Gives functionality to in-game keyboard controls when a key is pressed.
     * @param e Event in game
     */
    public void triggerKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                right = true;
                break;

        }
//        updateSpeed();
    }

    /**
     * Ends game upon collision between actor player and actor obstacle.
     * @param a actor value
     */
    public void collision(Actor a) {
        stage.endGame();
    }
}
