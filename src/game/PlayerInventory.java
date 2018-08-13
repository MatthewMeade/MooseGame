package game;

import java.io.*;
import java.util.Scanner;

/**
 * Keeps track of player settings and inventory within the game,
 * displays high score information, allows settings changes,
 * checks if purchases are possible.
 */
public class PlayerInventory {

    private static boolean settingsMusicOn = true;
    private static boolean settingSoundsOn = true;
    private static boolean showFPSOverlay = false;
    private static int highScore = 0;
    private static int fogLightsCount = 0;
    private static int slowMotionCount = 0;
    private static int invincibilityCount = 0;
    private static int currency = 0;

    private static boolean truckOwned = false;
    private static boolean atvOwned = false;

    public enum Vehicles {
        CAR,
        TRUCK,
        ATV
    }

    private static Vehicles equippedVehicle = Vehicles.CAR;

    /**
     * Gets value for equippedVehicle.
     *
     * @return equipped vehicle value
     */
    public static Vehicles getEquippedVehicle() {
        return equippedVehicle;
    }

    /**
     * Sets value for equippedVehicle.
     *
     * @param vehicle equipped vehicle value
     */
    public static void setEquippedVehicle(Vehicles vehicle) {
        equippedVehicle = vehicle;
    }


    /**
     * Get value for settingsMusicOn.
     *
     * @return settingsMusicOn
     */
    public static boolean isSettingMusicOn() {
        return settingsMusicOn;
    }

    /**
     * Set value for settingsMusic
     *
     * @param settingsMusicOn status of settings music
     */
    public static void setSettingMusicOn(boolean settingsMusicOn) {
        PlayerInventory.settingsMusicOn = settingsMusicOn;
    }

    /**
     * Get value for settingSoundsOn
     *
     * @return settingSoundsOn
     */
    public static boolean isSettingSoundsOn() {
        return settingSoundsOn;
    }

    /**
     * Set value for settingSoundsOn
     *
     * @param settingSoundsOn status of settings sounds
     */
    public static void setSettingSoundsOn(boolean settingSoundsOn) {
        PlayerInventory.settingSoundsOn = settingSoundsOn;
    }

    /**
     * Get value for highScore
     *
     * @return high score
     */
    public static int getHighScore() {
        return highScore;
    }

    /**
     * Set value for high score
     *
     * @param currentScore current game score
     */
    public static void setHighScore(int currentScore) {
        if (currentScore > highScore) {
            highScore = currentScore;
        }
    }

    /**
     * Gets value for showFPSOverlay setting
     *
     * @return showFPSOverlay boolean true if FPS overlay setting enabled
     */
    public static boolean isShowFPSOverlayOn() {
        return showFPSOverlay;
    }

    /**
     * Sets value for showFPSOverlay setting
     *
     * @param showFPSOverlay boolean true if FPS overlay setting enabled
     */
    public static void setShowFPSOverlay(boolean showFPSOverlay) {
        PlayerInventory.showFPSOverlay = showFPSOverlay;
    }

    /**
     * Gets the player's currency
     *
     * @return currency int
     */
    public static int getCurrency() {
        return currency;
    }

    /**
     * Adds additional currency to the player's inventory
     *
     * @param additionalCurrency int amount of currency to add
     */
    public static void addCurrency(int additionalCurrency) {
        currency += additionalCurrency;
    }

    /**
     * Determines whether a player's currency is sufficient to purchase an item.
     *
     * @param cost int Cost of an item
     * @return Whether sufficient currency exists
     */
    public static boolean spendCurrency(int cost) {

        if (currency >= cost) {
            currency -= cost;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks to see if truck is owned.
     *
     * @return Whether truck is owned
     */
    public static boolean isTruckOwned() {
        return truckOwned;
    }

    /**
     * Sets truck ownership value to true following truck purchase.
     */
    public static void buyTruck() {
        truckOwned = true;
    }

    /**
     * Checks to see if ATV is owned.
     *
     * @return Whether ATV is owned
     */
    public static boolean isAtvOwned() {
        return atvOwned;
    }

    /**
     * Sets truck ownership value to true following truck purchase.
     */
    public static void buyATV() {
        atvOwned = true;
    }


    /**
     * Get value for fogLightsCount
     *
     * @return fog lights count
     */
    public static int getFogLightsCount() {
        return fogLightsCount;
    }

    /**
     * Get value for slowMotionCount
     *
     * @return slow motion count
     */
    public static int getSlowMotionCount() {
        return slowMotionCount;
    }

    /**
     * Get value for invincibilityCount
     *
     * @return invincibility count
     */
    public static int getInvincibilityCount() {
        return invincibilityCount;
    }

    /**
     * Increases fog lights count
     */
    public static void incrementFogLights() {
        fogLightsCount++;
    }

    /**
     * Increases invincibility count
     */
    public static void incrementInvincibility() {
        invincibilityCount++;
    }

    /**
     * Increments slow motion count
     */
    public static void incrementSlowMotion() {
        slowMotionCount++;
    }

    /**
     * Allows for use of fog lights powerup by player.
     *
     * @return whether fog lights powerup is available
     */
    public static boolean useFogLightsPowerup() {
        if (getFogLightsCount() > 0) {
            fogLightsCount--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Allows for use of invincibility powerup by player.
     *
     * @return whether invincibility powerup is available
     */
    public static boolean useInvincibilityPowerup() {
        if (getInvincibilityCount() > 0) {
            invincibilityCount--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Allows for use of slow motion powerup by player.
     *
     * @return whether slow motion powerup is available
     */
    public static boolean useSlowMotionPowerup() {
        if (getSlowMotionCount() > 0) {
            slowMotionCount--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Resets the number of powerups available to zero.
     */
    public static void clearPowerups() {
        fogLightsCount = 0;
        invincibilityCount = 0;
        slowMotionCount = 0;
    }

    /**
     * Saves game settings to a file. Settings include high score, currency,
     * music settings, sound settings, FPS overlay, vehicle.
     */
    public static void saveToFile() {

        String equippedVehicleSaveString = "";

        if (getEquippedVehicle() == Vehicles.CAR) {
            equippedVehicleSaveString = "Car";
        } else if (getEquippedVehicle() == Vehicles.TRUCK) {
            equippedVehicleSaveString = "Truck";
        } else if (getEquippedVehicle() == Vehicles.ATV) {
            equippedVehicleSaveString = "ATV";
        }

        String s = (Integer.toString(highScore) + "," +
                Integer.toString(currency) + "," +
                Boolean.toString(settingsMusicOn) + "," +
                Boolean.toString(settingSoundsOn) + "," +
                Boolean.toString(showFPSOverlay) + "," +
                equippedVehicleSaveString + "," +
                Boolean.toString(truckOwned) + "," +
                Boolean.toString(atvOwned)
        );
        File saveFile = new File("./save.dat");
        try {
            FileWriter fw = new FileWriter(saveFile);
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            System.out.println("IOException occurred");
        }
    }

    /**
     * Loads game settings from a save file.
     * High score, currency, music toggle, sound effects toggle, FPS overlay toggle
     */
    public static void loadFromFile() {

        File saveFile = new File("./save.dat");

        if (!saveFile.exists()) {
            return;
        }

        try {
            Scanner scanner = new Scanner(saveFile);
            String line = scanner.nextLine();
            String[] s = line.split(",");

            highScore = Integer.parseInt(s[0]);
            currency = Integer.parseInt(s[1]);
            settingsMusicOn = Boolean.parseBoolean(s[2]);
            settingSoundsOn = Boolean.parseBoolean(s[3]);
            showFPSOverlay = Boolean.parseBoolean(s[4]);

            if (s[5].equals("Car")) {
                equippedVehicle = Vehicles.CAR;
            } else if (s[5].equals("Truck")) {
                equippedVehicle = Vehicles.TRUCK;
            } else if (s[5].equals("ATV")) {
                equippedVehicle = Vehicles.ATV;
            }
            truckOwned = Boolean.parseBoolean(s[6]);
            atvOwned = Boolean.parseBoolean(s[7]);


        } catch (IOException e) {
            System.out.println("IOException occurred");
        }

    }

    /**
     * Clears the save file contents returning the high score and player currency
     * values to 0 and settings to their default values.
     */
    public static void clearSave() {
        highScore = 0;
        currency = 0;
        settingsMusicOn = true;
        settingSoundsOn = true;
        showFPSOverlay = false;
        equippedVehicle = Vehicles.CAR;
        truckOwned = false;
        atvOwned = false;
        saveToFile();
    }


}
