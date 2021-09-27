// some codes are taken from provided project 1 solution.
import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

public class Bullet implements Movable {

    public static final double STEP_SIZE = 25;
    private static final Vector2 ZERO_VECTOR = new Vector2(0,0);
    public static final String FILENAME = "res/images/shot.png";
    // bullet information
    private final Image image;
    private Point pos;
    private double directionX;
    private double directionY;
    private boolean shot;

    /**
     * create bullet object
     */
    public Bullet() {
        this.image = new Image(FILENAME);
        this.pos = new Point(ZERO_VECTOR.x, ZERO_VECTOR.y);
        this.shot = false;
    }

    /**
     * @return position of the bullet
     */
    public Point getPos(){
        return this.pos;
    }

    /**
     * set position of the bullet
     */
    public void setPos(Point pos){
        this.pos = pos;
    }

    /**
     * set whether the bullet has been shot
     */
    public void setShot(boolean shotStatus) {
        this.shot = shotStatus;
    }

    /**
     * @return the status of the bullet
     */
    public boolean isShot() {
        return shot;
    }

    /**
     * point bullet to destination
     * @param dest position of the destination object
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
     * change the state of bullet during the game.
     * @param tomb the Shadow Treasure game
     */
    public void update(ShadowTreasure tomb) {
        // point bullet to nearest zombie and move one step towards it
        pointTo(tomb.getZombie().getPos());
        this.pos = new Point(this.pos.x+STEP_SIZE*this.directionX, this.pos.y+STEP_SIZE*this.directionY);

        // remove zombie from the game if bullet hits it
        if (tomb.getZombie().hit(this)) {
            shot = false;
            tomb.getZombies().remove(tomb.getZombie());
            tomb.printInfo(this.getPos().x, this.getPos().y);
            tomb.writeFile(this.getPos().x, this.getPos().y);
        }
    }

    /**
     * draw the bullet graphics if player has shot it
     */
    public void render() {
        if (shot) {
            image.drawFromTopLeft(pos.x, pos.y);
        }
    }
}
