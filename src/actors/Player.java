package actors;

import game.PlayerInventory;
import game.MooseGame;

import java.awt.event.KeyEvent;

import static game.PlayerInventory.Vehicles.*;

/**
 * Represents a Player.
 */
public class Player extends Actor implements KeyboardControllable {

    private boolean left, right;

    /**
     * Constructs a Player.
     *
     * @param mooseGame game window
     */
    public Player(MooseGame mooseGame) {
        super(mooseGame);

        if (PlayerInventory.getEquippedVehicle() == CAR) {
            sprites = new String[]{"player_bluecar.png"};
        } else if (PlayerInventory.getEquippedVehicle() == TRUCK) {
            sprites = new String[]{"truck.png"};
        } else if (PlayerInventory.getEquippedVehicle() == ATV) {
            sprites = new String[]{"atv.png"};
        }

        actorSpeed = 10;
        width = 50;
        height = 100;
        posX = MooseGame.WIDTH / 2;
        posY = 8 * MooseGame.HEIGHT / 10;
    }

    /**
     * Updates the speed at which the game moves.
     */
    public void update() {
        super.update();
        updateSpeed();
    }

    /**
     * Controls player movement.
     */
    private void updateSpeed() {
        vx = 0;
        if (left)
            vx = -actorSpeed;
        if (right)
            vx = actorSpeed;

        if (posX > 0 && vx < 0) {
            posX += vx;
        } else if (posX + width < MooseGame.WIDTH - 15 && vx > 0) {
            posX += vx;
        }

    }

    /**
     * Stop moving player when control key released
     *
     * @param e KeyEvent key event
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
    }

    /**
     * Move player when key pressed down
     *
     * @param e KeyEvent key event
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

    }
}
