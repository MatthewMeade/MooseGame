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

    private static final int SPAWN_WAIT_TIME = 2 * 1000;

    private static final int MOOSE_MIN_SPAWN_TIME = 5 * 1000;
    private static final int MOOSE_MAX_SPAWN_TIME = 6 * 1000;

    private static final int STATIC_MIN_SPAWN_TIME = 2 * 1000;
    private static final int STATIC_MAX_SPAWN_TIME = 3 * 1000;

    private static final int VEHICLE_MIN_SPAWN_TIME = 1 * 1000;
    private static final int VEHICLE_MAX_SPAWN_TIME = 2 * 1000;


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
                }, SPAWN_WAIT_TIME);



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

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        spawnMoose();
                    }
                }, (random.nextInt(MOOSE_MAX_SPAWN_TIME - MOOSE_MIN_SPAWN_TIME) + MOOSE_MIN_SPAWN_TIME ));
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
                }, (random.nextInt(STATIC_MAX_SPAWN_TIME - STATIC_MIN_SPAWN_TIME) + STATIC_MIN_SPAWN_TIME ));
    }

    public void spawnVehicle(){

        if (!gameplayActive) {
            return;
        }

        Obstacle obstacle = new VehicleObstacle(canvas);
        obstacle.spawn();
        activeObstacles.add(obstacle);

        Random random = new Random();

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        spawnVehicle();
                    }
                }, (random.nextInt(VEHICLE_MAX_SPAWN_TIME - VEHICLE_MIN_SPAWN_TIME) + VEHICLE_MIN_SPAWN_TIME ));
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
