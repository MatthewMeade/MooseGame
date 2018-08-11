package game;

import actors.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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

    public GameplayController(MooseGame canvas) {
        this.canvas = canvas;

        player = new Player(canvas);
        playerPressedHandler = new InputHandler(canvas, player);
        playerPressedHandler.action = InputHandler.Action.PRESS;
        playerReleasedHandler = new InputHandler(canvas, player);
        playerReleasedHandler.action = InputHandler.Action.RELEASE;

        obstacleManager = new ObstacleManager(canvas);
    }

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

    @Override
    public void triggerKeyPress(KeyEvent e) {
        playerPressedHandler.handleInput(e);
    }

    //
    @Override
    public void triggerKeyRelease(KeyEvent e) {
        playerReleasedHandler.handleInput(e);
    }

    public void checkCollision() {
        ArrayList<Obstacle> obstacles = obstacleManager.getObstacles();

        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle o = obstacles.get(0);

            if (o.getBounds().intersects(player.getBounds())) {
                o.despawn();

                // End of game
                if (!decreaseHealth()) {
                    obstacleManager.stop();
                    System.out.println("FINAL SCORE: " + getScore());
                    PlayerInventory.setHighScore(getScore());
                    canvas.initGameOverScreen(getScore());
                }
            }
        }

    }

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
        updateScore();
    }

    public void updateScore() {
        this.score++;
    }

    public int getScore() {
        return score / Stage.DESIRED_FPS;
    }
}
