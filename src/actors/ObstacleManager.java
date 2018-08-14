package actors;

import game.MooseGame;
import game.MooseGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Spawns, renders graphics for, and removes obstacles during gameplay.
 */
public class ObstacleManager {

    private static ArrayList<Obstacle> activeObstacles = new ArrayList<>();
    private static ArrayList<Obstacle> activeObstaclesMoving = new ArrayList<>();
    private static ArrayList<Obstacle> activeObstaclesStatic = new ArrayList<>();


    private MooseGame canvas;

    private static final int SPAWN_WAIT_TIME = 2 * 1000;

    private static final int MOOSE_MIN_SPAWN_TIME = 5 * 1000;
    private static final int MOOSE_MAX_SPAWN_TIME = 6 * 1000;
    private Timer mooseTimer = new Timer();

    private static final int STATIC_MIN_SPAWN_TIME = 2 * 1000;
    private static final int STATIC_MAX_SPAWN_TIME = 3 * 1000;
    private Timer staticTimer = new Timer();

    private static final int VEHICLE_MIN_SPAWN_TIME = 1 * 1000;
    private static final int VEHICLE_MAX_SPAWN_TIME = 2 * 1000;
    private Timer vehicleTimer = new Timer();

    /**
     * Constructs a new obstacle manager.
     *
     * @param canvas game window
     */
    public ObstacleManager(MooseGame canvas) {
        this.canvas = canvas;

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        spawnMoose();
                        spawnStatic();
                        spawnVehicle();
                    }
                }, SPAWN_WAIT_TIME);
    }

    /**
     * Clears active obstacles and stops obstacle spawning timers.
     */
    public void stop() {
        activeObstacles = new ArrayList<>();
        activeObstaclesMoving = new ArrayList<>();
        activeObstaclesStatic = new ArrayList<>();


        mooseTimer.cancel();
        mooseTimer.purge();

        staticTimer.cancel();
        staticTimer.purge();

        vehicleTimer.cancel();
        vehicleTimer.purge();
    }

    /**
     * Spawns a moose obstacle and adds it to the active obstacle arrays.
     */
    public void spawnMoose() {

        Obstacle obstacle = new MooseObstacle(canvas);
        obstacle.spawn();
        activeObstacles.add(obstacle);
        activeObstaclesMoving.add(obstacle);

        Random random = new Random();

        mooseTimer.cancel();
        mooseTimer.purge();

        mooseTimer = new Timer();
        mooseTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        spawnMoose();
                    }
                }, (random.nextInt(MOOSE_MAX_SPAWN_TIME - MOOSE_MIN_SPAWN_TIME) + MOOSE_MIN_SPAWN_TIME));
    }

    /**
     * Spawns a static obstacle and adds it to the active obstacle arrays.
     */
    public void spawnStatic() {
        Obstacle obstacle = new StaticObstacle(canvas);
        obstacle.spawn();
        activeObstacles.add(obstacle);
        activeObstaclesStatic.add(obstacle);


        Random random = new Random();

        staticTimer.cancel();
        staticTimer.purge();
        staticTimer = new Timer();
        staticTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        spawnStatic();
                    }
                }, (random.nextInt(STATIC_MAX_SPAWN_TIME - STATIC_MIN_SPAWN_TIME) + STATIC_MIN_SPAWN_TIME));
    }

    /**
     * Spawns a vehicle obstacle and adds it to the active obstacle arrays.
     */
    public void spawnVehicle() {

        Obstacle obstacle = new VehicleObstacle(canvas);
        obstacle.spawn();
        activeObstacles.add(obstacle);
        activeObstaclesMoving.add(obstacle);


        Random random = new Random();

        vehicleTimer.cancel();
        vehicleTimer.purge();
        vehicleTimer = new Timer();
        vehicleTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        spawnVehicle();
                    }
                }, (random.nextInt(VEHICLE_MAX_SPAWN_TIME - VEHICLE_MIN_SPAWN_TIME) + VEHICLE_MIN_SPAWN_TIME));
    }

    /**
     * Updates obstacles and removes them from active obstacle array after they have left the screen
     */
    public void update() {

        for (int i = 0; i < activeObstacles.size(); i++) {

            Obstacle o = activeObstacles.get(i);
            o.update();

            if (o.posY > MooseGame.HEIGHT || o.isActive == false) {
                o.despawn();
                activeObstacles.remove(o);
            }
        }
    }

    /**
     * Renders graphics for obstacles, drawing static obstacles below moving obstacles
     *
     * @param g obstacle to be painted
     */
    public void paint(Graphics g) {

        for (int i = 0; i < activeObstaclesStatic.size(); i++) {
            activeObstaclesStatic.get(i).paint(g);
        }

        for (int i = 0; i < activeObstaclesMoving.size(); i++) {
            activeObstaclesMoving.get(i).paint(g);
        }
    }

    /**
     * Checks if the player has collided with an obstacle
     *
     * @param player Actor player object
     * @return Collision boolean true if player collided
     */
    public boolean checkCollision(Actor player) {

        for (int i = 0; i < activeObstacles.size(); i++) {
            Obstacle o = activeObstacles.get(i);

            if (o.getBounds().intersects(player.getBounds())) {
                o.despawn();
                canvas.playSound("explosion.wav");
                return true;
            }
        }

        return false;
    }


}
