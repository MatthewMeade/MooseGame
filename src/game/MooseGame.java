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
 * MooseGame class defines behaviors and renders graphics for different game states.
 * Class extends Stage class and implements KeyListener interface.
 */
public class MooseGame extends Stage implements KeyListener {

    private static final long serialVersionUID = 1L;

    private InputHandler gameKeyPressedHandler;
    private InputHandler gameKeyReleasedHandler;

    private InputHandler menuKeyPressedHandler;
    private InputHandler menuKeyReleasedHandler;

    private InputHandler gameOverKeyPressedHandler;
    private InputHandler gameOverKeyReleasedHandler;


    public long usedTime; //time taken per game step
    public BufferStrategy strategy; //double buffering strategy

    private MenuController menuController;
    private GameplayController gameplayController;
    private GameOverScreenController gameOverScreenController;

    /**
     * Initializes different game states
     */
    public enum gameStates {
        MENU,
        GAME,
        GAME_OVER
    }

    private gameStates gameState;

    /**
     * Instance of MooseGame class is created. User interface background
     * color and dimensions are initialized, with dimension values inherited
     * from Stage class and background color set to blue.
     */
    public MooseGame() {

        // Load high score, currency, and saved settings
        PlayerInventory.loadFromFile();

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
     * Method declares game state as GAME, creates new instance of GameplayController class,
     * calls PRESS and RELEASE key action from InputHandler class.
     */
    public void initGame() {
        gameState = gameStates.GAME;
        gameplayController = new GameplayController(this);

        gameKeyPressedHandler = new InputHandler(this, gameplayController);
        gameKeyPressedHandler.action = InputHandler.Action.PRESS;
        gameKeyReleasedHandler = new InputHandler(this, gameplayController);
        gameKeyReleasedHandler.action = InputHandler.Action.RELEASE;
    }

    /**
     * Method declares game state as MENU, creates new instance of MenuController class,
     * calls PRESS and RELEASE key actions from InputHandler class.
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
     * Method declares game state as GAME_OVER, creates new instance of GameOverScreenControlles class,
     * calls PRESS and RELEASE key actions from InputHandler class.
     *
     * @param finalScore Holds the value of the final score for one gameplay instance
     */
    public void initGameOverScreen(int finalScore) {
        gameState = gameStates.GAME_OVER;
        gameOverScreenController = new GameOverScreenController(this, finalScore);

        gameOverKeyPressedHandler = new InputHandler(this, gameOverScreenController);
        gameOverKeyPressedHandler.action = InputHandler.Action.PRESS;
        gameOverKeyReleasedHandler = new InputHandler(this, gameOverScreenController);
        gameOverKeyReleasedHandler.action = InputHandler.Action.RELEASE;
    }

    /**
     * Renders background graphics
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
        } else if (gameOverScreenController != null && gameState == gameState.GAME_OVER) {
            gameOverScreenController.paint(g);
        }

        //swap buffer
        strategy.show();
    }

    /**
     * Renders graphics for frames appearing in game window.
     *
     * @param g Graphics to be rendered
     */
    public void paintFPS(Graphics g) {
        g.setColor(Color.RED);
        if (usedTime > 0)
            g.drawString(String.valueOf(1000 / usedTime) + " fps", 0, Stage.HEIGHT - 50);
        else
            g.drawString("--- fps", 0, Stage.HEIGHT - 50);
    }

    /**
     * Constructor for paint
     *
     * @param g object to be painted
     */
    public void paint(Graphics g) {
    }

    /**
     * Renders graphics to Menu screen.
     *
     * @param g screen
     */
    public void paintMenu(Graphics g) {
        menuController.paint(g);
    }

    /**
     * Renders graphics to game over screen.
     *
     * @param g screen
     */
    public void paintGameOverScreen(Graphics g) {
        gameOverScreenController.paint(g);
    }


    /**
     * Plays sound from a location.
     *
     * @param name location of sound
     */
    public void loopSound(final String name) {
        new Thread(new Runnable() {
            public void run() {
                ResourceLoader.getInstance().getSound(name).loop();
            }
        }).start();
    }

    /**
     * Begins game loop
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
            int timeDiff = 1000 / (gameplayController != null &&
                    gameplayController.isSlowMotionActive() ? SLOW_MOTION_FPS : DESIRED_FPS) - (int) (usedTime);
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
     * Handles key press events dependent on which state the game is in.
     *
     * @param e key control pressed
     */
    public void keyPressed(KeyEvent e) {


        if (gameState == gameStates.GAME) {
            gameKeyPressedHandler.handleInput(e);
        } else if (gameState == gameStates.MENU) {
            menuKeyPressedHandler.handleInput(e);
        } else if (gameState == gameStates.GAME_OVER) {
            gameOverKeyPressedHandler.handleInput((e));
        }
    }

    /**
     * Handles key release events dependent on which state the game is in.
     *
     * @param e key control released
     */
    public void keyReleased(KeyEvent e) {
        if (gameState == gameStates.GAME) {
            gameKeyReleasedHandler.handleInput(e);
        } else if (gameState == gameStates.MENU) {
            menuKeyReleasedHandler.handleInput(e);
        } else if (gameState == gameStates.GAME_OVER) {
            gameOverKeyReleasedHandler.handleInput((e));
        }
    }

    /**
     * Default constructor for keyTyped events.
     *
     * @param e
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Exits game and cleans up resources upon closing.
     */
    public static void exit() {
        ResourceLoader.getInstance().cleanup();
        System.exit(0);
    }

    /**
     * Main method with new instance of MooseGame object that executes the Game method
     *
     * @param args
     */
    public static void main(String[] args) {
        MooseGame mooseGame = new MooseGame();
        mooseGame.game();
    }
}