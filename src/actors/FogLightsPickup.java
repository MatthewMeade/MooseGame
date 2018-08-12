package actors;

import game.MooseGame;
import game.Stage;

import java.util.Random;

/**
 * Class allows for Fog Lights item to be picked up by user during gameplay.
 */
public class FogLightsPickup extends Pickup {

    /**
     * Constructor for Fog Lights.
     * @param canvas game window
     */
    public FogLightsPickup(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"foglights.png"};
    }

    /**
     * Adds Fog Lights item to game window.
     */
    @Override
    public void spawn() {
        System.out.println("Spawn fog lights");

        isActive = true;

        Random random = new Random();

        setX(124 + random.nextInt(Stage.WIDTH - 124));
        posY = -100;


        setWidth(50);
        setHeight(50);

        setVy(10);

    }

    /**
     * Removes Fog Lights item from game window.
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
