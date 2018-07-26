/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actors;
import game.Stage;
import java.awt.event.KeyEvent;
/**
 *
 * @author eric.stock
 */
public class RedBox extends Actor implements KeyboardControllable {
    private boolean up,down,left,right;
    
    
    public RedBox(Stage stage) {
        super(stage);
        sprites = new String[]{"redBox.png"};
        frame = 0;
        frameSpeed = 35;
        actorSpeed = 10;
        width = 256;
        height = 256;
        posX = Stage.WIDTH/2 - 128;
        posY = Stage.HEIGHT/2 - 128;
    }
    
    public void update() {
        super.update();
        updateSpeed();
    }
	
    protected void updateSpeed() {
        vx = 0;
        vy = 0;
        if (down)
                vy = actorSpeed;
        if (up)
                vy = -actorSpeed;
        if (left)
                vx = -actorSpeed;
        if (right)
                vx = actorSpeed;

        //don't allow scrolling off the edge of the screen		
        if (posX - width/2 > 0 && vx < 0)
                posX += vx;
        else if (posX + width  + (width/2)< Stage.WIDTH && vx > 0)
                posX += vx;
        
        if (posY - height/2 > 0 && vy < 0)
                posY += vy;
        else if (posY + height + (height/2) < Stage.HEIGHT && vy > 0)
                posY += vy;
    }
    
    public void triggerKeyRelease(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_DOWN:
                down = false;
                break;
        case KeyEvent.VK_UP:
                up = false;
                break;
        case KeyEvent.VK_LEFT:
                left = false;
                break;
        case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
    }

    public void triggerKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
        ///*
        case KeyEvent.VK_UP:
                up = true;
                break;
        //*/
        case KeyEvent.VK_LEFT:
                left = true;
                break;
        case KeyEvent.VK_RIGHT:
                right = true;
                break;
        ///*
        case KeyEvent.VK_DOWN:
                down = true;
                break;
        }
    }
}
