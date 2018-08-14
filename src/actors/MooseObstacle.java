package actors;

import game.MooseGame;
import java.util.Random;

/**
 * Represents a moose obstacle.
 */
public class MooseObstacle extends Obstacle {

    /**
     * Constructs a moose obstacle.
     *
     * @param canvas Game window
     */
    public MooseObstacle(MooseGame canvas) {
        super(canvas);
    }

    /**
     * Adds a moose obstacle to game window.
     */
    @Override
    public void spawn() {

        isActive = true;
        Random random = new Random();

        boolean left = random.nextBoolean();

        sprites = new String[]{(left ? "moose_left.png" : "moose_right.png")};

        setX(left ? -100 : MooseGame.WIDTH);
        setY(-100);

        setWidth(75);
        setHeight(75);

        setVx((left ? 1 : -1) * (random.nextInt(5) + 5));
        setVy(random.nextInt(2) + 9);
    }

}
