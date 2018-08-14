package actors;

import game.MooseGame;
import game.MooseGame;

import java.util.Random;

/**
 * Class represents a temporary invincibility pickup.
 */
public class InvincibilityPickup extends Pickup {

    /**
     * Constructs a temporary invincibility pickup.
     * @param canvas game window
     */
    public InvincibilityPickup(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"invincible.png"};

    }
}
