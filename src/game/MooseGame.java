package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;


import javax.swing.JFrame;
import javax.swing.JPanel;

import actors.Actor;
import actors.PlayerCar;

public class MooseGame extends Stage implements KeyListener {

    private static final long serialVersionUID = 1L;


    private InputHandler keyPressedHandler;
    private InputHandler keyReleasedHandler;

    private InputHandler menuKeyPressedHandler;
    private InputHandler menuKeyReleasedHandler;


    public long usedTime; //time taken per game step
    public BufferStrategy strategy; //double buffering strategy

    private PlayerCar playerCar;
    private MenuController menuController;

    private int gameState = 0;


    public MooseGame() {
        //init the UI
        setBounds(0, 0, Stage.WIDTH, Stage.HEIGHT);
        setBackground(Color.BLUE);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(Stage.WIDTH, Stage.HEIGHT));
        panel.setLayout(null);

        panel.add(this);

        JFrame frame = new JFrame("Moose Game");
        frame.add(panel);

        frame.setBounds(0, 0, Stage.WIDTH, Stage.HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);

        //cleanup resources on exit
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ResourceLoader.getInstance().cleanup();
                System.exit(0);
            }
        });


        addKeyListener(this);

        //create a double buffer
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        requestFocus();
//        initWorld();
        initMenu();

        keyPressedHandler = new InputHandler(this, playerCar);
        keyPressedHandler.action = InputHandler.Action.PRESS;
        keyReleasedHandler = new InputHandler(this, playerCar);
        keyReleasedHandler.action = InputHandler.Action.RELEASE;

        menuKeyPressedHandler = new InputHandler(this, menuController);
        menuKeyPressedHandler.action = InputHandler.Action.PRESS;

        menuKeyReleasedHandler = new InputHandler(this, menuController);
        menuKeyReleasedHandler.action = InputHandler.Action.RELEASE;



    }


    public void initWorld() {

        playerCar = new PlayerCar(this);
    }

    public void initMenu() {
        menuController = new MenuController(this);
    }

    public void paintWorld() {

        //get the graphics from the buffer
        Graphics g = strategy.getDrawGraphics();
        //init image to background
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        //load subimage from the background

        if (gameState == 0) {
            paintMenu(g);
        } else if (gameState == 1) {

            //paint the actors
            for (int i = 0; i < actors.size(); i++) {
                Actor actor = actors.get(i);
                actor.paint(g);
            }
            playerCar.paint(g);
            paintFPS(g);
        }


        //swap buffer
        strategy.show();
    }

    public void paintFPS(Graphics g) {
        g.setColor(Color.RED);
        if (usedTime > 0)
            g.drawString(String.valueOf(1000 / usedTime) + " fps", 0, Stage.HEIGHT - 50);
        else
            g.drawString("--- fps", 0, Stage.HEIGHT - 50);
    }

    public void paint(Graphics g) {
    }

    public void paintMenu(Graphics g){
        menuController.paint(g);
    }

    public void updateWorld() {
        if (gameState == 1) {
            playerCar.update();
        }
    }

    private void checkCollision() {
//        if (ball.getBounds().intersects(paddleLeft.getBounds())) {
//            ball.collision(paddleLeft);
//        } else if (ball.getBounds().intersects(paddleRight.getBounds())) {
//            ball.collision(paddleRight);
//        }

    }

    public void loopSound(final String name) {
        new Thread(new Runnable() {
            public void run() {
                ResourceLoader.getInstance().getSound(name).loop();
            }
        }).start();
    }


    public void game() {
        //loopSound("music.wav");
        usedTime = 0;
        while (isVisible()) {
            long startTime = System.currentTimeMillis();
            checkCollision();
            updateWorld();
            paintWorld();

            usedTime = System.currentTimeMillis() - startTime;

            //calculate sleep time
            if (usedTime == 0) usedTime = 1;
            int timeDiff = 1000 / DESIRED_FPS - (int) (usedTime);
            if (timeDiff > 0) {
                try {
                    Thread.sleep(timeDiff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            usedTime = System.currentTimeMillis() - startTime;
        }
    }

    public void keyPressed(KeyEvent e) {

//        keyPressedHandler.handleInput(e);
        menuKeyPressedHandler.handleInput(e);
    }

    public void keyReleased(KeyEvent e) {
//        keyReleasedHandler.handleInput(e);
        menuKeyReleasedHandler.handleInput(e);
    }

    public void keyTyped(KeyEvent e) {
    }


    public static void main(String[] args) {
        MooseGame mooseGame = new MooseGame();
        mooseGame.game();

        Invaders invaders = new Invaders();
        invaders.game();
    }

}