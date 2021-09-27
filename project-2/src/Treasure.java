// some codes are inspired from provided project 1 solution.
import bagel.Image;

/**
 * a stationary game object: Treasure
 */
public class Treasure extends StationaryGameObject {

    private final Image image = new Image("res/images/treasure.png");

    /**
     * constructor to create treasure object
     * @param x x coordinate of object
     * @param y y coordinate of object
     */
    public Treasure(double x, double y){
        super(x, y);
    }

    /**
     * render image at certain position
     */
    public void draw() {
        image.drawFromTopLeft(this.getPos().x, this.getPos().y);
    }
}
