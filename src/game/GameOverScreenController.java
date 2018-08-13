package game;

import actors.KeyboardControllable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Handles creation of game over screen.
 */
public class GameOverScreenController implements KeyboardControllable {

    private MooseGame stage;
    private int menuSelection = 0;
    private int finalScore;
    private int chosenPSA;
    private int coins;

    String[] PSAs = new String[]{
            "Remember to keep your car on the road!",
            "Fog reduces visibility, use your fog lights\nand pay extra attention for moose!",
            "Always wear your seat belt!",
            "Pay attention to road signs identifying\nareas with a large moose population!",
            "Don't drive ATVs on the highway!",
            "Don't text and drive!"
    };

    /**
     * Constructor for GameOverScreenController.
     *
     * @param stage      game window
     * @param finalScore final score of current game
     */
    public GameOverScreenController(MooseGame stage, int finalScore, int coins) {
        this.stage = stage;
        this.finalScore = finalScore;
        this.coins = coins;

        chosenPSA = new Random().nextInt(PSAs.length);

    }

    /**
     * Render graphics for game over screen
     *
     * @param g screen to be rendered
     */
    public void paint(Graphics g) {


        // Draw background
        g.drawImage(ResourceLoader.getInstance().getSprite("road.png"), 0, 0, stage);

        // Draw text background
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(Stage.WIDTH / 6, Stage.HEIGHT / 4, 2 * Stage.WIDTH / 3, Stage.WIDTH / 2);

        // Draw text background
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(Stage.WIDTH / 6, 40, 2 * Stage.WIDTH / 3, 75);

        // Game Over Text
        Font titleFont = new Font("Impact", Font.PLAIN, 60);
        FontMetrics metrics = g.getFontMetrics(titleFont);
        g.setFont(titleFont);
        g.setColor(Color.RED);
        g.drawString("Game Over!", (Stage.WIDTH - metrics.stringWidth("Game Over!")) / 2, 100);


        // Draw current and high scores
        g.setColor(Color.WHITE);
        Font menuFont = new Font("Impact", Font.PLAIN, 35);
        g.setFont(menuFont );

        metrics = g.getFontMetrics(menuFont);

        g.drawString("Score: " + finalScore, Stage.WIDTH / 6 + 10, 225);

        String highScoreText = "High Score: " + PlayerInventory.getHighScore();
        g.drawString(highScoreText, (5*Stage.WIDTH/6) - metrics.stringWidth(highScoreText) - 10, 225);

        // Draw coin info
        g.drawString("New Coins: " + coins, Stage.WIDTH / 6 + 10, 275);

        String totalCoinsText = "Total Coins: " + PlayerInventory.getCurrency();
        g.drawString(totalCoinsText, (5*Stage.WIDTH/6) - metrics.stringWidth(totalCoinsText) - 10, 275);

        // Draw Menu Options
        g.setFont(new Font("Impact", Font.PLAIN, 40));

        String[] optionText = new String[]{"Play Again", "Return to Main Menu"};
        for (int i = 0; i < optionText.length; i++) {
            g.setColor(menuSelection == i ? Color.GREEN : Color.WHITE);
            g.drawString(optionText[i], (Stage.WIDTH / 2) - (int)(optionText[i].length() * 8.5), 350 + (60 * i));
        }


        Font psaFont = new Font("Calibri", Font.BOLD, 25);
        Font psaTitleFont = new Font("Calibri", Font.PLAIN, 25);

        g.setFont(psaTitleFont);
        metrics = g.getFontMetrics(psaTitleFont);

        g.setColor(Color.white);

        g.drawString("Driving Tip:", (Stage.WIDTH - metrics.stringWidth("Driving Tip:")) / 2, 475);

        g.setFont(psaTitleFont);
        metrics = g.getFontMetrics(psaFont);

        String[] psaStrings = PSAs[chosenPSA].split("\n");
        for (int i = 0; i < psaStrings.length; i++) {
            int x = (Stage.WIDTH / 6) + ((2 * Stage.WIDTH / 3) - metrics.stringWidth(psaStrings[i])) / 2;
            g.drawString(psaStrings[i], x, 500 + 25 * i);

        }




    }

    /**
     * Checks which game stage has been selected using the Enter key,
     * goes to the selected stage.
     */
    private void handleEnterPress() {

        if (menuSelection == 0) {
            stage.initGame();
        } else if (menuSelection == 1) {
            stage.initMenu();
        }

    }

    /**
     * Handles key press events for game over screen.
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
                menuSelection = 1;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            handleEnterPress();
        }

        menuSelection %= 2;

    }

    /**
     * Handles key release events for game over screen.
     *
     * @param e key release event
     */
    @Override
    public void triggerKeyRelease(KeyEvent e) {

    }
}