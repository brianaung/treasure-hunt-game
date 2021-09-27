import bagel.*;
import bagel.util.*;

public class Zombie {
    private double x;
    private double y;

    private final Image zombieImage;

    public Zombie() {
        zombieImage = new Image("res/images/zombie.png");
        x = 0;
        y = 0;
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        zombieImage.draw(x, y);
    }
}
