package actors;

import game.MooseGame;
import game.Stage;

import java.util.Random;

/**
 * Class allows for Temporary Invincibility item to be picked up by player during gameplay.
 */
public class TemporaryInvincibilityPickup extends Pickup {

    /**
     * Constructor for Temporary Invincibility item.
     * @param canvas game window
     */
    public TemporaryInvincibilityPickup(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"invincible.png"};

    }

    /**
     * Adds Temporary Invincibility item to game window.
     */
    @Override
    public void spawn() {
        isActive = true;

        Random random = new Random();

        setX(124 + random.nextInt(Stage.WIDTH - 124));
        posY = -100;


        setWidth(50);
        setHeight(50);

        setVy(10);

    }

    /**
     * Removes Temporary Invincibility item from game window.
     */
    @Override
    public void despawn() {
        isActive = false;
        posX = -100;
        posY = -100;
    }

    /**
     * Updates position of Temporary Invincibility item.
     */
    public void update() {
        if (isActive) {
            posY += vy;
        }
    }
}
