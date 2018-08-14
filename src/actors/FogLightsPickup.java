package actors;

import game.MooseGame;
import game.MooseGame;

import java.util.Random;

/**
 * Represents a fog lights pickup item.
 */
public class FogLightsPickup extends Pickup {

    /**
     * Constructs a new fog lights object.
     * @param canvas game window
     */
    public FogLightsPickup(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"foglights.png"};
    }

}
