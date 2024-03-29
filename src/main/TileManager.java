package main;

import gameObj.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {
    private final ArrayList<Tile> tiles = new ArrayList<>();
    private int col;
    private int row;
    final Panel panel;
    BufferedImage tile1 = null;
    BufferedImage tile2 = null;

    public TileManager(Panel panel) {
        this.panel = panel;
        try {
            tile1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/tile1.png")));
            tile2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/tile2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addTiles();

    }

    public void addTiles() {
        for (int i = 140; i > 0; i--) {
            tiles.add(new Tile(tile1));
        }
        for (int i = 40; i > 0; i--) {
            tiles.add(new Tile(tile2));
        }
        for (int i = 140; i > 0; i--) {
            tiles.add(new Tile(tile1));
        }

    }

    public void drawTiles(Graphics2D g2) {
        for (int i = tiles.size() - 1; i >= 0; i--) {
            int x = col * panel.tileSize;
            int y = row * panel.tileSize;
            Tile tile = tiles.get(i);
            g2.drawImage(tile.image(), x, y, panel.tileSize, panel.tileSize, null);
            col++;
            if (col == 20) {
                col = 0;
                row++;
            }
        }
        col = 0;
        row = 0;
    }
}