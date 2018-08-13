package actors;

import game.MooseGame;
import game.Stage;

import java.util.Random;

/**
 * Class adds, removes, and updates positioning of vehicle obstacles.
 */
public class VehicleObstacle extends Obstacle {

    /**
     * Constructor for VehicleObstacle.
     * @param canvas game window
     */
    public VehicleObstacle(MooseGame canvas) {
        super(canvas);
        String[] possibleSprites = new String[]{"enemy_redcar.png", "enemy_purplecar.png", "enemy_truck.png"};
        Random random = new Random();
        sprites = new String[]{possibleSprites[random.nextInt(possibleSprites.length)]};
    }

    /**
     * Adds vehicle obstacle to game window.
     */
    @Override
    public void spawn() {
        isActive = true;
        Random random = new Random();

        boolean left = random.nextBoolean();
        setX(150 + random.nextInt(Stage.WIDTH - 300));
        setY(-200);

        setWidth(50);
        setHeight(100);

        setVx(0);
        setVy(random.nextInt(3) + 11);
    }

    /**
     * Removes vehicle obstacle from game window.
     */
    @Override
    public void despawn() {
        isActive = false;
        posX = -100;
        posY = -100;
    }

    /**
     * Updates positioning of vehicle obstacle within game window.
     */
    public void update() {
        if(isActive){
            posX += vx;
            posY += vy;
        }
    }
}
