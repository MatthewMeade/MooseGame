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

/**
 * MooseGame class extends Stage class and implements KeyListener interface
 */
public class MooseGame extends Stage implements KeyListener {

    private static final long serialVersionUID = 1L;


    private InputHandler gameKeyPressedHandler;
    private InputHandler gameKeyReleasedHandler;

    private InputHandler menuKeyPressedHandler;
    private InputHandler menuKeyReleasedHandler;


    public long usedTime; //time taken per game step
    public BufferStrategy strategy; //double buffering strategy

    private MenuController menuController;
    private GameplayController gameplayController;


    public enum gameStates {
        MENU,
        GAME
    }

    private gameStates gameState;

    /**
     * Instance of MooseGame class is created. User interface background
     * color and dimensions are initialized, with dimension values inherited
     * from Stage class and background color set to blue.
     */
    public MooseGame() {
        //init the UI
        setBounds(0, 0, Stage.WIDTH, Stage.HEIGHT);
        setBackground(Color.BLUE);

        /**
         * New instances of JPanel and JFrame are created, dimensions
         * inherited from the Stage class.
         */
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(Stage.WIDTH, Stage.HEIGHT));
        panel.setLayout(null);

        panel.add(this);

        JFrame frame = new JFrame("Moose Game");
        frame.add(panel);

        frame.setBounds(0, 0, Stage.WIDTH, Stage.HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);

        /**
         * WindowListener is added to the JFrame instance to clean up resources upon
         * closing of the window
         */
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });


        addKeyListener(this);

        //create a double buffer
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        requestFocus();
        initMenu();

    }


    /**
     * Method declares game state as Game, declares new instance of GameplayController class,
     * instantiates PRESS and RELEASE key action from InputHandler class.
     */
    public void initGame(){
        gameState = gameStates.GAME;
        gameplayController = new GameplayController(this);

        gameKeyPressedHandler = new InputHandler(this, gameplayController);
        gameKeyPressedHandler.action = InputHandler.Action.PRESS;
        gameKeyReleasedHandler = new InputHandler(this, gameplayController);
        gameKeyReleasedHandler.action = InputHandler.Action.RELEASE;
    }

    /**
     * Method declares game state as Menu, declares new instance of MenuController class,
     * instantiates PRESS and RELEASE key actions from InputHandler class.
     */
    public void initMenu() {
        gameState = gameStates.MENU;
        menuController = new MenuController(this);

        menuKeyPressedHandler = new InputHandler(this, menuController);
        menuKeyPressedHandler.action = InputHandler.Action.PRESS;
        menuKeyReleasedHandler = new InputHandler(this, menuController);
        menuKeyReleasedHandler.action = InputHandler.Action.RELEASE;
    }

    /**
     * Method to retrieve, set the background with conditional statement to apply correct settings based on game state.
     */
    public void paintWorld() {

        //get the graphics from the buffer
        Graphics g = strategy.getDrawGraphics();
        //init image to background
        g.setColor(getBackground());

        g.fillRect(0, 0, getWidth(), getHeight());


        //load subimage from the background

        paintFPS(g);

        if (menuController != null && gameState == gameStates.MENU) {
            menuController.paint(g);
        } else if (gameplayController != null && gameState == gameStates.GAME) {
            gameplayController.paint(g);
        }

        //swap buffer
        strategy.show();
    }

    /**
     *
     * @param g
     */
    public void paintFPS(Graphics g) {
        g.setColor(Color.RED);
        if (usedTime > 0)
            g.drawString(String.valueOf(1000 / usedTime) + " fps", 0, Stage.HEIGHT - 50);
        else
            g.drawString("--- fps", 0, Stage.HEIGHT - 50);
    }

    public void paint(Graphics g) {
    }

    public void paintMenu(Graphics g) {
        menuController.paint(g);
    }


    public void loopSound(final String name) {
        new Thread(new Runnable() {
            public void run() {
                ResourceLoader.getInstance().getSound(name).loop();
            }
        }).start();
    }

    /**
     *
     */
    public void game() {
        //loopSound("music.wav");
        usedTime = 0;
        while (isVisible()) {
            long startTime = System.currentTimeMillis();

            if (gameplayController != null && gameState == gameStates.GAME) {
                gameplayController.checkCollision();
                gameplayController.update();
            }
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

    /**
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {


        if (gameState == gameStates.GAME) {
            gameKeyPressedHandler.handleInput(e);
        } else if (gameState == gameStates.MENU) {
            menuKeyPressedHandler.handleInput(e);
        }
    }

    /**
     *
     * @param e
     */
    public void keyReleased(KeyEvent e) {
        if (gameState == gameStates.GAME) {
            gameKeyReleasedHandler.handleInput(e);
        } else if (gameState == gameStates.MENU) {
            menuKeyReleasedHandler.handleInput(e);
        }
    }

    /**
     *
     * @param e
     */
    public void keyTyped(KeyEvent e) {
    }


    /**
     *
      */
    public static void exit(){
        ResourceLoader.getInstance().cleanup();
        System.exit(0);
    }

    /**
     * Main method with new instance of MooseGame object that executes the Game method
     * @param args
     */
    public static void main(String[] args) {
        MooseGame mooseGame = new MooseGame();
        mooseGame.game();
    }

}