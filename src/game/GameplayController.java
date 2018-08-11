package game;

import actors.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Handles creation of in-game screen
 */
public class GameplayController implements KeyboardControllable {

    private MooseGame canvas;

    private ArrayList<Actor> actors = new ArrayList<>();
    private Player player;
    private ObstacleManager obstacleManager;

    private InputHandler playerPressedHandler;
    private InputHandler playerReleasedHandler;

    private int road1Pos = Stage.HEIGHT * -1;
    private int road2Pos = 0;
    private int score = 0;
    private int health = 3;

    /**
     * Constructor for GameplayController.
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
    }

    /**
     * Renders graphics and dimensions for game.
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

    }

    /**
     * Handles key control press event.
     * @param e key press event
     */
    @Override
    public void triggerKeyPress(KeyEvent e) {
        playerPressedHandler.handleInput(e);
    }


    /**
     * Handles key control release event
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
            if (!decreaseHealth()) {
                obstacleManager.stop();
                System.out.println("FINAL SCORE: " + getScore());
                PlayerInventory.setHighScore(getScore());
                canvas.initGameOverScreen(getScore());

            }
        }

    }

    /**
     * Checks if there has been a decrease in health.
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
     * @return game score
     */
    public int getScore() {
        return score / Stage.DESIRED_FPS;
    }
}
