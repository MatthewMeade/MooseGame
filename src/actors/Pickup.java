package actors;

import game.MooseGame;

/**
 * Abstract class represents a Pickup.
 */
public abstract class Pickup extends Actor {

    public boolean isActive = false;

    /**
     * Constructs a new Pickup.
     * @param canvas
     */
    public Pickup(MooseGame canvas) {
        super(canvas);
    }

    /**
     * Adds item to game window.
     */
    public abstract void spawn();

    /**
     * Removes item from game window.
     */
    public abstract void despawn();

}
