package game;

import actors.KeyboardControllable;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StoreController implements KeyboardControllable {

    private MooseGame stage;
    private int menuSelection = 0;
    private int[] menuLengths = new int[]{4};
    private int menuState = 0;


    public StoreController(MooseGame stage) {
        this.stage = stage;
    }

    public void paint(Graphics g) {

        // Draw background
        g.drawImage(ResourceLoader.getInstance().getSprite("road.png"), 0, 0, stage);

        // Draw store title
        g.setFont(new Font("Impact", Font.PLAIN, 40));
        g.setColor(Color.WHITE);
        g.drawString("Store", stage.WIDTH / 2, 100);

        String[] text = new String[]{
                "Powerups", "Vehicles", "Buy Coin Packs", "Back to Main Menu"};

        for (int i = 0; i < text.length; i++) {
            g.setColor(menuSelection == i ? Color.YELLOW : Color.WHITE);
            g.drawString((menuSelection == i ? " - " : "") + text[i], (Stage.WIDTH / 5), 250 + (75 * i));
        }

    }

    @Override
    public void triggerKeyPress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            menuSelection++;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            menuSelection--;
            if (menuSelection < 0) {
                menuSelection = 3;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            handleEnterPress();
        }

        menuSelection %= 4;

    }

    /**
     * Handles key release events for game over screen.
     *
     * @param e key release event
     */
    @Override
    public void triggerKeyRelease(KeyEvent e) {

    }

    private void handleEnterPress() {

        switch (menuSelection) {
            case 0: // Powerups
                break;
            case 1: // Vehicles
                break;
            case 2: // Buy coins
                break;
            case 3: // Back to main menu
                stage.initMenu();
                break;
        }

    }

}
