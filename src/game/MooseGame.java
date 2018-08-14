package game;

import java.applet.AudioClip;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;
import java.util.Timer;
import java.util.TimerTask;


import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * MooseGame class defines behaviors and renders graphics for different game states.
 * Class extends MooseGame class and implements KeyListener interface.
 */
public class MooseGame extends Canvas implements ImageObserver, KeyListener {

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 750;
    public static final int HEIGHT = 750;
    public static final int DESIRED_FPS = 60;
    public static final int SLOW_MOTION_FPS = 30;

    private InputHandler gameKeyPressedHandler;
    private InputHandler gameKeyReleasedHandler;
    private InputHandler menuKeyPressedHandler;
    private InputHandler menuKeyReleasedHandler;
    private InputHandler storeKeyPressedHandler;
    private InputHandler storeKeyReleasedHandler;
    private InputHandler gameOverKeyPressedHandler;
    private InputHandler gameOverKeyReleasedHandler;


    public long usedTime; //time taken per game step
    public BufferStrategy strategy; //double buffering strategy
    public static AudioClip backgroundMusic;

    private MenuController menuController;
    private GameplayController gameplayController;
    private StoreController storeController;
    private GameOverScreenController gameOverScreenController;

    private boolean spriteBlinkStatus = false;
    private static final int SPRITE_BLINK_INTERVAL = 100;



    /**
     * Initializes different game states
     */
    public enum gameStates {
        MENU,
        STORE,
        GAME,
        GAME_OVER
    }

    private gameStates gameState;

    /**
     * Instance of MooseGame class is created. User interface background
     * color and dimensions are initialized, with dimension values inherited
     * from MooseGame class and background color set to blue.
     */
    public MooseGame() {

        // Load high score, currency, and saved settings
        PlayerInventory.loadFromFile();

        //init the UI
        setBounds(0, 0, MooseGame.WIDTH, MooseGame.HEIGHT);
        setBackground(Color.BLACK);
        loopSound("backgroundloop.wav");

        /*
         * New instances of JPanel and JFrame are created, dimensions
         * inherited from the MooseGame class.
         */
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(MooseGame.WIDTH, MooseGame.HEIGHT));
        panel.setLayout(null);

        panel.add(this);

        JFrame frame = new JFrame("Moose Game");
        frame.add(panel);

        frame.setBounds(0, 0, MooseGame.WIDTH, MooseGame.HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);

        /*
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

        spriteBlinkTimer();

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
     * Method declares game state as STORE, creates new instance of StoreController class,
     * calls PRESS and RELEASE key actions from InputHandler class.
     */
    public void initStore() {
        gameState = gameStates.STORE;
        storeController = new StoreController(this);

        storeKeyPressedHandler = new InputHandler(this, storeController);
        storeKeyPressedHandler.action = InputHandler.Action.PRESS;
        storeKeyReleasedHandler = new InputHandler(this, storeController);
        storeKeyReleasedHandler.action = InputHandler.Action.RELEASE;
    }

    /**
     * Method declares game state as GAME_OVER, creates new instance of GameOverScreenControlles class,
     * calls PRESS and RELEASE key actions from InputHandler class.
     *
     * @param finalScore Holds the value of the final score for one gameplay instance
     */
    public void initGameOverScreen(int finalScore, int coins) {
        gameState = gameStates.GAME_OVER;
        gameOverScreenController = new GameOverScreenController(this, finalScore, coins);

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

        g.setFont(new Font("Impact", Font.PLAIN, 40));


        g.fillRect(0, 0, getWidth(), getHeight());

        if (menuController != null && gameState == gameStates.MENU) {
            menuController.paint(g);
        } else if (gameplayController != null && gameState == gameStates.GAME) {
            gameplayController.paint(g);
        } else if (storeController != null && gameState == gameStates.STORE) {
            storeController.paint(g);
        } else if (gameOverScreenController != null && gameState == gameState.GAME_OVER) {
            gameOverScreenController.paint(g);
        }

        if (PlayerInventory.isShowFPSOverlayOn()) {
            paintFPS(g);
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
        g.setFont(new Font("Courier New", Font.PLAIN, 30));
        if (usedTime > 0)
            g.drawString(String.valueOf(1000 / usedTime) + " fps", 0, MooseGame.HEIGHT - 50);
        else
            g.drawString("--- fps", 0, MooseGame.HEIGHT - 50);
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
     * Loads a sound resource and plays it in a loop.
     *
     * @param name location of sound
     */
    public void loopSound(final String name) {
        if (PlayerInventory.isSettingMusicOn()) {
            new Thread(new Runnable() {
                public void run() {
                    backgroundMusic = ResourceLoader.getInstance().getSound(name);
                    backgroundMusic.loop();
                }
            }).start();
        }
    }

    /**
     * Loads a sound resource and plays it once.
     *
     * @param name location of sound
     */
    public void playSound(final String name) {

        if (PlayerInventory.isSettingSoundsOn()) {
            new Thread(new Runnable() {
                public void run() {
                    ResourceLoader.getInstance().getSound(name).play();
                }
            }).start();
        }
    }

    /**
     * Stops the background music.
     */
    public static void stopMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    /**
     * Gets the sprite blink status.
     * @return
     */
    public boolean getSpriteBlinkStatus() {
        return spriteBlinkStatus;
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


        try {
            if (gameState == gameStates.GAME) {
                gameKeyPressedHandler.handleInput(e);
            } else if (gameState == gameStates.MENU) {
                menuKeyPressedHandler.handleInput(e);
            } else if (gameState == gameStates.STORE) {
                storeKeyPressedHandler.handleInput(e);
            } else if (gameState == gameStates.GAME_OVER) {
                gameOverKeyPressedHandler.handleInput((e));
            }
        } catch (NullPointerException ex) {
            // Key pressed while transitioning
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
        PlayerInventory.saveToFile();
        ResourceLoader.getInstance().cleanup();
        System.exit(0);
    }

    /**
     *
     */
    public void spriteBlinkTimer() {
        Timer spriteBlinkTimer = new Timer();
        spriteBlinkTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        spriteBlinkStatus = !spriteBlinkStatus;
                        spriteBlinkTimer();
                    }
                }, SPRITE_BLINK_INTERVAL);
    }

    /**
     * @param img
     * @param infoflags
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public boolean imageUpdate(Image img, int infoflags, int x, int y,
                               int width, int height) {
        return false;
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