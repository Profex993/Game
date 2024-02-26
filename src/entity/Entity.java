package entity;

import java.awt.image.BufferedImage;

/**
 * this is a class for some common variables of all the entities
 */
public class Entity {
    public int x, y;
    public Boolean active;
    protected int speed;
    protected int entitySize;
    protected BufferedImage up, down, left, right, death;
    protected String direction;
    protected int health;
}
