// some codes are taken from provided project 1 solution.
import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Vector2;

public class Player implements Movable {

    public static final String FILENAME = "res/images/player.png";
    public static final double STEP_SIZE = 10;
    private static final int LOWENERGY = 3;
    private static final Vector2 ZERO_VECTOR = new Vector2(0,0);
    // healthbar font
    private final Font FONT = new Font("res/font/DejaVuSans-Bold.ttf", 20);
    private final DrawOptions OPT = new DrawOptions();
    // player info
    private final Image image;
    private Point pos;
    private double directionX;
    private double directionY;
    private int energy;

    /**
     * construct player object
     * @param x x coordinate of player
     * @param y y coordinate of player
     * @param energy amount of energy player has
     */
    public Player(double x, double y, int energy) {
        this.image = new Image(FILENAME);
        this.pos = new Point(x,y);
        this.directionX = ZERO_VECTOR.x;
        this.directionY = ZERO_VECTOR.y;
        this.energy = energy;
    }

    /**
     * @return the position of player in Point
     */
    public Point getPos(){
        return this.pos;
    }

    /**
     * @return the energy level of player
     */
    public int getEnergy(){
        return this.energy;
    }

    /**
     * point player to a destination
     * @param dest position of destination object to point to
     */
    public void pointTo(Point dest){
        this.directionX = dest.x - this.pos.x;
        this.directionY = dest.y - this.pos.y;
        normalizeDir();
    }

    /**
     * normalize direction
     */
    public void normalizeDir(){
        double len = Math.sqrt(Math.pow(this.directionX,2)+Math.pow(this.directionY,2));
        this.directionX /= len;
        this.directionY /= len;
    }

    /**
     * change the state of player during the game based on algorithm 1
     * @param tomb Shadow Treasure game
     */
    public void update(ShadowTreasure tomb){

        if (tomb.getZombies().isEmpty()) {
            // no more zombies left. go to treasure
            pointTo(tomb.getTreasure().getPos());
        } else if (this.energy >= LOWENERGY && !tomb.getZombies().isEmpty()) {
            // if player has energy and there are more zombies, go to closest zombie
            pointTo(tomb.getZombie().getPos());
        } else if (!tomb.getSandwiches().isEmpty()){
            // go to sandwich if there are more sandwiches and energy is low
            pointTo(tomb.getSandwich().getPos());
        }

        if ((this.energy<LOWENERGY && tomb.getSandwiches().isEmpty() && !tomb.getZombies().isEmpty() && !tomb.getBullet().isShot())) {
            tomb.setEndOfGame(true);
            System.out.print(this.energy);
        } else if (tomb.getTreasure().meets(this)) {
            // game won. player reach treasure
            tomb.setEndOfGame(true);
            System.out.print(this.energy + ", success!");
        } else if (tomb.getSandwich().meets(this)) {
            eatSandwich();
            tomb.getSandwiches().remove(tomb.getSandwich());
        } else if (tomb.getZombie().meets(this) && !tomb.getBullet().isShot()) {
            reachZombie();
            tomb.getBullet().setPos(pos);
            tomb.getBullet().setShot(true);
        }

        // move one step
        this.pos = new Point(this.pos.x+STEP_SIZE*this.directionX, this.pos.y+STEP_SIZE*this.directionY);
    }

    /**
     * render player image and its energy level
     */
    public void render() {
        image.drawFromTopLeft(pos.x, pos.y);
        FONT.drawString("energy: "+ energy,20,760, OPT.setBlendColour(Colour.BLACK));
    }

    /**
     * add +5 energy if player eats sandwich
     */
    public void eatSandwich(){
        energy += 5;
    }

    /**
     * reduce -3 energy when player reach zombie and shoots bullet
     */
    public void reachZombie(){
        energy -= 3;
    }
}
