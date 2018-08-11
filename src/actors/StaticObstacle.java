package actors;

import game.MooseGame;
import game.Stage;

import java.util.Random;

/**
 * Initializes, and provides spawning and despawning capabilities for static obstacles
 */
public class StaticObstacle extends Obstacle {

    /**
     * Initializes a static obstacle
     * @param canvas
     */
    public StaticObstacle(MooseGame canvas) {

        super(canvas);

        sprites = new String[]{"pothole.png"};
    }

    /**
     *
     */
    @Override
    public void spawn() {

        isActive = true;

        Random random = new Random();

        posX = ((int) (Stage.WIDTH * random.nextDouble()));
        posY = -100;


        setWidth(50);
        setHeight(50);

        setVy(10);

    }

    /**
     *
     */
    @Override
    public void despawn() {
        isActive = false;
        posX = -100;
        posY = -100;
    }

    /**
     *
     */
    public void update() {
        if (isActive) {
            posY += vy;
        }
    }
}
