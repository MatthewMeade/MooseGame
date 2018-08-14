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

    /**
     * Spawns a new coin.
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
     * Removes a coin object from the game window.
     */
    @Override
    public void despawn() {
        isActive = false;
        posX = -100;
        posY = -100;
    }

    /**
     * Updates a coin's position.
     */
    public void update() {
        if (isActive) {
            posY += vy;
        }
    }

}
