package Entity;

import java.awt.image.BufferedImage;

/**
 * this is a class for some common variables of all the entities
 */
public class Entity {
    public int x, y;
    public Boolean active;
    public int speed;
    public int entitySize;
    public BufferedImage up, down, left, right, death;
    public String direction;
    public int health;
}
