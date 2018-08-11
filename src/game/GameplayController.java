package game;

import actors.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
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
    private final static int OPACITY_CYCLE_INTERVAL = 5000;
    private int[] opacityLevel = {0, 75, 150, 75};
    private int opacityLevelCounter = 0;

    private int road1Pos = Stage.HEIGHT * -1;
    private int road2Pos = 0;
    private int score = 0;
    private int health = 3;

    /**
     * @param canvas
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
     * @param g
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

        // Draw score
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + getScore(), 50, 50);

        // Draw health
        g.drawString("Health: " + health, 50, 100);

        for (int i = 0; i < actors.size(); i++) {
            Actor actor = actors.get(i);
            actor.paint(g);
        }
        player.paint(g);
        obstacleManager.paint(g);
        pickupManager.paint(g);
        paintOverlay(g);
    }

    public void incrementOverlayLevel() {
        opacityTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                opacityLevelCounter++;
                incrementOverlayLevel();

            }
        }, OPACITY_CYCLE_INTERVAL);
    }


    public void paintOverlay(Graphics g) {
        Color color = new Color(0, 0, 0, opacityLevel[opacityLevelCounter % opacityLevel.length]);
        g.setColor(color);
        g.fillRect(0, 0, 1000, 1000);
    }

    /**
     * @param e
     */
    @Override
    public void triggerKeyPress(KeyEvent e) {
        playerPressedHandler.handleInput(e);
    }


    /**
     * @param e
     */
    @Override
    public void triggerKeyRelease(KeyEvent e) {
        playerReleasedHandler.handleInput(e);
    }

    /**
     *
     */
    public void checkCollision() {

        if (obstacleManager.checkCollision(player)) {
            if (!decreaseHealth()) {
                obstacleManager.stop();
                System.out.println("FINAL SCORE: " + getScore());
                PlayerInventory.setHighScore(getScore());
                canvas.initGameOverScreen(getScore());

            }
        }

    }

    /**
     * @return
     */
    public boolean decreaseHealth() {
        health--;
        if (health > 0) {
            return true;
        }
        health = 3;
        return false;

    }


    public void update() {
        player.update();
        obstacleManager.update();
        pickupManager.update();
        updateScore();
    }

    public void updateScore() {
        this.score++;
    }

    public int getScore() {
        return score / Stage.DESIRED_FPS;
    }


}
