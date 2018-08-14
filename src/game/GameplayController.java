package game;

import actors.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controls gameplay.
 */
public class GameplayController implements KeyboardControllable {

    private MooseGame canvas;

    private ArrayList<Actor> actors = new ArrayList<>();
    private Player player;
    private ObstacleManager obstacleManager;
    private PickupManager pickupManager;

    private InputHandler playerPressedHandler;
    private InputHandler playerReleasedHandler;

    private Timer opacityTimer = new Timer();
    private final static int OPACITY_CYCLE_INTERVAL = 1000;
    private int[] opacityLevel = {0, 25, 50, 75, 100, 125, 150, 175, 200, 175, 150, 125, 100, 75, 50, 25};
    private int opacityLevelCounter = 0;

    private int road1Pos = MooseGame.HEIGHT * -1;
    private int road2Pos = 0;
    private int score = 0;
    private int health = 3;

    private boolean fogLightsActive = false;
    private boolean invincibilityActive = false;
    private boolean slowMotionActive = false;

    private static final int FOG_LIGHTS_DURATION = 15 * 1000;
    private static final int INVINCIBILITY_DURATION = 15 * 1000;
    private static final int SLOW_MOTION_DURATION = 15 * 1000;

    private Timer fogLightsTimer = new Timer();
    private Timer invincibilityTimer = new Timer();
    private Timer slowMotionTimer = new Timer();

    /**
     * Constructs a GameplayController.
     *
     * @param canvas game window
     */
    public GameplayController(MooseGame canvas) {
        this.canvas = canvas;

        player = new Player(canvas);
        playerPressedHandler = new InputHandler(canvas, player, InputHandler.Action.PRESS);
        playerReleasedHandler = new InputHandler(canvas, player, InputHandler.Action.RELEASE);

        obstacleManager = new ObstacleManager(canvas);
        pickupManager = new PickupManager(canvas);

        incrementOverlayLevel();
    }

    /**
     * Paints game
     *
     * @param g Graphics object being painted to
     */
    public void paint(Graphics g) {

        road1Pos += 10;
        road2Pos += 10;

        if (road1Pos >= MooseGame.HEIGHT) {
            road1Pos = MooseGame.HEIGHT * -1;
        }

        if (road2Pos >= MooseGame.HEIGHT) {
            road2Pos = MooseGame.HEIGHT * -1;
        }

        // Draw road
        g.drawImage(ResourceLoader.getInstance().getSprite("road.png"), 0, road1Pos, canvas);
        g.drawImage(ResourceLoader.getInstance().getSprite("road2.png"), 0, road2Pos, canvas);

        g.setColor(Color.WHITE);

        // Draw score
        Font scoreFont = new Font("Impact", Font.PLAIN, 50);
        FontMetrics metrics = g.getFontMetrics(scoreFont);
        g.setFont(scoreFont);

        String scoreText = "" + getScore();
        g.drawString(scoreText, MooseGame.WIDTH - metrics.stringWidth(scoreText) - 25, 50);

        // Draw health
        Font healthFont = new Font("Impact", Font.PLAIN, 45);
        metrics = g.getFontMetrics(healthFont);
        g.setFont(healthFont);
        g.drawImage(ResourceLoader.getInstance().getSprite("heart.png"), 10, 5, canvas);
        g.drawString("" + health, 10 + (100 - metrics.stringWidth("" + health)) / 2, 75);

        // Draw Coins
        g.drawImage(ResourceLoader.getInstance().getSprite("coin.png"), 10, 120, canvas);
        g.drawString("" + pickupManager.getCoinsPickedUp(), 75, 165);

        // Draw powerups
        if (!fogLightsActive || canvas.getSpriteBlinkStatus()) {
            g.drawImage(ResourceLoader.getInstance().getSprite("foglights.png"), 680, MooseGame.HEIGHT - 210, canvas);
        }

        if (!invincibilityActive || canvas.getSpriteBlinkStatus()) {
            g.drawImage(ResourceLoader.getInstance().getSprite("invincible.png"), 680, MooseGame.HEIGHT - 150, canvas);
        }

        if (!slowMotionActive || canvas.getSpriteBlinkStatus()) {
            g.drawImage(ResourceLoader.getInstance().getSprite("slowmotion.png"), 680, MooseGame.HEIGHT - 90, canvas);
        }

        String fCount = Integer.toString(PlayerInventory.getFogLightsCount());
        String iCount = Integer.toString(PlayerInventory.getInvincibilityCount());
        String sCount = Integer.toString(PlayerInventory.getSlowMotionCount());
        g.drawString(fCount, MooseGame.WIDTH - 100 - metrics.stringWidth(fCount), MooseGame.HEIGHT - 170);
        g.drawString(iCount, MooseGame.WIDTH - 100 - metrics.stringWidth(iCount), MooseGame.HEIGHT - 110);
        g.drawString(sCount, MooseGame.WIDTH - 100 - metrics.stringWidth(sCount), MooseGame.HEIGHT - 50);


        for (int i = 0; i < actors.size(); i++) {
            Actor actor = actors.get(i);
            actor.paint(g);
        }

        if (!invincibilityActive || canvas.getSpriteBlinkStatus()) {
            player.paint(g);
        }

        obstacleManager.paint(g);
        pickupManager.paint(g);
        paintOverlay(g);
    }

    /**
     * Increments fog lights opacity level.
     */
    public void incrementOverlayLevel() {

        opacityTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!fogLightsActive) {
                    opacityLevelCounter++;
                }
                incrementOverlayLevel();
            }
        }, (opacityLevelCounter % opacityLevel.length) == 0 ? opacityLevel.length * OPACITY_CYCLE_INTERVAL : OPACITY_CYCLE_INTERVAL);

    }

    /**
     * Sets overlay graphics.
     *
     * @param g Graphics object being painted to
     */
    public void paintOverlay(Graphics g) {
        Color color = new Color(255, 255, 255, opacityLevel[opacityLevelCounter % opacityLevel.length]);
        g.setColor(color);
        g.fillRect(0, 0, 1000, 1000);
    }

    /**
     * Handles key control press event.
     *
     * @param e KeyEvent key press event
     */
    @Override
    public void triggerKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_1:
            case KeyEvent.VK_NUMPAD1:
                activateFogLights();
                break;
            case KeyEvent.VK_2:
            case KeyEvent.VK_NUMPAD2:
                activateInvincibilityPowerup();
                break;
            case KeyEvent.VK_3:
            case KeyEvent.VK_NUMPAD3:
                activateSlowMotion();
                break;
        }
        playerPressedHandler.handleInput(e);
    }


    /**
     * Handles key control release event
     *
     * @param e KeyEvent key release event
     */
    @Override
    public void triggerKeyRelease(KeyEvent e) {
        playerReleasedHandler.handleInput(e);
    }

    /**
     * Checks for a collision between Player and Obstacle or Pickup.
     */
    public void checkCollision() {

        if (obstacleManager.checkCollision(player)) {
            damagePlayer();
        }

        pickupManager.checkCollision(player);

        // Damages player when their car leaves the road.
        if (player.getX() < 75 || player.getX() > MooseGame.WIDTH - 125) {
            damagePlayer();
            player.setX(MooseGame.WIDTH / 2 - 25);
        }

    }

    /**
     * Damages player and handles player death
     */
    public void damagePlayer() {
        if (!invincibilityActive) {
            if (!decreaseHealth()) {
                canvas.playSound("gameover.wav");
                obstacleManager.stop();
                pickupManager.stop();
                opacityTimer.cancel();
                opacityTimer.purge();
                PlayerInventory.addCurrency(pickupManager.getCoinsPickedUp());
                PlayerInventory.clearPowerups();
                PlayerInventory.setHighScore(getScore());
                PlayerInventory.saveToFile();
                canvas.initGameOverScreen(getScore(), pickupManager.getCoinsPickedUp());
            } else { // Player damaged, but has health remaining
                activateInvincibility(3000);
            }
        }
    }

    /**
     * Decreases health.
     *
     * @return boolean true if player health greater than zero.
     */
    public boolean decreaseHealth() {
        health--;
        if (health > 0) {
            return true;
        }
        health = 3;
        return false;

    }

    /**
     * Updates player, obstacleManager, and score status.
     */
    public void update() {
        player.update();
        obstacleManager.update();
        pickupManager.update();
        updateScore();
    }

    /**
     * Increments score value.
     */
    public void updateScore() {
        this.score++;
    }

    /**
     * Calculates score.
     *
     * @return game score
     */
    public int getScore() {
        return score / MooseGame.DESIRED_FPS;
    }

    /**
     * Check if fog lights powerup is active.
     *
     * @return whether fog Lights is active
     */
    public boolean areFogLightsActive() {
        return fogLightsActive;
    }

    /**
     * Activate Fog Lights powerup, opacity level is changed
     * to zero for a fixed length of time.
     */
    public void activateFogLights() {
        if (!fogLightsActive && PlayerInventory.useFogLightsPowerup()) {
            fogLightsActive = true;
            canvas.playSound("powerup.wav");
            opacityLevelCounter = 0;
            fogLightsTimer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            fogLightsActive = false;
                        }
                    }, FOG_LIGHTS_DURATION);

        }
    }

    /**
     * Checks if Invincibility powerup is active.
     *
     * @return Whether invincibility is active
     */
    public boolean isInvincibilityActive() {
        return invincibilityActive;
    }

    /**
     * Activates invincibility powerup.
     */
    public void activateInvincibilityPowerup() {
        if (!invincibilityActive && PlayerInventory.useInvincibilityPowerup()) {
            canvas.playSound("powerup.wav");
            activateInvincibility(INVINCIBILITY_DURATION);
        }
    }

    /**
     * Activates invincibility.
     *
     * @param time fixed length of time
     */
    public void activateInvincibility(int time) {
        invincibilityActive = true;
        invincibilityTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        invincibilityActive = false;
                    }
                }, time);
    }

    /**
     * Checks if Slow Motion powerup is active.
     *
     * @return Whether Slow Motion is active.
     */
    public boolean isSlowMotionActive() {
        return slowMotionActive;
    }

    /**
     * Activates Slow Motion powerup.
     */
    public void activateSlowMotion() {
        if (!slowMotionActive && PlayerInventory.useSlowMotionPowerup()) {
            slowMotionActive = true;
            canvas.playSound("powerup.wav");
            player.setActorSpeed(20);
            slowMotionTimer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            slowMotionActive = false;
                            player.setActorSpeed(10);
                        }
                    }, SLOW_MOTION_DURATION);

        }
    }
}
