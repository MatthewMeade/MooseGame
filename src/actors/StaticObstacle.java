package actors;

import game.MooseGame;
import game.Stage;

import java.util.Random;

public class StaticObstacle extends Obstacle {


    public StaticObstacle(MooseGame canvas) {

        super(canvas);

        sprites = new String[]{"pothole.png"};
    }

    @Override
    public void spawn() {

        isActive = true;

        Random random = new Random();

        setX(124 + random.nextInt(Stage.WIDTH - 124));
        posY = -100;


        setWidth(50);
        setHeight(50);

        setVy(10);

    }

    @Override
    public void despawn() {
        isActive = false;
        posX = -100;
        posY = -100;
    }

    public void update() {
        if (isActive) {
            posY += vy;
        }
    }
}
