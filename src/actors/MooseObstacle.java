package actors;

import game.MooseGame;
import game.MooseGame;

import java.util.Random;

/**
 * Represents a moose obstacle.
 */
public class MooseObstacle extends Obstacle{

    /**
     * Constructs a moose obstacle.
     * @param canvas Game window
     */
    public MooseObstacle(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"moose.png"};
    }

    /**
     * Adds a moose obstacle to game window.
     */
    @Override
    public void spawn() {

        isActive = true;
        Random random = new Random();

        boolean left = random.nextBoolean();
        setX(left ? -100 : MooseGame.WIDTH);
        setY(-100);

        setWidth(75);
        setHeight(75);

        setVx((left ? 1 : -1) * (random.nextInt(5) + 5));
        setVy(random.nextInt(2) + 9);
    }

    /**
     * Removes a moose obstacle from game window.
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
