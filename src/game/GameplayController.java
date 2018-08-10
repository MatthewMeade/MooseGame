package game;

import actors.Actor;
import actors.KeyboardControllable;
import actors.ObstacleManager;
import actors.Player;

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


    public GameplayController(MooseGame canvas) {
        this.canvas = canvas;

        player = new Player(canvas);
        playerPressedHandler = new InputHandler(canvas, player);
        playerPressedHandler.action = InputHandler.Action.PRESS;
        playerReleasedHandler = new InputHandler(canvas, player);
        playerReleasedHandler.action = InputHandler.Action.RELEASE;

        obstacleManager= new ObstacleManager(canvas);
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

        g.drawImage(ResourceLoader.getInstance().getSprite("road.png"), 0, road1Pos, canvas);
        g.drawImage(ResourceLoader.getInstance().getSprite("road2.png"), 0, road2Pos, canvas);

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
//        if (ball.getBounds().intersects(paddleLeft.getBounds())) {
//            ball.collision(paddleLeft);
//        } else if (ball.getBounds().intersects(paddleRight.getBounds())) {
//            ball.collision(paddleRight);
//        }

    }

    public void update() {
        player.update();
        obstacleManager.update();
    }
}
