package game;

import actors.KeyboardControllable;

import java.awt.*;
import java.awt.event.KeyEvent;

import static game.PlayerInventory.Vehicles.*;

/**
 * Handles creation of store screen,
 */
public class StoreController implements KeyboardControllable {

    private MooseGame mooseGame;
    private int menuSelection = 0;
    private int[] menuLengths = new int[]{4, 4, 4, 5};
    private int menuState = 0;

    public static final int FOG_LIGHTS_COST = 50;
    public static final int INVINCIBILITY_COST = 60;
    public static final int SLOW_MOTION_COST = 70;

    private static final int TRUCK_COST = 250;
    private static final int ATV_COST = 300;

    private static final int SMALL_COIN_PACK_VALUE = 100;
    private static final double SMALL_COIN_PACK_COST = 0.99;
    private static final int MEDIUM_COIN_PACK_VALUE = 500;
    private static final double MEDIUM_COIN_PACK_COST = 2.99;
    private static final int LARGE_COIN_PACK_VALUE = 1000;
    private static final double LARGE_COIN_PACK_COST = 4.99;
    private static final int SUPER_COIN_PACK_VALUE = 5000;
    private static final double SUPER_COIN_PACK_COST = 9.99;

    /**
     * Constructor for StoreController.
     *
     * @param mooseGame game window
     */
    public StoreController(MooseGame mooseGame) {
        this.mooseGame = mooseGame;
    }

    /**
     * Renders graphics for Store screen.
     *
     * @param g instance of Store screen
     */
    public void paint(Graphics g) {

        // Draw background
        g.drawImage(ResourceLoader.getInstance().getSprite("road.png"), 0, 0, mooseGame);

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(MooseGame.WIDTH / 6, 50, 2 * MooseGame.WIDTH / 3, 100);

        // Draw store title
        Font storeTitle = new Font("Impact", Font.PLAIN, 75);
        FontMetrics metrics = g.getFontMetrics(storeTitle);
        g.setFont(storeTitle);
        g.setColor(Color.WHITE);
        g.drawString("Store", (MooseGame.WIDTH - metrics.stringWidth("Store")) / 2, 125);


        Font coinFont = new Font("Impact", Font.PLAIN, 30);
        metrics = g.getFontMetrics(coinFont);
        g.setFont(coinFont);
        g.setColor(new Color(255, 215, 0));

        String coinText = "Coins: " + PlayerInventory.getCurrency();
        g.drawString(coinText, 5 * MooseGame.WIDTH / 6 - metrics.stringWidth(coinText) - 10, 135);


        Font menuFont = new Font("Impact", Font.PLAIN, 40);
        metrics = g.getFontMetrics(menuFont);
        g.setFont(menuFont);
        g.setColor(Color.white);

        if (menuState == 0) { // Main Store

            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(MooseGame.WIDTH / 6, MooseGame.WIDTH / 4, 2 * MooseGame.WIDTH / 3, MooseGame.WIDTH / 2);

            String[] text = new String[]{
                    "Powerups", "Vehicles", "Buy Coin Packs", "Back to Main Menu"};

            for (int i = 0; i < text.length; i++) {
                g.setColor(menuSelection == i ? Color.GREEN : Color.WHITE);
                String drawString = (menuSelection == i ? " - " : "") + text[i] + (menuSelection == i ? " - " : "");
                g.drawString(drawString, (MooseGame.WIDTH / 6) + ((2 * MooseGame.WIDTH / 3) - metrics.stringWidth(drawString)) / 2, 250 + (75 * i));

            }

        } else if (menuState == 1) { // Power Ups

            g.setColor(new Color(0, 0, 0, 150));

            g.fillRect(MooseGame.WIDTH / 6, 160, 2 * MooseGame.WIDTH / 3, 375);

            g.setColor(Color.white);

            String[] sprites = new String[]{"foglights.png", "invincible.png", "slowmotion.png"};
            String[] names = new String[]{"Fog Lights", "Invincibility", "Slow Motion"};
            Integer[] options = new Integer[]{FOG_LIGHTS_COST, INVINCIBILITY_COST, SLOW_MOTION_COST};

            for (int i = 0; i < sprites.length; i++) {
                g.drawImage(ResourceLoader.getInstance().getSprite(sprites[i]), MooseGame.WIDTH / 6 + 10, 175 + i * 75, mooseGame);
                g.drawString("[" + options[i] + "]    " + names[i], (MooseGame.WIDTH / 4) + 20, 215 + i * 75);
                g.setColor(menuSelection == i ? Color.GREEN : Color.WHITE);
                g.drawString("Buy", 550, 215 + (75 * i));
                g.setColor(Color.white);
            }

            Font warningFont = new Font("Calivri", Font.BOLD, 16);
            metrics = g.getFontMetrics(warningFont);
            g.setFont(warningFont);
            g.setColor(Color.red);

            String warningText = "Note: Powerups are lost on game over";
            g.drawString(warningText, (MooseGame.WIDTH - metrics.stringWidth(warningText)) / 2, 400);

            metrics = g.getFontMetrics(menuFont);
            g.setFont(menuFont);
            g.setColor(menuSelection == 3 ? Color.GREEN : Color.WHITE);

            String backText = "Back to store";
            g.drawString(backText, (MooseGame.WIDTH - metrics.stringWidth(backText)) / 2, 500);

        } else if (menuState == 2) { // Vehicles

            String[] sprites = new String[]{"player_bluecar.png", "player_truck.png", "atv.png"};
            Integer[] prices = new Integer[]{0, TRUCK_COST, ATV_COST};


            PlayerInventory.Vehicles equipped = PlayerInventory.getEquippedVehicle();
            Boolean[] equippedStates = new Boolean[]{equipped == CAR, equipped == TRUCK, equipped == ATV};

            Boolean[] ownedStates = new Boolean[]{true, PlayerInventory.isTruckOwned(), PlayerInventory.isAtvOwned()};


            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(MooseGame.WIDTH / 6, 175, 2 * MooseGame.WIDTH / 3, 500);

            for (int i = 0; i < sprites.length; i++) {
                g.drawImage(ResourceLoader.getInstance().getSprite(sprites[i]), (MooseGame.WIDTH / 3), 180 + 150 * i, mooseGame);
                g.setColor(menuSelection == i ? Color.GREEN : Color.white);
                String drawString = "";

                if (!ownedStates[i]) {
                    drawString = "Buy (" + prices[i] + ")";
                } else if (equippedStates[i]) {
                    drawString = "Equipped";
                } else {
                    drawString = "Equip";
                }

                g.drawString(drawString, 400, 255 + 150 * i);
            }


            g.setColor(menuSelection == 3 ? Color.GREEN : Color.WHITE);


            String backString = "Back to Store";
            g.drawString(backString, (MooseGame.WIDTH - metrics.stringWidth(backString)) / 2, 650);

        } else if (menuState == 3) { // Buy coins

            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(MooseGame.WIDTH / 6, 200, 2 * MooseGame.WIDTH / 3, 375);

            String[] coinPacksText = new String[]{
                    SMALL_COIN_PACK_VALUE + " for $" + SMALL_COIN_PACK_COST,
                    MEDIUM_COIN_PACK_VALUE + " for $" + MEDIUM_COIN_PACK_COST,
                    LARGE_COIN_PACK_VALUE + " for $" + LARGE_COIN_PACK_COST,
                    SUPER_COIN_PACK_VALUE + " for $" + SUPER_COIN_PACK_COST};

            for (int i = 0; i < coinPacksText.length; i++) {
                g.setColor(menuSelection == i ? Color.GREEN : Color.WHITE);
                g.drawString(coinPacksText[i], (MooseGame.WIDTH / 5), 250 + (75 * i));
            }

            g.setColor(menuSelection == 4 ? Color.GREEN : Color.WHITE);

            String backText = "Back to Store";
            g.drawString(backText, (MooseGame.WIDTH - metrics.stringWidth(backText)) / 2, 550);
        }

    }

    /**
     * Handles key press event
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
     * Handles key release events
     *
     * @param e key release event
     */
    @Override
    public void triggerKeyRelease(KeyEvent e) {

    }

    /**
     * Perform correct action based on current menu state and item selection
     */
    private void handleEnterPress() {

        if (menuState == 0) { // Main tore Menu
            if (menuSelection == 3) {
                mooseGame.initMenu();
            } else {
                menuState = menuSelection + 1;
            }
        } else if (menuState == 1) { // Power Ups Menu
            switch (menuSelection) {
                case 0: // Fog Lights
                    if (PlayerInventory.spendCurrency(FOG_LIGHTS_COST)) {
                        PlayerInventory.incrementFogLights();
                    }
                    break;
                case 1: // Invincibility
                    if (PlayerInventory.spendCurrency(INVINCIBILITY_COST)) {
                        PlayerInventory.incrementInvincibility();
                    }
                    break;
                case 2: // Slow Motion
                    if (PlayerInventory.spendCurrency(SLOW_MOTION_COST)) {
                        PlayerInventory.incrementSlowMotion();
                    }
                    break;
                case 3: // Back to main menu
                    menuState = 0;
                    menuSelection = 0;
                    break;
            }
        } else if (menuState == 2) { // Vehicles menu
            switch (menuSelection) {
                case 0: // Car
                    PlayerInventory.setEquippedVehicle(CAR);
                    break;
                case 1: // Truck
                    if (!PlayerInventory.isTruckOwned()) {
                        if (PlayerInventory.spendCurrency(TRUCK_COST)) {
                            PlayerInventory.buyTruck();
                        }
                    } else {
                        PlayerInventory.setEquippedVehicle(TRUCK);
                    }
                    break;
                case 2: // ATV
                    if (!PlayerInventory.isAtvOwned()) {
                        if (PlayerInventory.spendCurrency(ATV_COST)) {
                            PlayerInventory.buyATV();
                        }
                    } else {
                        PlayerInventory.setEquippedVehicle(ATV);
                    }
                    break;
                case 3: // Back to store
                    menuState = 0;
                    menuSelection = 0;
                    break;
            }
        } else if (menuState == 3) { // Coin Packs
            switch (menuSelection) {
                case 0: // Small
                    PlayerInventory.addCurrency(SMALL_COIN_PACK_VALUE);
                    break;
                case 1: // Medium
                    PlayerInventory.addCurrency(MEDIUM_COIN_PACK_VALUE);
                    break;
                case 2: // Large
                    PlayerInventory.addCurrency(LARGE_COIN_PACK_VALUE);
                    break;
                case 3: // Super
                    PlayerInventory.addCurrency(SUPER_COIN_PACK_VALUE);
                    break;
                case 4: // Back to store
                    menuState = 0;
                    menuSelection = 0;
                    break;
            }
        }

    }

}
