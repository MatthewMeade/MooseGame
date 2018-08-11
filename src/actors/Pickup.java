package actors;

import game.MooseGame;

public abstract class Pickup extends Actor {

    public boolean isActive = false;

    /**
     * Constructs
     * @param canvas
     */
    public Pickup(MooseGame canvas) {
        super(canvas);
    }

    /**
     * Adds obstacle to game window.
     */
    public abstract void spawn();

    /**
     * Removes obstacle from game window.
     */
    public abstract void despawn();

}
