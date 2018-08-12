package actors;

import game.MooseGame;
import game.Stage;

import java.util.Random;

/**
 * Class allows Slow Motion item to be picked up by player during gameplay
 */
public class SlowMotionPickup extends Pickup {

    /**
     * Constructor for Slow Motion
     * @param canvas game window
     */
    public SlowMotionPickup(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"slowmotion.png"};
    }

    /**
     * Add Slow Motion item to game window.
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
     * Remove Slow Motion item from game window.
     */
    @Override
    public void despawn() {
        isActive = false;
        posX = -100;
        posY = -100;
    }

    /**
     * Update position of Slow Motion item.
     */
    public void update() {
        if (isActive) {
            posY += vy;
        }
    }

}
