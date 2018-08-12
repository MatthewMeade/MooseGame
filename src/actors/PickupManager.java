package actors;

import game.MooseGame;
import game.PlayerInventory;
import game.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PickupManager {

    private static ArrayList<Pickup> fogLightsPickups = new ArrayList<>();
    private static ArrayList<Pickup> slowMotionPickups = new ArrayList<>();
    private static ArrayList<Pickup> invincibilityPickups = new ArrayList<>();

    private static ArrayList<Pickup> activePickups = new ArrayList<>();

    private MooseGame canvas;

    private static final int SPAWN_WAIT_TIME = 2 * 1000;

    private static final int FOG_LIGHTS_MIN_SPAWN_TIME = 8 * 1000;
    private static final int FOG_LIGHTS_MAX_SPAWN_TIME = 18 * 1000;
    private Timer fogLightsTimer = new Timer();

    private static final int SLOW_MOTION_MIN_SPAWN_TIME = 10 * 1000;
    private static final int SLOW_MOTION_MAX_SPAWN_TIME = 20 * 1000;
    private Timer slowMotionTimer = new Timer();

    private static final int INVINCIBILITY_MIN_SPAWN_TIME = 12 * 1000;
    private static final int INVINCIBILITY_MAX_SPAWN_TIME = 22 * 1000;
    private Timer invincibilityTimer = new Timer();

    public PickupManager(MooseGame canvas) {
        this.canvas = canvas;

        /**
         * Provides chronological order in which obstacles spawn
         */
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        spawnFogLightsPickup();
                        spawnSlowMotionPickup();
                        spawnInvincibilityPickup();
                    }
                }, SPAWN_WAIT_TIME);
    }

    public ArrayList<Pickup> getPickups() {
        return activePickups;
    }

    public void stop() {
        activePickups = new ArrayList<Pickup>();

        fogLightsTimer.cancel();
        fogLightsTimer.purge();

        slowMotionTimer.cancel();
        slowMotionTimer.purge();

        invincibilityTimer.cancel();
        invincibilityTimer.purge();
    }

    public void spawnFogLightsPickup() {
        Random random = new Random();

        fogLightsTimer.cancel();
        fogLightsTimer.purge();
        fogLightsTimer = new Timer();
        fogLightsTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Pickup pickup = new FogLightsPickup(canvas);
                        pickup.spawn();
                        activePickups.add(pickup);
                        spawnFogLightsPickup();
                    }
                }, (random.nextInt(FOG_LIGHTS_MAX_SPAWN_TIME - FOG_LIGHTS_MIN_SPAWN_TIME) + FOG_LIGHTS_MIN_SPAWN_TIME));
    }

    public void spawnSlowMotionPickup() {
        Random random = new Random();

        slowMotionTimer.cancel();
        slowMotionTimer.purge();
        slowMotionTimer = new Timer();
        slowMotionTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Pickup pickup = new SlowMotionPickup(canvas);
                        pickup.spawn();
                        activePickups.add(pickup);
                        spawnSlowMotionPickup();
                    }
                }, (random.nextInt(SLOW_MOTION_MAX_SPAWN_TIME - SLOW_MOTION_MIN_SPAWN_TIME) + SLOW_MOTION_MIN_SPAWN_TIME));
    }


    public void spawnInvincibilityPickup() {


        Random random = new Random();

        invincibilityTimer.cancel();
        invincibilityTimer.purge();
        invincibilityTimer = new Timer();
        invincibilityTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Pickup pickup = new TemporaryInvincibilityPickup(canvas);
                        pickup.spawn();
                        activePickups.add(pickup);
                        spawnInvincibilityPickup();
                    }
                }, (random.nextInt(INVINCIBILITY_MAX_SPAWN_TIME - INVINCIBILITY_MIN_SPAWN_TIME) + INVINCIBILITY_MIN_SPAWN_TIME));
    }


    public void update() {
        for (int i = 0; i < activePickups.size(); i++) {

            Pickup p = activePickups.get(i);
            p.update();

            if (p.posY > Stage.HEIGHT || p.isActive == false) {
                activePickups.remove(p);
            }
        }

    }

    public void paint(Graphics g) {

        for (int i = 0; i < activePickups.size(); i++) {
            activePickups.get(i).paint(g);
        }
    }

    public boolean checkCollision(Actor player) {

        for (int i = 0; i < activePickups.size(); i++) {
            Pickup p = activePickups.get(i);

            if (p.getBounds().intersects(player.getBounds())) {
                p.despawn();
                if (p instanceof FogLightsPickup) {
                    PlayerInventory.incrementFogLights();
                } else if (p instanceof SlowMotionPickup) {
                    PlayerInventory.incrementSlowMotion();
                } else if (p instanceof TemporaryInvincibilityPickup) {
                    PlayerInventory.incrementInvincibility();
                }
                return true;
            }
        }

        return false;
    }


}



