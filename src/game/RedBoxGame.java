/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;


import javax.swing.JFrame;
import javax.swing.JPanel;

import actors.Actor;
import actors.RedBox;
/**
 *
 * @author eric.stock
 */
public class RedBoxGame extends Stage implements KeyListener {

    private RedBox redBox;	
    public long usedTime;
    public BufferStrategy strategy;	 //double buffering strategy
    private InputHandler keyPressedHandler;
    private InputHandler keyReleasedHandler;

    public RedBoxGame() {
        //init the UI
        
        
        setBounds(0,0,Stage.WIDTH,Stage.HEIGHT);
        setBackground(Color.BLACK);		

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(Stage.WIDTH,Stage.HEIGHT));
        panel.setLayout(null);

        panel.add(this);

        JFrame frame = new JFrame("Invaders");
        frame.add(panel);

        frame.setBounds(0,0,Stage.WIDTH,Stage.HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);

        //cleanup resources on exit
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                  ResourceLoader.getInstance().cleanup();
              System.exit(0);
            }
          }); 


        addKeyListener(this);

        //create a double buffer
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        requestFocus();
        initWorld();
        

    }
    
    public void initWorld() {
        actors = new ArrayList<Actor>();
        gameOver = false;
        gameWon = false;
        //add a player
        redBox = new RedBox(this);
        keyPressedHandler = new InputHandler(this, redBox);
        keyPressedHandler.action = InputHandler.Action.PRESS;
        keyReleasedHandler = new InputHandler(this, redBox);
        keyReleasedHandler.action = InputHandler.Action.RELEASE;
    }
    
    
    public void game() {
		//loopSound("music.wav");		
		usedTime= 0;				
		while(isVisible()) {
			long startTime = System.currentTimeMillis();
			
			/*backgroundY--;
			if (backgroundY < 0)
				backgroundY = backgroundTile.getHeight();

			if (super.gameOver) { 
				paintGameOver();
				break;
			} 
			else if (super.gameWon) {
				paintGameWon();
				break;
			}*/
			
			/*int random = (int)(Math.random()*1000);
			if (random == 700) {
				Actor ufo = new Ufo(this);
				ufo.setX(0);
				ufo.setY(20);
				ufo.setVx(1);
				actors.add(ufo);
			}*/
			
			updateWorld();
			paintWorld();
			
			usedTime = System.currentTimeMillis() - startTime;
			
			//calculate sleep time
			if (usedTime == 0) usedTime = 1;			
			int timeDiff = (int) ((1000/usedTime) - DESIRED_FPS);
			if (timeDiff > 0) {				
				try {
					Thread.sleep(timeDiff/100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}			
	}
    
    public void updateWorld() {		
        redBox.update();
    }
    
    public void paintWorld() {
			
        //get the graphics from the buffer
        Graphics g = strategy.getDrawGraphics();
        //init image to background
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        //load subimage from the background
        //g.drawImage( background,0,0,Stage.WIDTH,Stage.HEIGHT,0,backgroundY,Stage.WIDTH,backgroundY+Stage.HEIGHT,this);

        //paint the actors
        for (int i = 0; i < actors.size(); i++) {
                Actor actor = actors.get(i);
                actor.paint(g);
        }

        redBox.paint(g);
        //paintScore(g);		
        //paintFPS(g);
        //swap buffer
        strategy.show();		
    }
    
    public void keyPressed(KeyEvent e) {	
        keyPressedHandler.handleInput(e);
	}

	public void keyReleased(KeyEvent e) {
        keyReleasedHandler.handleInput(e);
	}

	public void keyTyped(KeyEvent e) {
	}
}
