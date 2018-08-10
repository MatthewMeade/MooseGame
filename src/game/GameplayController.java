package game;

import actors.Actor;
import actors.KeyboardControllable;
import actors.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameplayController implements KeyboardControllable {

    private MooseGame canvas;

    private ArrayList<Actor> actors = new ArrayList<>();
    private Player player;

    private InputHandler playerPressedHandler;
    private InputHandler playerReleasedHandler;


    public GameplayController(MooseGame canvas) {
        this.canvas = canvas;

        player = new Player(canvas);
        playerPressedHandler = new InputHandler(canvas, player);
        playerPressedHandler.action = InputHandler.Action.PRESS;
        playerReleasedHandler = new InputHandler(canvas, player);
        playerReleasedHandler.action = InputHandler.Action.RELEASE;

    }

    public void paint(Graphics g) {
        for (int i = 0; i < actors.size(); i++) {
            Actor actor = actors.get(i);
            actor.paint(g);
        }
        player.paint(g);
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
    }
}
