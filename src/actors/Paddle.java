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
 * @author Eric
 */
public class Paddle extends Actor implements KeyboardControllable {

    public enum ePlayerNumber {
        PN_ONE,
        PN_TWO,
    };
    
    private ePlayerNumber playerNumber;
    
    public Paddle(Stage canvas, ePlayerNumber playerNo) {
        super(canvas);
        playerNumber = playerNo;
        sprites = new String[]{"paddle.png"};
        frame = 0;
        
        
        
        width = 10;
        height = 80;
        
        posY = Stage.HEIGHT / 2;
        
        if( playerNumber == ePlayerNumber.PN_ONE) {
            posX = 0;
        }
        else {
            posX = Stage.WIDTH - width -10;
        }
        
        
    }
    
    public void update() {
        super.update();
        posY += vy * 3;
        
        if( posY > Stage.HEIGHT - height ) {
            posY = Stage.HEIGHT - height;
        }
        else if( posY < 0 ) {
            posY = 0;
        }
    }
    
    
    @Override
    public void triggerKeyPress(KeyEvent e) {
        if( playerNumber == ePlayerNumber.PN_ONE) {
            if( e.getKeyCode() == KeyEvent.VK_S) {
                vy = 1;
            }
            else if( e.getKeyCode() == KeyEvent.VK_W) {
                vy = -1;
            }
        }
        else {
            if( e.getKeyCode() == KeyEvent.VK_DOWN) {
                vy = 1;
            }
            else if( e.getKeyCode() == KeyEvent.VK_UP) {
                vy = -1;
            }
        }
    }

    @Override
    public void triggerKeyRelease(KeyEvent e) {
        if( playerNumber == ePlayerNumber.PN_ONE) {
            if( e.getKeyCode() == KeyEvent.VK_S) {
                vy = 0;
            }
            else if( e.getKeyCode() == KeyEvent.VK_W) {
                vy = 0;
            }
        }
        else {
            if( e.getKeyCode() == KeyEvent.VK_DOWN) {
                vy = 0;
            }
            else if( e.getKeyCode() == KeyEvent.VK_UP) {
                vy = 0;
            }
        }
    }
    
}
