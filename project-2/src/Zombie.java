// some codes are inspired from provided project 1 solution.
import bagel.Image;

/**
 * a stationary game object: Zombie
 */
public class Zombie extends StationaryGameObject {

    private final Image image = new Image("res/images/zombie.png");

    /**
     * default constructor
     */
    public Zombie() {
        super();
    }

    /**
     * constructor to create zombie object
     * @param x x coordinate of object
     * @param y y coordinate of object
     */
    public Zombie(double x, double y) {
        super(x, y);
    }

    /**
     * render image at certain position if object is visible
     */
    public void draw() {
        image.drawFromTopLeft(this.getPos().x, this.getPos().y);
    }

    /**
     * @param player movable player object
     * @return if the player is in shootiong range of the zombie
     */
    @Override
    public boolean meets(Player player) {
        boolean hasMet = false;
        double distanceToPlayer = player.getPos().distanceTo(this.getPos());
        if (distanceToPlayer < ShadowTreasure.SHOOTING_RANGE) {
            hasMet = true;
        }
        return hasMet;
    }

    /**
     * @param bullet movable bullet object
     * @return whether the zombie has been hit by the bullet
     */
    public boolean hit(Bullet bullet) {
        boolean hasHit = false;
        double distanceToPlayer = bullet.getPos().distanceTo(this.getPos());
        if (distanceToPlayer < ShadowTreasure.DEATH_RANGE) {
            hasHit = true;
        }
        return hasHit;
    }
}
