package game;

import actors.Actor;
import actors.KeyboardControllable;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuController extends Actor implements KeyboardControllable {

    private int menuSelection = 0;
    private String[] selectionSprites = new String[]{
            "MenuOptionsPlay.png",
            "MenuOptionsStore.png",
            "MenuOptionsSettings.png",
            "MenuOptionsExit.png"
    };

    public MenuController(Stage canvas) {
        super(canvas);

        frame = 0;
        frameSpeed = 0;
        actorSpeed = 10;
        time = 0;
    }

    @Override
    public void paint(Graphics g) {

        // Draw Logo
        g.drawImage(ResourceLoader.getInstance().getSprite("title.png"), (Stage.WIDTH / 2) - 358, 50, stage);

        // Draw menu options
        g.drawImage(ResourceLoader.getInstance().getSprite(selectionSprites[menuSelection]), (Stage.WIDTH / 2) - 100, 250, stage);


    }

    @Override
    public void triggerKeyPress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            menuSelection++;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            menuSelection--;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            switch (menuSelection) {
                case 0: // Start Game
                    break;
                case 1: // Enter Store
                    break;
                case 2: // Enter Settings
                    break;
                case 3:
                    ResourceLoader.getInstance().cleanup();
                    System.exit(0);
                    break;
            }
        }

        menuSelection %= selectionSprites.length;
    }

    @Override
    public void triggerKeyRelease(KeyEvent e) {
//        System.out.println("Key Released!");
    }
}
