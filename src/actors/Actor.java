package actors;

import game.ResourceLoader;
import game.Stage;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Actor class created to provide functionality for users of Moose game
 */
public class Actor {

	private static final int POINT_VALUE = 0;
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
	private boolean markedForRemoval = false;
	protected String[] sprites = null; 
	protected Stage stage = null;

	/**
	 * Instance of Actor class created to initialize variables
	 * @param canvas
	 */
	public Actor(Stage canvas) {
		this.stage = canvas;
		frame = 0;
		frameSpeed = 1;
		actorSpeed = 10;
		time = 0;
	}

	/**
	 *
	 */
	public void update() {
		updateSpriteAnimation();
	}

	/**
	 * Method will update sprite animation as gameplay progresses
	 */
	private void updateSpriteAnimation() {
		time++;
		if (time % frameSpeed == 0) {
			time = 0;
			frame = (frame + 1) % sprites.length;
		}
	}

	/**
	 * Retrieve a sound and play it
	 * @param name Holds location of sound that will be played.
	 */
	public void playSound(final String name) {
		new Thread(new Runnable() {
			public void run() {
				ResourceLoader.getInstance().getSound(name).play();
			}
		}).start();
	}

	/**
	 *
	 * @param g
	 */
	public void paint(Graphics g) {		
		g.drawImage(ResourceLoader.getInstance().getSprite(sprites[frame]), posX, posY, stage);
	}

	/**
	 * Set value for posX
	 * @param posX position of x
	 */
	public void setX(int posX) {
		this.posX = posX;
	}

	/**
	 * Set value for posY
	 * @param posY position of y
	 */
	public void setY(int posY) {
		this.posY = posY;
	}

	/**
	 * Get value for posX
	 * @return position of x
	 */
	public int getX() {
		return posX;
	}

	/**
	 * Get value for posY
	 * @return position of y
	 */
	public int getY() {
		return posY;
	}

	/**
	 * Set value for width
	 * @param width width of rectangle
	 */
	protected void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Get value for width
	 * @return Width of rectangle
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Set value for height
	 * @param height of rectangle
	 */
	protected void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Get value for height
	 * @return height of rectangle
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Set value for vx
	 * @param vx
	 */
	public void setVx(int vx) {
		this.vx = vx;
	}

	/**
	 *
	 * @return
	 */
	public int getVx() {
		return vx;
	}

	/**
	 * Set value for vy
	 * @param vy
	 */
	public void setVy(int vy) {
		this.vy = vy;
	}

	/**
	 * Get value for vy
	 * @return
	 */
	public int getVy() {
		return vy;
	}

	/**
	 * Get dimensions of rectangle
	 * @return Rectangle dimensions
	 */
	public Rectangle getBounds() {
		return new Rectangle(posX,posY,width, height);
	}

	/**
	 *
	 * @param a
	 */
	public void collision(Actor a) {		
	}

	/**
	 * Set value for markedForRemoval
	 * @param markedForRemoval
	 */
	public void setMarkedForRemoval(boolean markedForRemoval) {
		this.markedForRemoval = markedForRemoval;
	}

	/**
	 * Get value for markedForRemoval
	 * @return
	 */
	public boolean isMarkedForRemoval() {
		return markedForRemoval;
	}

	/**
	 * Get value for POINT_VALUE
	 * @return Point value
	 */
	public int getPointValue() {
		return Actor.POINT_VALUE;
	}
}
