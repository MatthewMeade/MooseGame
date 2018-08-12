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
     *  Get value for settingsMusicOn
     * @return settingsMusicOn
     */
    public static boolean isSettingsMusicOn() {
        return settingsMusicOn;
    }

    /**
     * Set value for settingsMusic
     * @param settingsMusicOn status of settings music
     */
    public static void setSettingsMusicOn(boolean settingsMusicOn) {
        PlayerInventory.settingsMusicOn = settingsMusicOn;
    }

    /**
     * Get value for settingSoundsOn
     * @return settingSoundsOn
     */
    public static boolean isSettingSoundsOn() {
        return settingSoundsOn;
    }

    /**
     * Set value for settingSoundsOn
     * @param settingSoundsOn status of settings sounds
     */
    public static void setSettingSoundsOn(boolean settingSoundsOn) {
        PlayerInventory.settingSoundsOn = settingSoundsOn;
    }

    /**
     * Get value for highScore
     * @return high score
     */
    public static int getHighScore() {
        return highScore;
    }

    /**
     * Set value for high score
     * @param currentScore current game score
     */
    public static void setHighScore(int currentScore) {
        if (currentScore > highScore) {
            highScore = currentScore;
        }
    }

    /**
     * Get value for fogLightsCount
     * @return fog lights count
     */
    public static int getFogLightsCount() {
        return fogLightsCount;
    }

    /**
     * Get value for slowMotionCount
     * @return slow motion count
     */
    public static int getSlowMotionCount() {
        return slowMotionCount;
    }

    /**
     * Get value for invincibilityCount
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


}
