package actors;

import game.MooseGame;

/**
 * Allows items to be picked up by player during gameplay.
 */
public abstract class Pickup extends Actor {

    public boolean isActive = false;

    /**
     * Constructor for pickup.
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
