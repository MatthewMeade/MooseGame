package actors;

import game.MooseGame;
import game.Stage;

import java.util.Random;

/**
 * Class adds, removes, and updates positioning of moose obstacles.
 */
public class MooseObstacle extends Obstacle{

    /**
     * Initializes a moose obstacle to add to the game window.
     * @param canvas Game window
     */
    public MooseObstacle(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"moose.png"};
    }

    /**
     * Adds moose obstacle to game window.
     */
    @Override
    public void spawn() {

        isActive = true;
        Random random = new Random();

        boolean left = random.nextBoolean();
        setX(left ? -100 : Stage.WIDTH);
        setY(-100);

        setWidth(75);
        setHeight(75);

        setVx((left ? 1 : -1) * (random.nextInt(5) + 5));
        setVy(random.nextInt(2) + 9);
    }

    /**
     * Removes moose obstacle from game window.
     */
    @Override
    public void despawn() {
        isActive = false;
        posX = -100;
        posY = -100;
    }

    /**
     * Updates positioning of moose obstacle within game window.
     */
    public void update() {
        if(isActive){
            posX += vx;
            posY += vy;
        }
    }

}
