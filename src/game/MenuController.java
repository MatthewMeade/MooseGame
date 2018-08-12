package game;

import actors.Actor;
import actors.KeyboardControllable;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 *
 */
public class MenuController implements KeyboardControllable {

    private MooseGame stage;
    private int menuSelection = 0;
    private String[] selectionSprites = new String[]{
            "MenuOptionsPlay.png",
            "MenuOptionsStore.png",
            "MenuOptionsSettings.png",
            "MenuOptionsExit.png"
    };

    private int[] menuLengths = new int[]{4, 5};

    // 0 main menu
    // 1 settings
    private int menuState = 0;

    public MenuController(MooseGame canvas) {
        this.stage = canvas;
    }


    public void paint(Graphics g) {


        // Draw background
        g.drawImage(ResourceLoader.getInstance().getSprite("road.png"), 0, 0, stage);

        // Draw Logo
        g.drawImage(ResourceLoader.getInstance().getSprite("title.png"), (Stage.WIDTH / 2) - 358, 50, stage);


        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(Stage.WIDTH / 6, Stage.WIDTH / 4, 2 * Stage.WIDTH / 3, Stage.WIDTH / 2);

        if (menuState == 0) {

            String[] text = new String[]{"Play", "Store", "Settings", "Exit"};
            for (int i = 0; i < text.length; i++) {
                g.setColor(menuSelection == i ? Color.YELLOW : Color.WHITE);
                g.drawString((menuSelection == i ? " - " : "") + text[i], (Stage.WIDTH / 5), 250 + (75 * i));
            }

            g.setColor(Color.white);
            g.drawString("Controls:", 425, 250);


        } else if (menuState == 1) {

            String[] text = new String[]{
                    "Music: " + (PlayerInventory.isSettingsMusicOn() ? "On" : "Off"),
                    "Sounds: " + (PlayerInventory.isSettingSoundsOn() ? "On" : "Off"),
                    "FPS: " + "On",
                    "Reset Save",
                    "Back"
            };

            for (int i = 0; i < text.length; i++) {
                g.setColor(menuSelection == i ? Color.YELLOW : Color.WHITE);
                g.drawString((menuSelection == i ? " - " : "") + text[i], (Stage.WIDTH / 5), 250 + (75 * i));
            }

        }

    }

    private void handleEnterPress() {

        if (menuState == 0) {
            switch (menuSelection) {
                case 0: // Play
                    stage.initGame();
                    break;
                case 1: // Store
                    break;
                case 2: // Enter Settings
                    menuState = 1;
                    menuSelection = 0;
                    break;
                case 3:
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
                    break;
                case 3:
                    break;
                case 4:
                    menuState = 0;
                    menuSelection = 0;
                    break;
            }
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

        menuSelection %= menuLengths[menuState];
    }

    @Override
    public void triggerKeyRelease(KeyEvent e) {
//        System.out.println("Key Released!");
    }
}
