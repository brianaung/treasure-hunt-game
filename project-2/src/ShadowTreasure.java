// some codes are taken from provided project 1 solution.
import bagel.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * An example Bagel game.
 */
public class ShadowTreasure extends AbstractGame {

    private final Image BACKGROUND = new Image("res/images/background.png");
    // constant variables
    public static final int ClOSENESS = 50;
    public static final int SHOOTING_RANGE = 150;
    public static final int DEATH_RANGE = 25;
    private static final int LOWENERGY = 3;
    // for rounding double number
    private static DecimalFormat df = new DecimalFormat("0.00");
    // tick cycle and var
    private final int TICK_CYCLE = 10;
    private int tick;
    // list of characters
    private Player player;
    private Treasure treasure;
    private Bullet bullet;
    private ArrayList<Sandwich> sandwiches = new ArrayList<Sandwich> ();
    private ArrayList<Zombie> zombies = new ArrayList<Zombie>();
    // end of game indicator
    private boolean endOfGame;

    /**
     * construct Shadow Treasure game
     */
    public ShadowTreasure() throws IOException {
        this.loadEnvironment("res/IO/environment.csv");
        this.tick = 1;
        this.endOfGame = false;
    }

    /**
     * @return the treasure object
     */
    public Treasure getTreasure() {
        return treasure;
    }

    /**
     * @return the bullet object
     */
    public Bullet getBullet() {
        return bullet;
    }

    /**
     * @return array of sandwiches
     */
    public ArrayList<Sandwich> getSandwiches() {
        return sandwiches;
    }

    /**
     *
     * @return array of zombies
     */
    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    /**
     * set whether the game has ended or not
     * @param endOfGame game status
     */
    public void setEndOfGame(boolean endOfGame) {
        this.endOfGame = endOfGame;
    }

    /**
     * outputs the object coordinate to console
     * @param x x coordinate of the object
     * @param y y coordinate of the object
     */
    public void printInfo(double x, double y) {
        System.out.println(df.format(x) + "," + df.format(y));
    }

    /**
     * print the x and y coordinates into output.csv file
     */
    public void writeFile(double x, double y) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("res/IO/output.csv", true))) {

            pw.append(String.valueOf(df.format(x)));
            pw.append(",");
            pw.append(String.valueOf(df.format(y)));
            pw.append("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load environment from input file
     * @param filename csv file containing characters information
     */
    private void loadEnvironment(String filename){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                type = type.replaceAll("[^a-zA-Z0-9]", "");

                // coordinates
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);

                // create game objects
                switch (type) {
                    case "Player" -> this.player = new Player(x, y, Integer.parseInt(parts[3]));
                    case "Treasure" -> this.treasure = new Treasure(x, y);
                    case "Zombie"  -> this.zombies.add(new Zombie(x, y));
                    case "Sandwich"  -> this.sandwiches.add(new Sandwich(x, y));
                    default    -> throw new BagelError("Unknown type: " + type);
                }
                this.bullet = new Bullet();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * @return the closest sandwich to the player
     */
    public Sandwich getSandwich() {
        double closestToP = Double.MAX_VALUE;
        Sandwich closestS = new Sandwich();
        for (Sandwich s : sandwiches) {
            double disToP = s.getPos().distanceTo(player.getPos());
            if (disToP < closestToP){
                closestToP = disToP;
                closestS = s;
            }
        }
        return closestS;
    }

    /**
     * @return the closest zombie to the player
     */
    public Zombie getZombie() {
        double closestToP = Double.MAX_VALUE;
        Zombie closestZ = new Zombie();
        for (Zombie z : zombies) {
            double disToP = z.getPos().distanceTo(player.getPos());
            if (disToP < closestToP) {
                closestToP = disToP;
                closestZ = z;
            }
        }
        return closestZ;
    }

    /**
     * Performs a state update.
     */
    @Override
    public void update(Input input) {
        if (this.endOfGame || input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        } else{
            // Draw background
            BACKGROUND.drawFromTopLeft(0, 0);
            // Update status when the TICK_CYCLE is up
            if (tick > TICK_CYCLE) {
                // update player and bullet status
                player.update(this);
                if (bullet.isShot()) {
                    printInfo(bullet.getPos().x, bullet.getPos().y);
                    writeFile(bullet.getPos().x, bullet.getPos().y);
                    bullet.update(this);
                }
                tick = 1;
            }
            tick++;

            // render game objects
            player.render();
            treasure.draw();
            // render bullet only if player has shot it
            if (bullet.isShot()) {
                bullet.render();
            }
            for (Sandwich sandwich: sandwiches) {
                sandwich.draw();
            }
            for (Zombie zombie: zombies) {
                zombie.draw();
            }
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasure game = new ShadowTreasure();
        // clean the output csv file to add new positions
        PrintWriter pw = new PrintWriter("res/IO/output.csv");
        game.run();
    }
}
