package game;

import actors.KeyboardControllable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Controls the Game Over screen.
 */
public class GameOverScreenController implements KeyboardControllable {

    private MooseGame mooseGame;
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
     * Constructs a GameOverScreenController.
     *
     * @param mooseGame      game window
     * @param finalScore final score of current game
     */
    public GameOverScreenController(MooseGame mooseGame, int finalScore, int coins) {
        this.mooseGame = mooseGame;
        this.finalScore = finalScore;
        this.coins = coins;

        chosenPSA = new Random().nextInt(PSAs.length);

    }

    /**
     * Renders graphics for game over screen
     *
     * @param g screen to be rendered
     */
    public void paint(Graphics g) {


        // Draw background
        g.drawImage(ResourceLoader.getInstance().getSprite("road.png"), 0, 0, mooseGame);

        // Draw text background
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(MooseGame.WIDTH / 6, MooseGame.HEIGHT / 4, 2 * MooseGame.WIDTH / 3, MooseGame.WIDTH / 2);

        // Draw text background
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(MooseGame.WIDTH / 6, 40, 2 * MooseGame.WIDTH / 3, 75);

        // Game Over Text
        Font titleFont = new Font("Impact", Font.PLAIN, 60);
        FontMetrics metrics = g.getFontMetrics(titleFont);
        g.setFont(titleFont);
        g.setColor(Color.RED);
        g.drawString("Game Over!", (MooseGame.WIDTH - metrics.stringWidth("Game Over!")) / 2, 100);


        // Draw current and high scores
        g.setColor(Color.WHITE);
        Font menuFont = new Font("Impact", Font.PLAIN, 35);
        g.setFont(menuFont );

        metrics = g.getFontMetrics(menuFont);

        g.drawString("Score: " + finalScore, MooseGame.WIDTH / 6 + 10, 225);

        String highScoreText = "High Score: " + PlayerInventory.getHighScore();
        g.drawString(highScoreText, (5*MooseGame.WIDTH/6) - metrics.stringWidth(highScoreText) - 10, 225);

        // Draw coin info
        g.drawString("New Coins: " + coins, MooseGame.WIDTH / 6 + 10, 275);

        String totalCoinsText = "Total Coins: " + PlayerInventory.getCurrency();
        g.drawString(totalCoinsText, (5*MooseGame.WIDTH/6) - metrics.stringWidth(totalCoinsText) - 10, 275);

        // Draw Menu Options
        g.setFont(new Font("Impact", Font.PLAIN, 40));

        String[] optionText = new String[]{"Play Again", "Return to Main Menu"};
        for (int i = 0; i < optionText.length; i++) {
            g.setColor(menuSelection == i ? Color.GREEN : Color.WHITE);
            g.drawString(optionText[i], (MooseGame.WIDTH / 2) - (int)(optionText[i].length() * 8.5), 350 + (60 * i));
        }


        Font psaFont = new Font("Calibri", Font.BOLD, 25);
        Font psaTitleFont = new Font("Calibri", Font.PLAIN, 25);

        g.setFont(psaTitleFont);
        metrics = g.getFontMetrics(psaTitleFont);

        g.setColor(Color.white);

        g.drawString("Driving Tip:", (MooseGame.WIDTH - metrics.stringWidth("Driving Tip:")) / 2, 475);

        g.setFont(psaTitleFont);
        metrics = g.getFontMetrics(psaFont);

        String[] psaStrings = PSAs[chosenPSA].split("\n");
        for (int i = 0; i < psaStrings.length; i++) {
            int x = (MooseGame.WIDTH / 6) + ((2 * MooseGame.WIDTH / 3) - metrics.stringWidth(psaStrings[i])) / 2;
            g.drawString(psaStrings[i], x, 500 + 25 * i);

        }




    }

    /**
     * Handles a Enter key press on the Game Over screen.
     */
    private void handleEnterPress() {

        if (menuSelection == 0) {
            mooseGame.initGame();
        } else if (menuSelection == 1) {
            mooseGame.initMenu();
        }

    }

    /**
     * Handles key presses on the Game Over screen.
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
     * Handles key release events on the Game Over screen.
     *
     * @param e key release event
     */
    @Override
    public void triggerKeyRelease(KeyEvent e) {

    }
}