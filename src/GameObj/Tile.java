package GameObj;

import java.awt.image.BufferedImage;

/**
 * Tile class, the gamemap is made out of objects of this class
 */
public class Tile {
    public BufferedImage image;

    /**
     * this is a constructor of Tile class
     * @param image texture of the tile
     */
    public Tile(BufferedImage image) {
        this.image = image;
    }
}
