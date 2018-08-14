package actors;

import game.MooseGame;
import game.MooseGame;

import java.util.Random;

/**
 * Represents a coin pickup item.
 */
public class CoinPickup extends Pickup {

    public CoinPickup(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"coin.png"};
    }



}
