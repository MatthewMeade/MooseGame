package actors;

import game.MooseGame;
import game.Stage;

import java.util.Random;

/**
 * Class adds, removes, and updates positioning of static obstacles.
 */
public class StaticObstacle extends Obstacle {

    /**
     * Initializes a static obstacle to add to the game window.
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

        setX(124 + random.nextInt(Stage.WIDTH - 124));
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
