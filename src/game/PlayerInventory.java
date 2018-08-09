package game;

public class PlayerInventory {
    private static boolean settingsMusicOn = true;
    private static boolean settingSoundsOn = true;

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
}
