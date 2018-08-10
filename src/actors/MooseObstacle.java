package actors;

import game.MooseGame;
import game.Stage;

import java.util.Random;

public class MooseObstacle extends Obstacle{


    public MooseObstacle(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"moose.png"};

        Random random = new Random();
        setX(-100);
        setY(0);
//        setY((int)(random.nextDouble() * Stage.HEIGHT));

        setWidth(100);
        setHeight(100);

        setVx(5);
        setVy(2);
    }

    @Override
    public void spawn() {
        Random random = new Random();
    }

    @Override
    public void despawn() {

    }

    public void update() {
        posX += vx;
        posY += vy;
    }


}
