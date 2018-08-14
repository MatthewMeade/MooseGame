package actors;

import game.MooseGame;

/**
 * Represents a coin pickup item.
 */
public class CoinPickup extends Pickup {

    /**
     * Constructs a coin pickup
     *
     * @param canvas MooseGame canvas to draw to
     */
    public CoinPickup(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"coin.png"};
    }

}
