package game;

import actors.KeyboardControllable;

import java.awt.*;
import java.awt.event.KeyEvent;

import static game.PlayerInventory.Vehicles.*;

public class StoreController implements KeyboardControllable {

    private MooseGame stage;
    private int menuSelection = 0;
    private int[] menuLengths = new int[]{4, 4, 4};
    private int menuState = 0;

    public static final int FOG_LIGHTS_COST = 4;
    public static final int INVINCIBILITY_COST = 3;
    public static final int SLOW_MOTION_COST = 5;

    private static final int TRUCK_COST = 20;
    private static final int ATV_COST = 30;


    public StoreController(MooseGame stage) {
        this.stage = stage;
    }

    public void paint(Graphics g) {

        // Draw background
        g.drawImage(ResourceLoader.getInstance().getSprite("road.png"), 0, 0, stage);

        // Draw store title
        g.setFont(new Font("Impact", Font.PLAIN, 40));
        g.setColor(Color.WHITE);
        g.drawString("Store", stage.WIDTH / 2, 75);

        // 0 - main store, 1 - powerups, 2 - vehicles
        // 3 - buy coin packs

        if (menuState == 0) {

            String[] text = new String[]{
                    "Powerups", "Vehicles", "Buy Coin Packs", "Back to Main Menu"};

            for (int i = 0; i < text.length; i++) {
                g.setColor(menuSelection == i ? Color.GREEN : Color.WHITE);
                g.drawString((menuSelection == i ? " - " : "") + text[i], (Stage.WIDTH / 5), 250 + (75 * i));
            }

        } else if (menuState == 1) {

            g.setFont(new Font("Impact", Font.PLAIN, 25));
            g.drawString("Coins: " + PlayerInventory.getCurrency(), stage.WIDTH / 2, 150);

            // Draw powerups
            g.drawImage(ResourceLoader.getInstance().getSprite("foglights.png"), (Stage.WIDTH / 5), 160, stage);
            g.drawImage(ResourceLoader.getInstance().getSprite("invincible.png"), (Stage.WIDTH / 5), 220, stage);
            g.drawImage(ResourceLoader.getInstance().getSprite("slowmotion.png"), (Stage.WIDTH / 5), 280, stage);
            g.drawString("Fog Lights", (Stage.WIDTH / 3), 200);
            g.drawString("Invincibility", (Stage.WIDTH / 3), 250);
            g.drawString("Slow Motion", (Stage.WIDTH / 3), 300);

            String[] options = new String[]{"Buy (" + FOG_LIGHTS_COST + " coins)", "Buy (" + INVINCIBILITY_COST + " coins)", "Buy (" + SLOW_MOTION_COST + " coins)"};

            for (int i = 0; i < options.length; i++) {
                g.setColor(menuSelection == i ? Color.GREEN : Color.WHITE);
                g.drawString(options[i], 500, 200 + (50 * i));
            }

            g.setColor(menuSelection == 3 ? Color.GREEN : Color.WHITE);
            g.drawString("Back to Store", (Stage.WIDTH / 2), 700);

            // VEHICLES MENU
        } else if (menuState == 2) {

            g.setFont(new Font("Impact", Font.PLAIN, 25));
            g.drawString("Coins: " + PlayerInventory.getCurrency(), stage.WIDTH / 3, 150);

            // Car
            g.drawImage(ResourceLoader.getInstance().getSprite("CarFullHealth.png"), (Stage.WIDTH / 3), 180, stage);

            if (menuSelection == 0) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.WHITE);
            }
            if (PlayerInventory.getEquippedVehicle() != CAR) {
                g.drawString("Equip", 400, 330);
            } else {
                g.drawString("Equipped", 400, 330);
            }

            // Truck
            g.drawImage(ResourceLoader.getInstance().getSprite("TruckFullHealth.png"), (Stage.WIDTH / 3), 375, stage);

            if (menuSelection == 1) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.WHITE);
            }
            if (!PlayerInventory.isTruckOwned()) {
                g.drawString("Buy (" + TRUCK_COST + " coins)", 400, 400);
            } else if (PlayerInventory.getEquippedVehicle() != TRUCK) {
                g.drawString("Equip", 400, 400);
            } else {
                g.drawString("Equipped", 400, 400);
            }

            // ATV
            g.drawImage(ResourceLoader.getInstance().getSprite("atv.png"), (Stage.WIDTH / 3), 575, stage);

            if (menuSelection == 2) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.WHITE);
            }
            if (!PlayerInventory.isAtvOwned()) {
                g.drawString("Buy (" + ATV_COST + " coins)", 400, 575);
            } else if (PlayerInventory.getEquippedVehicle() != ATV) {
                g.drawString("Equip", 400, 575);
            } else {
                g.drawString("Equipped", 400, 575);
            }

            g.setColor(menuSelection == 3 ? Color.GREEN : Color.WHITE);
            g.drawString("Back to Store", (Stage.WIDTH / 2), 700);

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

    /**
     * Handles key release events for game over screen.
     *
     * @param e key release event
     */
    @Override
    public void triggerKeyRelease(KeyEvent e) {

    }

    private void handleEnterPress() {

        // Main store menu
        if (menuState == 0) {
            switch (menuSelection) {
                case 0: // Powerups
                    menuState = 1;
                    break;
                case 1: // Vehicles
                    menuState = 2;
                    break;
                case 2: // Buy coins
                    break;
                case 3: // Back to main menu
                    stage.initMenu();
                    break;
            }
        }

        // Powerups Menu
        else if (menuState == 1) {
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
                    stage.initStore();
                    break;
            }
        }

        // Vehicles menu
        else if (menuState == 2) {
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
        }

    }

}
