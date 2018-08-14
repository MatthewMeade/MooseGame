package actors;

import game.ResourceLoader;
import game.MooseGame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Class provides rendering of graphics, get and set methods, point values for gameplay.
 */
public class Actor {

    protected int vx;
    protected int vy;
    protected int posX;
    protected int posY;
    protected int height;
    protected int width;
    protected int frame;
    protected int frameSpeed;
    protected int actorSpeed;
    protected int time;
    protected String[] sprites = null;
    protected MooseGame mooseGame = null;

    public boolean isActive = false;

    /**
     * Constructs a new Actor
     *
     * @param canvas game window
     */
    public Actor(MooseGame canvas) {
        this.mooseGame = canvas;
        frame = 0;
        frameSpeed = 1;
        actorSpeed = 10;
        time = 0;
    }

    /**
     * Sets the speed of an Actor.
     *
     * @param actorSpeed int speed of actor
     */
    public void setActorSpeed(int actorSpeed) {
        this.actorSpeed = actorSpeed;
    }

    /**
     * Updates positioning of actor obstacle within game window.
     */
    public void update() {
        if (isActive) {
            posX += vx;
            posY += vy;
        }
    }

    /**
     * Updates actor animation to next sprite
     */
    private void updateSpriteAnimation() {
        time++;
        if (time % frameSpeed == 0) {
            time = 0;
            frame = (frame + 1) % sprites.length;
        }
    }

    /**
     * Loads and renders sprites.
     *
     * @param g Graphics object
     */
    public void paint(Graphics g) {
        g.drawImage(ResourceLoader.getInstance().getSprite(sprites[frame]), posX, posY, mooseGame);
    }

    /**
     * Sets value for posX
     *
     * @param posX position of x
     */
    public void setX(int posX) {
        this.posX = posX;
    }

    /**
     * Sets value for posY
     *
     * @param posY position of y
     */
    public void setY(int posY) {
        this.posY = posY;
    }

    /**
     * Gets value for posX
     *
     * @return position of x
     */
    public int getX() {
        return posX;
    }

    /**
     * Gets value for posY
     *
     * @return position of y
     */
    public int getY() {
        return posY;
    }

    /**
     * Sets value for width
     *
     * @param width width of rectangle
     */
    protected void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets width value
     *
     * @return Width of rectangle
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets height value
     *
     * @param height of rectangle
     */
    protected void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets height value
     *
     * @return height of rectangle
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets value for vx
     *
     * @param vx velocity of x
     */
    public void setVx(int vx) {
        this.vx = vx;
    }

    /**
     * Get value for vx
     *
     * @return velocity of x
     */
    public int getVx() {
        return vx;
    }

    /**
     * Sets value for vy
     *
     * @param vy velocity of y
     */
    public void setVy(int vy) {
        this.vy = vy;
    }

    /**
     * Gets value for vy
     *
     * @return velocity of y
     */
    public int getVy() {
        return vy;
    }

    /**
     * Get dimensions of rectangle
     *
     * @return Rectangle dimensions
     */
    public Rectangle getBounds() {
        return new Rectangle(posX, posY, width, height);
    }

    /**
     * Removes actor from game window.
     */
    public void despawn() {
        isActive = false;
        posX = -100;
        posY = -100;
    }
}


