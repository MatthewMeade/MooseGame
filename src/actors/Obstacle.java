package actors;

import game.MooseGame;

/**
 * Base class for all in-game obstacles
 */
public class Obstacle extends Actor {


    /**
     * Constructs a new obstacle.
     *
     * @param canvas game window
     */
    public Obstacle(MooseGame canvas) {
        super(canvas);
    }

    /**
     * Adds an obstacle to game window.
     */
    public void spawn() {}


}
