package game;

import actors.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Handles creation of in-game screen
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

    private int road1Pos = Stage.HEIGHT * -1;
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
     * Constructor for GameplayController.
     *
     * @param canvas game window
     */
    public GameplayController(MooseGame canvas) {
        this.canvas = canvas;

        player = new Player(canvas);
        playerPressedHandler = new InputHandler(canvas, player);
        playerPressedHandler.action = InputHandler.Action.PRESS;
        playerReleasedHandler = new InputHandler(canvas, player);
        playerReleasedHandler.action = InputHandler.Action.RELEASE;

        obstacleManager = new ObstacleManager(canvas);
        pickupManager = new PickupManager(canvas);


        incrementOverlayLevel();


    }

    /**
     * Renders graphics and dimensions for game.
     *
     * @param g game window
     */
    public void paint(Graphics g) {

        road1Pos += 10;
        road2Pos += 10;

        if (road1Pos >= Stage.HEIGHT) {
            road1Pos = Stage.HEIGHT * -1;
        }

        if (road2Pos >= Stage.HEIGHT) {
            road2Pos = Stage.HEIGHT * -1;
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
        g.drawString(scoreText, Stage.WIDTH - metrics.stringWidth(scoreText) - 25, 50);

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
        g.drawImage(ResourceLoader.getInstance().getSprite("foglights.png"), 680, Stage.HEIGHT - 210, canvas);
        g.drawImage(ResourceLoader.getInstance().getSprite("invincible.png"), 680, Stage.HEIGHT - 150, canvas);
        g.drawImage(ResourceLoader.getInstance().getSprite("slowmotion.png"), 680, Stage.HEIGHT - 90, canvas);


        String fCount = Integer.toString(PlayerInventory.getFogLightsCount());
        String iCount = Integer.toString(PlayerInventory.getInvincibilityCount());
        String sCount = Integer.toString(PlayerInventory.getSlowMotionCount());
        g.drawString(fCount, Stage.WIDTH - 100 - metrics.stringWidth(fCount), Stage.HEIGHT - 170);
        g.drawString(iCount, Stage.WIDTH - 100 - metrics.stringWidth(iCount), Stage.HEIGHT - 110);
        g.drawString(sCount, Stage.WIDTH - 100 - metrics.stringWidth(sCount), Stage.HEIGHT - 50);


        for (int i = 0; i < actors.size(); i++) {
            Actor actor = actors.get(i);
            actor.paint(g);
        }
        player.paint(g);
        obstacleManager.paint(g);
        pickupManager.paint(g);
        paintOverlay(g);
    }

    /**
     * Increases and decreases level of opacity incrementally during gameplay if fog lights
     * powerup is not active.
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
     * @param g overlay to be rendered
     */
    public void paintOverlay(Graphics g) {
        Color color = new Color(255, 255, 255, opacityLevel[opacityLevelCounter % opacityLevel.length]);
        g.setColor(color);
        g.fillRect(0, 0, 1000, 1000);
    }

    /**
     * Handles key control press event.
     *
     * @param e key press event
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
     * @param e key release event
     */
    @Override
    public void triggerKeyRelease(KeyEvent e) {
        playerReleasedHandler.handleInput(e);
    }

    /**
     * Checks whether a collision has occured in-game, displays score if true,
     * and resets high score to current score.
     */
    public void checkCollision() {

        if (obstacleManager.checkCollision(player)) {
            damagePlayer();
        }

        pickupManager.checkCollision(player);

        if (player.getX() < 75 || player.getX() > Stage.WIDTH - 125) {
            damagePlayer();
            player.setX(Stage.WIDTH / 2 - 25);
        }

    }

    /**
     * Processes damage to the player during gameplay
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
            }
        }
    }

    /**
     * Checks if there has been a decrease in health.
     *
     * @return decrease or lack thereof
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
        return score / Stage.DESIRED_FPS;
    }

    /**
     * Check if Fog lights powerup is active.
     *
     * @return Whether fog Lights is active
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
     * Check if Invincibility powerup is active.
     *
     * @return Whether invincibility is active
     */
    public boolean isInvincibilityActive() {
        return invincibilityActive;
    }

    /**
     * Activate Invincibility powerup from player inventory.
     */
    public void activateInvincibilityPowerup() {
        if (!invincibilityActive && PlayerInventory.useInvincibilityPowerup()) {
            activateInvincibility(INVINCIBILITY_DURATION);
        }
    }

    /**
     * Activate Invincibility, player does not incur damage for
     * a fixed length of time during gameplay.
     *
     * @param time fixed length of time
     */
    public void activateInvincibility(int time) {
        invincibilityActive = true;
        canvas.playSound("powerup.wav");
        invincibilityTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        invincibilityActive = false;
                    }
                }, time);
    }

    /**
     * Check if Slow Motion powerup is active.
     *
     * @return Whether Slow Motion is active.
     */
    public boolean isSlowMotionActive() {
        return slowMotionActive;
    }

    /**
     * Activate Slow Motion powerup, speed rate of game play is
     * reduced for a fixed length of time.
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
