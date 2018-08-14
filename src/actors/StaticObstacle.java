package actors;

import game.MooseGame;

import java.util.Random;

/**
 * Represents a static obstacle.
 */
public class StaticObstacle extends Obstacle {

    /**
     * Constructs a static obstacle.
     *
     * @param canvas MooseGame canvas being drawn to
     */
    public StaticObstacle(MooseGame canvas) {

        super(canvas);

        sprites = new String[]{"pothole.png"};
    }

    /**
     * Adds static obstacle to game window.
     */
    @Override
    public void spawn() {

        isActive = true;

        Random random = new Random();

        setX(125 + random.nextInt(MooseGame.WIDTH - 250));
        posY = -100;


        setWidth(38);
        setHeight(38);

        setVy(10);

    }

}
