package game;

/**
 * Keeps track of player inventory obtained within the game,
 * displays high score information and allows settings changes.
 */
public class PlayerInventory {

    private static boolean settingsMusicOn = true;
    private static boolean settingSoundsOn = true;
    private static int highScore = 0;
    private static int fogLightsCount = 0;
    private static int slowMotionCount = 0;
    private static int invincibilityCount = 0;


    /**
     * @return
     */
    public static boolean isSettingsMusicOn() {
        return settingsMusicOn;
    }

    /**
     * @param settingsMusicOn
     */
    public static void setSettingsMusicOn(boolean settingsMusicOn) {
        PlayerInventory.settingsMusicOn = settingsMusicOn;
    }

    /**
     * @return
     */
    public static boolean isSettingSoundsOn() {
        return settingSoundsOn;
    }

    /**
     * @param settingSoundsOn
     */
    public static void setSettingSoundsOn(boolean settingSoundsOn) {
        PlayerInventory.settingSoundsOn = settingSoundsOn;
    }

    /**
     * @return
     */
    public static int getHighScore() {
        return highScore;
    }

    /**
     * @param currentScore
     */
    public static void setHighScore(int currentScore) {
        if (currentScore > highScore) {
            highScore = currentScore;
        }
    }

    public static int getFogLightsCount() {
        return fogLightsCount;
    }

    public static int getSlowMotionCount() {
        return slowMotionCount;
    }

    public static int getInvincibilityCount() {
        return invincibilityCount;
    }

    public static void incrementFogLights() {
        fogLightsCount++;
    }

    public static void incrementInvincibility() {
        invincibilityCount++;
    }

    public static void incrementSlowMotion() {
        slowMotionCount++;
    }

    public static boolean useFogLightsPowerup() {
        if (getFogLightsCount() > 0) {
            fogLightsCount--;
            return true;
        } else {
            return false;
        }
    }

    public static boolean useInvincibilityPowerup() {
        if (getInvincibilityCount() > 0) {
            invincibilityCount--;
            return true;
        } else {
            return false;
        }
    }

    public static boolean useSlowMotionPowerup() {
        if (getSlowMotionCount() > 0) {
            slowMotionCount--;
            return true;
        } else {
            return false;
        }
    }

    public static void clearPowerups() {
        fogLightsCount = 0;
        invincibilityCount = 0;
        slowMotionCount = 0;
    }


}
