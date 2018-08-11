package actors;

import game.Stage;

import java.awt.event.KeyEvent;

public class Player extends Actor implements KeyboardControllable {

    private boolean left, right;
    private int score = 0;


    public Player(Stage stage) {
        super(stage);

        sprites = new String[]{"CarFullHealth.png"};
        frame = 0;
        frameSpeed = 35;
        actorSpeed = 10;
        width = 70;
        height = 140;
        posX = Stage.WIDTH / 2;
        posY = 8 * Stage.HEIGHT / 10;
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
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                right = false;
                break;
        }
//        updateSpeed();
    }

    public void triggerKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
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
