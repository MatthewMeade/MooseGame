package actors;

import game.MooseGame;
import game.MooseGame;

import java.util.Random;

/**
 * Class repesents a slow motion pickup.
 */
public class SlowMotionPickup extends Pickup {

    /**
     * Constructs a new slow motion pickup.
     * @param canvas game window
     */
    public SlowMotionPickup(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"slowmotion.png"};
    }

}
