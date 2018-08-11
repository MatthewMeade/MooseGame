package game;

public class PlayerInventory {
    private static boolean settingsMusicOn = true;
    private static boolean settingSoundsOn = true;


    public static int health = 3;

    public static boolean isSettingsMusicOn() {
        return settingsMusicOn;
    }

    public static void setSettingsMusicOn(boolean settingsMusicOn) {
        PlayerInventory.settingsMusicOn = settingsMusicOn;
    }

    public static boolean isSettingSoundsOn() {
        return settingSoundsOn;
    }

    public static void setSettingSoundsOn(boolean settingSoundsOn) {
        PlayerInventory.settingSoundsOn = settingSoundsOn;
    }

    public static boolean decreaseHealth() {
        System.out.println("Hit moose!");
        health--;
        if (health > 0) {
            return true;
        }
        health = 3;
        return false;

    }
}
