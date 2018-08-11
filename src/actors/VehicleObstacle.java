package actors;

import game.MooseGame;
import game.Stage;

import java.util.Random;

public class VehicleObstacle extends Obstacle {
    public VehicleObstacle(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"TempBadCar.png"};
    }

    @Override
    public void spawn() {
        isActive = true;
        Random random = new Random();

        boolean left = random.nextBoolean();
        setX(124 + random.nextInt(Stage.WIDTH - 124));
        setY(-200);

        setWidth(70);
        setHeight(140);

        setVx(0);
        setVy(random.nextInt(5) + 11);
    }

    @Override
    public void despawn() {
        isActive = false;
        posX = -100;
        posY = -100;
    }

    public void update() {
        if(isActive){
            posX += vx;
            posY += vy;
        }
    }
}
