package game;

/**
 * Keeps track of player inventory obtained within the game,
 * displays high score information and allows settings changes.
 */
public class PlayerInventory {

    private static boolean settingsMusicOn = true;
    private static boolean settingSoundsOn = true;
    private static int highScore = 0;

    /**
     *
     * @return
     */
    public static boolean isSettingsMusicOn() {
        return settingsMusicOn;
    }

    /**
     *
     * @param settingsMusicOn
     */
    public static void setSettingsMusicOn(boolean settingsMusicOn) {
        PlayerInventory.settingsMusicOn = settingsMusicOn;
    }

    /**
     *
     * @return
     */
    public static boolean isSettingSoundsOn() {
        return settingSoundsOn;
    }

    /**
     *
     * @param settingSoundsOn
     */
    public static void setSettingSoundsOn(boolean settingSoundsOn) {
        PlayerInventory.settingSoundsOn = settingSoundsOn;
    }

    /**
     *
     * @return
     */
    public static int getHighScore() {
        return highScore;
    }

    /**
     *
     * @param currentScore
     */
    public static void setHighScore(int currentScore) {
        if (currentScore > highScore) {
            highScore = currentScore;
        }
    }

}
