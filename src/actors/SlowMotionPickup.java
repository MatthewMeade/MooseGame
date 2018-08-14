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

    /**
     * Adds Slow Motion item to game window.
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
     * Removes Slow Motion item from game window.
     */
    @Override
    public void despawn() {
        isActive = false;
        posX = -100;
        posY = -100;
    }

    /**
     * Updates position of Slow Motion item.
     */
    public void update() {
        if (isActive) {
            posY += vy;
        }
    }

}
