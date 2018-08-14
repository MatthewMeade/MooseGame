package actors;

import game.MooseGame;

/**
 * Class represents a temporary invincibility pickup.
 */
public class InvincibilityPickup extends Pickup {

    /**
     * Constructs a new Invincibility Pickup
     *
     * @param canvas game window
     */
    public InvincibilityPickup(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"invincible.png"};
    }
    
}
