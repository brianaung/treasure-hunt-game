import bagel.Image;
import bagel.util.*;

public class Sandwich {
    private double x;
    private double y;
    public boolean isEaten;

    private final Image sandwichImage;

    public Sandwich() {
        sandwichImage = new Image("res/images/sandwich.png");
        x = 0;
        y = 0;
        isEaten = false;
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public boolean getIsEaten() {
        return isEaten;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void markAsEaten() {
        this.isEaten = true;
    }

    public void update() {
        // only render sandwich image if it is not eaten
        if (!isEaten) {
            sandwichImage.draw(x, y);
        }
    }
}
