// some codes are inspired from provided project 1 solution.
import bagel.Image;

/**
 * a stationary game object: Sandwich
 */
public class Sandwich extends StationaryGameObject {

    private final Image image = new Image("res/images/sandwich.png");

    /**
     * default constructor
     */
    public Sandwich() {
        super();
    }

    /**
     * constructor to create sandwich object
     * @param x x coordinate of object
     * @param y y coordinate of object
     */
    public Sandwich(double x, double y){
        super(x, y);
    }

    /**
     * render image at certain position if object is visible
     */
    public void draw() {
        image.drawFromTopLeft(this.getPos().x, this.getPos().y);
    }
}
