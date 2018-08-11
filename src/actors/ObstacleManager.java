package actors;

import game.MooseGame;
import game.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Provides functionality to each separate obstacle during gameplay
 */
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


        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("Spawning");
                        spawnMoose();
                        spawnStatic();
                        spawnVehicle();
                    }
                }, 1000);



    }

    public ArrayList<Obstacle> getObstacles(){
        return activeObstacles;
    }

    public void stop(){
        activeObstacles = new ArrayList<Obstacle>();
        gameplayActive = false;
    }


    public void spawnMoose(){

        if (!gameplayActive) {
            return;
        }

        Obstacle obstacle = new MooseObstacle(canvas);
        obstacle.spawn();
        activeObstacles.add(obstacle);

        Random random = new Random();

        int maxTime = 6;
        int minTime = 5;

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("Active Objects: " + activeObstacles.size());

                        spawnMoose();
                    }
                }, 1000 * (random.nextInt(maxTime - minTime) + minTime ));
    }

    public void spawnStatic(){

        if (!gameplayActive) {
            return;
        }

        Obstacle obstacle = new StaticObstacle(canvas);
        obstacle.spawn();
        activeObstacles.add(obstacle);

        Random random = new Random();


        int maxTime = 3;
        int minTime = 2;

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        spawnStatic();
                    }
                }, 1000 * (random.nextInt(maxTime - minTime) + minTime ));
    }

    public void spawnVehicle(){

        if (!gameplayActive) {
            return;
        }

        Obstacle obstacle = new VehicleObstacle(canvas);
        obstacle.spawn();
        activeObstacles.add(obstacle);

        Random random = new Random();

        int maxTime = 2;
        int minTime = 1;

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        spawnVehicle();
                    }
                }, 1000 * (random.nextInt(maxTime - minTime) + minTime ));
    }

    public void update() {

        if (!gameplayActive) {
            return;
        }

        for (int i = 0; i < activeObstacles.size(); i++) {

            Obstacle o = activeObstacles.get(i);
            o.update();

            if (o.posY > Stage.HEIGHT || o.isActive == false) {
                activeObstacles.remove(o);
            }
        }
    }

    public void paint(Graphics g) {
        if (!gameplayActive) {
            return;
        }

        for (int i = 0; i < activeObstacles.size(); i++) {
            activeObstacles.get(i).paint(g);
        }
    }

    public boolean checkCollision(Actor player) {
        if (!gameplayActive) {
            return false;
        }

        for (int i = 0; i < activeObstacles.size(); i++) {
            Obstacle o = activeObstacles.get(i);

            if (o.getBounds().intersects(player.getBounds())) {
                o.despawn();

                return true;
            }
        }

        return false;
    }


}
