package actors;

import game.MooseGame;
import game.MooseGame;

import java.util.Random;

/**
 * Represents a static obstacle.
 */
public class StaticObstacle extends Obstacle {

    /**
     * Constructs a static obstacle.
     * @param canvas game window
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

    /**
     * Removes static obstacle from game window.
     */
    @Override
    public void despawn() {
        isActive = false;
        posX = -100;
        posY = -100;
    }

    /**
     * Updates positioning of static obstacle within game window.
     */
    public void update() {
        if (isActive) {
            posY += vy;
        }
    }
}
