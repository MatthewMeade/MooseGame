package actors;

import game.MooseGame;
import game.MooseGame;

import java.util.Random;

/**
 * Class repesents a temporary invincibility pickup.
 */
public class TemporaryInvincibilityPickup extends Pickup {

    /**
     * Constructs a temporary invincibility pickup.
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

        boolean left = random.nextBoolean();
        setX(150 + random.nextInt(MooseGame.WIDTH - 300));
        setY(-200);

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
