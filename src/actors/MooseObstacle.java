package actors;

import game.MooseGame;
import game.Stage;

import java.util.Random;

public class MooseObstacle extends Obstacle{




    public MooseObstacle(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"moose.png"};


    }

    @Override
    public void spawn() {

        isActive = true;
        Random random = new Random();

        boolean left = random.nextBoolean();
        setX(left ? -100 : Stage.WIDTH);
        setY((int)((Stage.HEIGHT / 4) * random.nextDouble()));

        setWidth(100);
        setHeight(100);

        setVx((left ? 1 : -1) * (random.nextInt(5) + 5));
        setVy(random.nextInt(5) + 5);
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
