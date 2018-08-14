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

    /**
     * Adds fog lights object to game window.
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
     * Removes a fog lights object from the game window.
     */
    @Override
    public void despawn() {
        isActive = false;
        posX = -100;
        posY = -100;
    }

    /**
     * Update position of Fog Lights item.
     */
    public void update() {
        if (isActive) {
            posY += vy;
        }
    }
}
