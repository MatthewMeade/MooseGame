package actors;

import game.MooseGame;

import java.util.Random;

/**
 * Abstract class represents a Pickup.
 */
public class Pickup extends Actor {


    /**
     * Constructs a new Pickup.
     *
     * @param canvas
     */
    public Pickup(MooseGame canvas) {
        super(canvas);
    }

    /**
     * Adds item to game window.
     */
    public void spawn() {
        isActive = true;

        Random random = new Random();

        setX(150 + random.nextInt(MooseGame.WIDTH - 300));
        setY(-200);

        setWidth(50);
        setHeight(50);

        setVy(10);

    }

}
