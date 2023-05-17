package Entity;

import Interfaces.DrawUpdate;
import Main.Panel;
import Main.SoundManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Enemy extends Entity implements DrawUpdate {
    int playerX;
    int playerY;
    public BufferedImage image = null;
    Random rnd = new Random();
    Player player;
    Panel panel;
    SoundManager soundM;

    public Enemy(int x, int y, int pX, int pY, Player player, Panel panel, SoundManager soundM) {
        this.entitySize = 64;
        this.direction = "up";
        this.x = x;
        this.y = y;
        this.health = 100;
        this.playerX = pX;
        this.playerY = pY;
        this.player = player;
        this.panel = panel;
        this.soundM = soundM;
        this.speed = 2;
        getImage();
    }

    public void getImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/soldierUp.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (x != playerX) {
            int temp = playerX - x;
            if (temp < 0) {
                x -= speed;
            } else {
                x += speed;
            }
        } else {
            setDir();
        }
        if (x == player.x) {
            panel.projectileList.add(new Projectile(x + 40, y, "up", true, false, player, panel));
            soundM.play(6);
        }
        if (health <= 0) {
            panel.enemyList.remove(this);
        }
    }

    public void setDir() {
        boolean opt = rnd.nextBoolean();
        if (opt) {
            playerX = player.x + 24;
        } else {
            playerX = player.x - 24;
        }

    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, entitySize, entitySize, null);
    }
}