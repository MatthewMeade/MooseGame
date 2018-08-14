package game;

import actors.KeyboardControllable;

import java.awt.*;
import java.awt.event.KeyEvent;

import static game.PlayerInventory.saveToFile;

/**
 * Controls the main and settings menus.
 */
public class MenuController implements KeyboardControllable {

    private MooseGame mooseGame;
    private int menuSelection = 0;

    private int[] menuLengths = new int[]{5, 5, 1};

    // 0 main menu
    // 1 settings
    private int menuState = 0;

    /**
     * Constructs a MenuController.
     *
     * @param canvas game window
     */
    public MenuController(MooseGame canvas) {
        this.mooseGame = canvas;
    }

    /**
     * Renders graphics for Menu screen.
     *
     * @param g instance of Menu screen
     */
    public void paint(Graphics g) {

        // Draw background
        g.drawImage(ResourceLoader.getInstance().getSprite("road.png"), 0, 0, mooseGame);

        // Draw Logo
        g.drawImage(ResourceLoader.getInstance().getSprite("title.png"), (MooseGame.WIDTH / 2) - 358, 50, mooseGame);


        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(MooseGame.WIDTH / 6,  MooseGame.WIDTH / 4 + 10, 2 * MooseGame.WIDTH / 3, MooseGame.WIDTH / 2);


        Font menuFont = new Font("Impact", Font.PLAIN, 40);
        FontMetrics metrics = g.getFontMetrics(menuFont);
        g.setFont(menuFont);

        if (menuState == 0) {

            String[] text = new String[]{"Play", "Store", "Settings", "How to Play", "Exit"};
            for (int i = 0; i < text.length; i++) {
                g.setColor(menuSelection == i ? Color.GREEN : Color.WHITE);
                String drawString = (menuSelection == i ? " - " : "") + text[i] + (menuSelection == i ? " - " : "");
                g.drawString(drawString, (MooseGame.WIDTH / 6) + ((2 * MooseGame.WIDTH / 3) - metrics.stringWidth(drawString)) / 2, 250 + (75 * i));
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
                g.drawString(drawString, (MooseGame.WIDTH / 6) + ((2 * MooseGame.WIDTH / 3) - metrics.stringWidth(drawString)) / 2, 250 + (75 * i));

            }

            // How to play menu
        } else if (menuState == 2) {

            g.setColor(Color.WHITE);
            Font textFont = new Font("Arial", Font.BOLD, 20);

            g.setFont(menuFont);
            String controlsTitle = "Controls";
            g.drawString(controlsTitle, (MooseGame.WIDTH - metrics.stringWidth(controlsTitle)) / 2, 240);

            g.setFont(textFont);
            metrics = g.getFontMetrics();
            String[] controlsStrings = new String[]{"Move Left:  [ Left Arrow ] or [ A ]", "Move Right: [ Right Arrow ] or [ D ]", "Power Ups: [ 1 ] [ 2 ] and [ 3 ]"};
            for (int i = 0; i < controlsStrings.length; i++) {
                g.drawString(controlsStrings[i], (MooseGame.WIDTH - metrics.stringWidth(controlsStrings[i])) / 2, 280 + 35 * i);
            }

            g.setFont(menuFont);
            metrics = g.getFontMetrics();
            String powerTitle = "Power Ups";
            g.drawString(powerTitle, (MooseGame.WIDTH - metrics.stringWidth(powerTitle)) / 2, 425);

            g.setFont(textFont);
            metrics = g.getFontMetrics();

            String[] powersStrings = new String[]{"Fog Lights: Disables fog for a short time", "Invincibility: Prevents damage from obstacles", "Slow Motion: Slows the movement of obstacles"};
            for (int i = 0; i < powersStrings.length; i++) {
                g.drawString(powersStrings[i], (MooseGame.WIDTH - metrics.stringWidth(powersStrings[i])) / 2, 475 + 35 * i);
            }

            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(MooseGame.WIDTH / 6, MooseGame.WIDTH / 2 + 10 + MooseGame.WIDTH / 4, 2 * MooseGame.WIDTH / 3, 100);

            g.setFont(menuFont);
            metrics = g.getFontMetrics();
            g.setColor(Color.GREEN);

            String backText = "Back to Main Menu";
            g.drawString(backText, (MooseGame.WIDTH - metrics.stringWidth(backText)) / 2, 625);
        }

    }

    /**
     * Handles enter key press event on the main and settings menus.
     */
    private void handleEnterPress() {

        if (menuState == 0) {
            switch (menuSelection) {
                case 0: // Play
                    mooseGame.initGame();
                    break;
                case 1: // Store
                    mooseGame.initStore();
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
                case 0: // Background Music
                    PlayerInventory.setSettingMusicOn(!PlayerInventory.isSettingMusicOn());
                    if (PlayerInventory.isSettingMusicOn()) {
                        mooseGame.loopSound("backgroundloop.wav");
                    } else {
                        mooseGame.stopMusic();
                    }
                    break;
                case 1: // Sound Effects
                    PlayerInventory.setSettingSoundsOn(!PlayerInventory.isSettingSoundsOn());
                    break;
                case 2: // FPS
                    PlayerInventory.setShowFPSOverlay(!PlayerInventory.isShowFPSOverlayOn());
                    break;
                case 3: // Reset Save
                    MooseGame.stopMusic();
                    PlayerInventory.clearSave();
                    mooseGame.loopSound("backgroundloop.wav");
                    break;
                case 4: // Back
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
     * Handles key press events.
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
     * Handles key release events.
     *
     * @param e key release event
     */
    @Override
    public void triggerKeyRelease(KeyEvent e) {

    }
}
