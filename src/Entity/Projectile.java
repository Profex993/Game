package Entity;


import Main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Projectile extends Entity {

    public BufferedImage image;
    private final boolean fromPlayer;
    public int projectileSize = 16;
    Player player;
    Panel panel;

    public Projectile(int x, int y, String direction, boolean active, boolean fromPlayer, Player player, Panel panel) {
        this.x = x;
        this.y = y + 15;
        this.direction = direction;
        this.active = active;
        this.speed = 16;
        this.fromPlayer = fromPlayer;
        this.player = player;
        this.panel = panel;
        getImage();
    }

    public void getImage() {
        try {
            down = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("prefab/bullet.png")));
            up = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("prefab/bulletUp.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (direction) {
            case "up" -> image = up;
            case "down" -> image = down;
        }
    }

    public void update() {
        if (direction.equals("down")) {
            y += speed;
        } else if (direction.equals("up")) {
            y -= speed;
        }

        if (y <= 5 || y >= 710) {
            panel.projectileList.remove(this);
        } else if ((y >= player.y && y < player.y + 60) && (x >= player.x && x < player.x + 60)) {
            if (!fromPlayer) {
                int damageEnemy = 15;
                player.health -= damageEnemy;
                panel.projectileList.remove(this);
            }

        } else if (fromPlayer) {
            for (int i = 0; i < panel.enemyList.size(); i++) {

                if ((y >= panel.enemyList.get(i).y && y < panel.enemyList.get(i).y + 60) && (x >= panel.enemyList.get(i).x && x < panel.enemyList.get(i).x + 60)) {
                    int damagePlayer = 34;
                    panel.enemyList.get(i).health -= damagePlayer;
                    panel.projectileList.remove(this);
                }
            }
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, projectileSize, projectileSize, null);
    }
}