import bagel.util.Point;

public interface Movable {
    /**
     * point object to destination object
     */
    void pointTo(Point dest);

    /**
     * normalize direction
     */
    void normalizeDir();

    /**
     * update the status of the object in the game
     */
    void update(ShadowTreasure tomb);
}
