import bagel.*;
import bagel.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * An example Bagel game.
 */
public class ShadowTreasure extends AbstractGame {

    private static final int TYPE_INDEX = 0;
    private static final int X_INDEX = 1;
    private static final int Y_INDEX = 2;
    private static final int NRG_INDEX = 3;
    private static final Point TEXT_POINT = new Point(20, 760);
    private static final Point BG_POINT = new Point(0, 0);
    private static final double STEP_SIZE = 10;
    private static final double MEET_DISTANCE = 50;

    private final Image backgroundImage;
    private final Image playerImage;
    private final Zombie zombie = new Zombie();
    private final Sandwich sandwich = new Sandwich();
    private final DrawOptions options = new DrawOptions();
    private final Font font;

    private double playerX;
    private double playerY;
    private int playerNrg;
    private double playerDirX;
    private double playerDirY;
    private double tick;

    // for rounding double number; use this to print the location of the player
    private static DecimalFormat df = new DecimalFormat("0.00");

    // print the provided player information
    public static void printInfo(double x, double y, int e) {
        System.out.println(df.format(x) + "," + df.format(y) + "," + e);
    }

    // constructor
    public ShadowTreasure() throws IOException {
        this.loadEnvironment("res/IO/environment.csv");
        this.backgroundImage = new Image("res/images/background.png");
        this.playerImage = new Image("res/images/player.png");
        this.playerDirX = 0;
        this.playerDirY = 0;
        this.tick = 1;
        this.font = new Font("res/font/DejaVuSans-Bold.ttf", 20);
    }

    /**
     * read from the input file and set up the environment
     */
    private void loadEnvironment(String filename){
        try (BufferedReader br =
                new BufferedReader (new FileReader(filename))) {

            String text;
            while ((text = br.readLine()) != null) {
                // process the text to remove special chars
                text = text.replaceAll("[^a-zA-Z0-9,]", "");
                String[] cells = text.split(",");

                // save the information of each entities
                if (cells[TYPE_INDEX].equals("Player")) {
                    playerX = Double.parseDouble(cells[X_INDEX]);
                    playerY = Double.parseDouble(cells[Y_INDEX]);
                    playerNrg = Integer.parseInt(cells[NRG_INDEX]);
                } else if (cells[TYPE_INDEX].equals("Zombie")) {
                    zombie.setPosition(Double.parseDouble(cells[X_INDEX]),
                            Double.parseDouble(cells[Y_INDEX]));
                } else if (cells[TYPE_INDEX].equals("Sandwich")) {
                    sandwich.setPosition(Double.parseDouble(cells[X_INDEX]),
                            Double.parseDouble(cells[Y_INDEX]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * find player direction to destination object
     */
    public void setPlayerDir(Point desLoc) {
        double len = new Point(playerX, playerY).distanceTo(desLoc);
        playerDirX = (desLoc.x - playerX)/len;
        playerDirY = (desLoc.y - playerY)/len;
    }


    /**
     * Performs a state update.
     */
    @Override
    public void update(Input input) {
        // only update the state of the game every 10 frames (1 tick)
        if (tick % 10 == 0) {

            printInfo(playerX, playerY, playerNrg);

            // move player to either zombie or sandwich
            if (playerNrg >= 3) {
                // move player to zombie as long as energy >= 3
                setPlayerDir(zombie.getPosition());
                playerX += STEP_SIZE * playerDirX;
                playerY += STEP_SIZE * playerDirY;
            } else {
                // move player to sandwich if the energy is lower than 3
                setPlayerDir(sandwich.getPosition());
                playerX += STEP_SIZE * playerDirX;
                playerY += STEP_SIZE * playerDirY;
            }

            // player meets either zombie or sandwich, add or reduce energy
            if (new Point(playerX, playerY).
                    distanceTo(zombie.getPosition()) <= MEET_DISTANCE) {
                // player meets zombie, reduce 3 energy then end the game
                playerNrg -= 3;
                printInfo(playerX, playerY, playerNrg);
                Window.close();
            } else if (!sandwich.isEaten && new Point(playerX, playerY).
                    distanceTo(sandwich.getPosition()) <= MEET_DISTANCE) {
                // player meets sandwich, eat it then add 5 energy
                playerNrg += 5;
                sandwich.markAsEaten();
            }
        }

        // draw the graphics and update them
        backgroundImage.drawFromTopLeft(BG_POINT.x, BG_POINT.y);
        playerImage.draw(playerX, playerY);
        zombie.update();
        sandwich.update();
        // print current player energy
        String energy = Integer.toString(playerNrg);
        font.drawString("energy: " + energy, TEXT_POINT.x,
                TEXT_POINT.y, options.setBlendColour(Colour.BLACK));

        tick++;
    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasure game = new ShadowTreasure();
        game.run();
    }
}
