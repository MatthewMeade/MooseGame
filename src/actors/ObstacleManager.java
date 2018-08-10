package actors;

import game.MooseGame;
import game.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ObstacleManager {

    private static ArrayList<Obstacle> mooseObstacles = new ArrayList<>();
    private static ArrayList<Obstacle> vehicleObstacles = new ArrayList<>();
    private static ArrayList<Obstacle> staticObstacles = new ArrayList<>();

    private static ArrayList<Obstacle> activeObstacles = new ArrayList<>();

    private static boolean gameplayActive;

    private MooseGame canvas;

    public ObstacleManager(MooseGame canvas) {
        this.canvas = canvas;
        gameplayActive = true;
        spawnMoose();


    }

    public ArrayList<Obstacle> getObstacles(){
        return activeObstacles;
    }

    public void stop(){
        System.out.println("Stopping moose spawn");
        gameplayActive = false;
    }

    public void spawnMoose() {
        System.out.println("Spawning moose");
        Obstacle moose = new MooseObstacle(canvas);
        moose.spawn();
        activeObstacles.add(moose);

        Random random = new Random();

        if (!gameplayActive) {
            return;
        }

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        spawnMoose();
                    }
                }, 500 * (random.nextInt(5) + 5 ));
    }

    public void update() {
        for (int i = 0; i < activeObstacles.size(); i++) {

            Obstacle o = activeObstacles.get(i);
            o.update();

            if (o.posY > Stage.HEIGHT || o.isActive == false) {
                activeObstacles.remove(o);
            }
        }
    }

    public void paint(Graphics g) {
        for (int i = 0; i < activeObstacles.size(); i++) {
            activeObstacles.get(i).paint(g);
        }
    }


}
