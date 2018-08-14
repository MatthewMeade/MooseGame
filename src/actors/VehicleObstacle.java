package actors;

import game.MooseGame;

import java.util.Random;

/**
 * Class adds, removes, and updates positioning of vehicle obstacles.
 */
public class VehicleObstacle extends Obstacle {

    /**
     * Constructs a vehicle obstacle
     *
     * @param canvas MooseGame canvas to draw to
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

        setX(150 + random.nextInt(MooseGame.WIDTH - 300));
        setY(-200);

        setWidth(50);
        setHeight(100);

        setVx(0);
        setVy(random.nextInt(3) + 11);
    }

}
