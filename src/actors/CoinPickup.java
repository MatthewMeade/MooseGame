package actors;

import game.MooseGame;
import game.Stage;

import java.util.Random;

/**
 * Class allows coins to be picked up by player during gameplay
 */
public class CoinPickup extends Pickup {

    public CoinPickup(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"Coin.png"};
    }

    /**
     * Adds coin to game window.
     */
    @Override
    public void spawn() {
        isActive = true;

        Random random = new Random();

        boolean left = random.nextBoolean();
        setX(150 + random.nextInt(Stage.WIDTH - 300));
        setY(-200);

        setWidth(50);
        setHeight(50);

        setVy(10);

    }

    /**
     * Removes a coin from game window.
     */
    @Override
    public void despawn() {
        isActive = false;
        posX = -100;
        posY = -100;
    }

    /**
     * Updates the position of a coin.
     */
    public void update() {
        if (isActive) {
            posY += vy;
        }
    }

}
