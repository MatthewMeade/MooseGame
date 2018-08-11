package game;

import actors.Actor;
import actors.KeyboardControllable;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuController implements KeyboardControllable {

    private MooseGame stage;


    private MooseGame canvas;
    private int menuSelection = 0;
    private String[] selectionSprites = new String[]{
            "MenuOptionsPlay.png",
            "MenuOptionsStore.png",
            "MenuOptionsSettings.png",
            "MenuOptionsExit.png"
    };

    private int[] menuLengths = new int[]{4, 4};

    // 0 main menu
    // 1 settings
    private int menuState = 0;

    public MenuController(MooseGame canvas) {
        this.stage = canvas;
    }


    public void paint(Graphics g) {

        // Draw background
        g.drawImage(ResourceLoader.getInstance().getSprite("road.png"), 0, 0, canvas);

        // Draw Logo
        g.drawImage(ResourceLoader.getInstance().getSprite("title.png"), (Stage.WIDTH / 2) - 358, 50, stage);


        if (menuState == 0) {

            // Draw menu options
            g.drawImage(ResourceLoader.getInstance().getSprite(selectionSprites[menuSelection % selectionSprites.length]), (Stage.WIDTH / 2) - 100, 250, stage);
        } else if (menuState == 1) {

            // Draw Settings
            g.drawImage(ResourceLoader.getInstance().getSprite(
                    PlayerInventory.isSettingsMusicOn() ? "OptionMusicOn.png" : "OptionMusicOff.png"
                    ),
                    (menuSelection == 0 ? 50 : 0) + (Stage.WIDTH / 2) - 100,
                    250,
                    stage);

            g.drawImage(ResourceLoader.getInstance().getSprite(
                    PlayerInventory.isSettingSoundsOn() ? "OptionSoundOn.png" : "OptionSoundOff.png"
                    ),
                    (menuSelection == 1 ? 50 : 0) + (Stage.WIDTH / 2) - 100,
                    300,
                    stage);

            g.drawImage(ResourceLoader.getInstance().getSprite("OptionReset.png"
                    ),
                    (menuSelection == 2 ? 50 : 0) + (Stage.WIDTH / 2) - 100,
                    350,
                    stage);

            g.drawImage(ResourceLoader.getInstance().getSprite("BackText.png"
                    ),
                    (menuSelection == 3 ? 50 : 0) + (Stage.WIDTH / 2) - 100,
                    400,
                    stage);

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
