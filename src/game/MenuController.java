package game;

import actors.Actor;
import actors.KeyboardControllable;

import java.awt.*;
import java.awt.event.KeyEvent;

import static game.PlayerInventory.saveToFile;

/**
 * Handles creation of main menu.
 */
public class MenuController implements KeyboardControllable {

    private MooseGame stage;
    private int menuSelection = 0;

    private int[] menuLengths = new int[]{5, 5, 1};

    // 0 main menu
    // 1 settings
    private int menuState = 0;

    /**
     * Constructor for MenuController.
     *
     * @param canvas game window
     */
    public MenuController(MooseGame canvas) {
        this.stage = canvas;
    }

    /**
     * Renders graphics for Menu screen.
     *
     * @param g instance of Menu screen
     */
    public void paint(Graphics g) {


        // Draw background
        g.drawImage(ResourceLoader.getInstance().getSprite("road.png"), 0, 0, stage);

        // Draw Logo
        g.drawImage(ResourceLoader.getInstance().getSprite("title.png"), (Stage.WIDTH / 2) - 358, 50, stage);


        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(Stage.WIDTH / 6, Stage.WIDTH / 4, 2 * Stage.WIDTH / 3, Stage.WIDTH / 2);

        if (menuState == 0) {

            String[] text = new String[]{"Play", "Store", "Settings", "How to Play", "Exit"};
            for (int i = 0; i < text.length; i++) {
                g.setColor(menuSelection == i ? Color.GREEN : Color.WHITE);
                g.drawString((menuSelection == i ? " - " : "") + text[i], (Stage.WIDTH / 5), 250 + (75 * i));
            }

            g.setColor(Color.white);
            g.drawString("Controls:", 425, 250);


            // Settings menu
        } else if (menuState == 1) {

            String[] text = new String[]{
                    "Music: " + (PlayerInventory.isSettingsMusicOn() ? "On" : "Off"),
                    "Sounds: " + (PlayerInventory.isSettingSoundsOn() ? "On" : "Off"),
                    "FPS: " + (PlayerInventory.isShowFPSOverlayOn() ? "On" : "Off"),
                    "Reset Save",
                    "Back"
            };

            for (int i = 0; i < text.length; i++) {
                g.setColor(menuSelection == i ? Color.GREEN : Color.WHITE);
                g.drawString((menuSelection == i ? " - " : "") + text[i], (Stage.WIDTH / 5), 250 + (75 * i));
            }

            // How to play menu
        } else if (menuState == 2) {

            g.setColor(Color.GREEN);
            g.drawString("Back to Main Menu", (Stage.WIDTH / 2), 700);

        }

    }

    /**
     * Handles enter key press event on Main Menu.
     */
    private void handleEnterPress() {

        if (menuState == 0) {
            switch (menuSelection) {
                case 0: // Play
                    stage.initGame();
                    break;
                case 1: // Store
                    stage.initStore();
                    break;
                case 2: // Enter Settings
                    menuState = 1;
                    menuSelection = 0;
                    break;
                case 3: // How to play
                    menuState = 2;
                    menuSelection = 0;
                    break;
                case 4:
                    saveToFile();
                    MooseGame.exit();
                    break;
            }
        } else if (menuState == 1) {
            switch (menuSelection) {
                case 0:
                    PlayerInventory.setSettingsMusicOn(!PlayerInventory.isSettingsMusicOn());
                    break;
                case 1:
                    PlayerInventory.setSettingSoundsOn(!PlayerInventory.isSettingSoundsOn());
                    break;
                case 2:
                    PlayerInventory.setShowFPSOverlay(!PlayerInventory.isShowFPSOverlayOn());
                    break;
                case 3:
                    PlayerInventory.clearSave();
                    break;
                case 4:
                    saveToFile();
                    menuState = 0;
                    menuSelection = 0;
                    break;
            }
        } else if (menuState == 2) {
            switch (menuSelection) {
                case 0: // Return to main menu
                    menuState = 0;
            }
        }

    }

    /**
     * Handles key press event for Main Menu.
     *
     * @param e key press event
     */
    @Override
    public void triggerKeyPress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            menuSelection++;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            menuSelection--;
            if (menuSelection < 0) {
                menuSelection = menuLengths[menuState] - 1;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            handleEnterPress();
        }

        menuSelection %= menuLengths[menuState];
    }

    /**
     * Handles key release event for Main Menu.
     *
     * @param e key release event
     */
    @Override
    public void triggerKeyRelease(KeyEvent e) {
//        System.out.println("Key Released!");
    }
}
