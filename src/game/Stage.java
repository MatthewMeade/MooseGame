package game;

import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import actors.Actor;

/**
 *
 */
public class Stage extends Canvas implements ImageObserver {

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 750;
    public static final int HEIGHT = 750;
    public static final int DESIRED_FPS = 60;
    public static final int SLOW_MOTION_FPS = 30;

    protected boolean gameWon = false;
    protected boolean gameOver = false;
    public List<Actor> actors = new ArrayList<Actor>();

    /**
     *
     */
    public Stage() {
    }

    /**
     *
     */
    public void endGame() {
        gameOver = true;
    }

    /**
     * @return
     */
    public boolean isGameOver() {
        return gameOver;
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
     *
     */
    public void initWorld() {

    }

    /**
     *
     */
    public void game() {

    }
}
