package game;

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
        g.fillRect(Stage.WIDTH / 6, 10 + Stage.WIDTH / 4, 2 * Stage.WIDTH / 3, Stage.WIDTH / 2);

        Font menuFont = new Font("Impact", Font.PLAIN, 40);
        FontMetrics metrics = g.getFontMetrics(menuFont);
        g.setFont(menuFont);

        if (menuState == 0) {

            String[] text = new String[]{"Play", "Store", "Settings", "How to Play", "Exit"};
            for (int i = 0; i < text.length; i++) {
                g.setColor(menuSelection == i ? Color.GREEN : Color.WHITE);
                String drawString = (menuSelection == i ? " - " : "") + text[i] + (menuSelection == i ? " - " : "");
                g.drawString(drawString, (Stage.WIDTH / 6) + ((2 * Stage.WIDTH / 3) - metrics.stringWidth(drawString)) / 2, 250 + (75 * i));
            }

            g.setColor(Color.white);


            // Settings menu
        } else if (menuState == 1) {

            String[] text = new String[]{
                    "Music: " + (PlayerInventory.isSettingMusicOn() ? "On" : "Off"),
                    "Sounds: " + (PlayerInventory.isSettingSoundsOn() ? "On" : "Off"),
                    "FPS: " + (PlayerInventory.isShowFPSOverlayOn() ? "On" : "Off"),
                    "Reset Save",
                    "Back"
            };

            for (int i = 0; i < text.length; i++) {
                g.setColor(menuSelection == i ? Color.GREEN : Color.WHITE);
                String drawString = (menuSelection == i ? " - " : "") + text[i] + (menuSelection == i ? " - " : "");
                g.drawString(drawString, (Stage.WIDTH / 6) + ((2 * Stage.WIDTH / 3) - metrics.stringWidth(drawString)) / 2, 250 + (75 * i));

            }

            // How to play menu
        } else if (menuState == 2) {

            g.setColor(Color.WHITE);
            Font textFont = new Font("Arial", Font.BOLD, 20);

            g.setFont(menuFont);
            String controlsTitle = "Controls";
            g.drawString(controlsTitle, (Stage.WIDTH - metrics.stringWidth(controlsTitle)) / 2, 240);

            g.setFont(textFont);
            metrics = g.getFontMetrics();
            String[] controlsStrings = new String[]{"Move Left:  [ Left Arrow ] or [ A ]", "Move Right: [ Right Arrow ] or [ D ]", "Power Ups: [ 1 ] [ 2 ] and [ 3 ]"};
            for (int i = 0; i < controlsStrings.length; i++) {
                g.drawString(controlsStrings[i], (Stage.WIDTH - metrics.stringWidth(controlsStrings[i])) / 2, 280 + 35 * i);
            }

            g.setFont(menuFont);
            metrics = g.getFontMetrics();
            String powerTitle = "Power Ups";
            g.drawString(powerTitle, (Stage.WIDTH - metrics.stringWidth(powerTitle)) / 2, 425);

            g.setFont(textFont);
            metrics = g.getFontMetrics();

            String[] powersStrings = new String[]{"Fog Lights: Disable fog for a short time", "Invincibility: Prevents damage from obstacles", "Slow Motion: Slows the movement of obstacles"};
            for (int i = 0; i < powersStrings.length; i++) {
                g.drawString(controlsStrings[i], (Stage.WIDTH - metrics.stringWidth(controlsStrings[i])) / 2, 475 + 35 * i);
            }

            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(Stage.WIDTH / 6, Stage.WIDTH / 2 + 10 + Stage.WIDTH / 4, 2 * Stage.WIDTH / 3, 100);

            g.setFont(menuFont);
            metrics = g.getFontMetrics();
            g.setColor(Color.GREEN);
            String backText = "Back to Main menu";
            g.drawString(backText, (Stage.WIDTH - metrics.stringWidth(backText)) / 2, 625);

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
                    PlayerInventory.setSettingMusicOn(!PlayerInventory.isSettingMusicOn());
                    if (PlayerInventory.isSettingMusicOn()) {
                        stage.loopSound("backgroundloop.wav");
                    } else {
                        stage.stopMusic();
                    }
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

    }
}
