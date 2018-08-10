package actors;

import game.MooseGame;

import java.util.ArrayList;

public abstract class Obstacle extends Actor{

    public  boolean isActive = false;

    public Obstacle(MooseGame canvas) {
        super(canvas);
    }

    public abstract void spawn();

    public abstract void despawn();



}
