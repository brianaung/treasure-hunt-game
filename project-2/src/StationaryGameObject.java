// some codes are inspired from provided project 1 solution.
import bagel.util.Point;
import bagel.util.Vector2;

/**
 * a stationary game object
 */
public abstract class StationaryGameObject {

    private static final Vector2 ZERO_VECTOR = new Vector2(0,0);
    private Point pos;

    /**
     * default constructor
     */
    public StationaryGameObject() {
        this.pos = new Point(ZERO_VECTOR.x, ZERO_VECTOR.y);
    }

    /**
     * construct a stationary game object
     * @param x x coordinate of object
     * @param y y coordinate of object
     */
    public StationaryGameObject(double x, double y) {
        this.pos = new Point(x, y);
    }

    /**
     *
     * @return the position of the object
     */
    public Point getPos() {
        return pos;
    }

    /**
     * renders object
     */
    public abstract void draw();

    /**
     * check if the object has met the player
     * @param player movable player object
     * @return object and player has met or not
     */
    public boolean meets(Player player) {
        boolean hasMet = false;
        double distanceToPlayer = player.getPos().distanceTo(pos);
        if (distanceToPlayer < ShadowTreasure.ClOSENESS) {
            hasMet = true;
        }
        return hasMet;
    }
}
