package actors;

import game.MooseGame;
import game.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Spawns, renders graphics for, and removes obstacles during gameplay dependent on their status and position.
 */
public class ObstacleManager {

    private static ArrayList<Obstacle> mooseObstacles = new ArrayList<>();
    private static ArrayList<Obstacle> vehicleObstacles = new ArrayList<>();
    private static ArrayList<Obstacle> staticObstacles = new ArrayList<>();

    private static ArrayList<Obstacle> activeObstacles = new ArrayList<>();

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

    public ObstacleManager(MooseGame canvas) {
        this.canvas = canvas;

        /**
         * Provides chronological order in which obstacles spawn
         */
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
     * Gets value of an obstacle from array
     *
     * @return active obstacle
     */
    public ArrayList<Obstacle> getObstacles() {
        return activeObstacles;
    }

    /**
     *
     */
    public void stop() {
        activeObstacles = new ArrayList<Obstacle>();

        mooseTimer.cancel();
        mooseTimer.purge();

        staticTimer.cancel();
        staticTimer.purge();

        vehicleTimer.cancel();
        vehicleTimer.purge();

    }

    /**
     * Spawns a moose obstacle at random and adds its value to the active obstacle array
     */
    public void spawnMoose() {

        Obstacle obstacle = new MooseObstacle(canvas);
        obstacle.spawn();
        activeObstacles.add(obstacle);

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
     * Spawns a static obstacle at random and adds its value to the active obstacle array
     */
    public void spawnStatic() {
        Obstacle obstacle = new StaticObstacle(canvas);
        obstacle.spawn();
        activeObstacles.add(obstacle);

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
     * Spawns a vehicle obstacle at random and adds its value to the active obstacle array
     */
    public void spawnVehicle() {

        Obstacle obstacle = new VehicleObstacle(canvas);
        obstacle.spawn();
        activeObstacles.add(obstacle);

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
     * Removes obstacles from active obstacle array after they have left the screen
     */
    public void update() {

        for (int i = 0; i < activeObstacles.size(); i++) {

            Obstacle o = activeObstacles.get(i);
            o.update();

            if (o.posY > Stage.HEIGHT || o.isActive == false) {
                activeObstacles.remove(o);
            }
        }
    }

    /**
     * Render graphics for obstacles
     *
     * @param g obstacle to be painted
     */
    public void paint(Graphics g) {

        for (int i = 0; i < activeObstacles.size(); i++) {
            activeObstacles.get(i).paint(g);
        }
    }

    /**
     * Checks to see if current player has suffered a collision with an obstacle
     *
     * @param player Current game player
     * @return Collision status of user
     */
    public boolean checkCollision(Actor player) {

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
