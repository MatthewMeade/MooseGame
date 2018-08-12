package actors;

import game.MooseGame;

import java.util.ArrayList;

/**
 * Base class for all in-game obstacles
 */
public abstract class Obstacle extends Actor{

    public boolean isActive = false;

    /**
     * Obstacle constructor.
     * @param canvas game window
     */
    public Obstacle(MooseGame canvas) {
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
