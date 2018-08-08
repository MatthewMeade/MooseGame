package actors;

import game.Stage;

import java.awt.event.KeyEvent;

public class PlayerCar extends Actor implements KeyboardControllable {

    private boolean left, right;
    private int score = 0;


    public PlayerCar(Stage stage) {
        super(stage);

        sprites = new String[]{"MoosePlayer.png"};
        frame = 0;
        frameSpeed = 35;
        actorSpeed = 10;
        width = 100;
        height = 100;
        posX = Stage.WIDTH / 2;
        posY = 4 * Stage.HEIGHT / 6;
    }


    public void update() {
        super.update();
        updateSpeed();
    }

    protected void updateSpeed() {
        vx = 0;
        if (left)
            vx = -actorSpeed;
        if (right)
            vx = actorSpeed;

        //don't allow scrolling off the edge of the screen
        if (posX> 0 && vx < 0) {
            posX += vx;
        } else if (posX + width < Stage.WIDTH - 15 && vx > 0) {
            posX += vx;
        }

    }

    public void triggerKeyRelease(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
//        updateSpeed();
    }

    public void triggerKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;

        }
//        updateSpeed();
    }

    public void collision(Actor a) {
        stage.endGame();
    }

    public void updateScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }




}
