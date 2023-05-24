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

/**
 * this class adds enemies
 */
public class Enemy extends Entity implements DrawUpdate {
    int playerX;
    int playerY;
    public BufferedImage image = null;
    Random rnd = new Random();
    Player player;
    Panel panel;
    SoundManager soundM;

    /**
     * constructor for Enemy class
     * @param x position of the enemy
     * @param y position of the enemy
     * @param pX player position x
     * @param pY player position y
     * @param player Player object
     * @param panel Panel object
     * @param soundM SoundManager object
     */
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

    /**
     * this method reads the texture of the Enemy
     */
    public void getImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/soldierUp.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method makes the Enemy to update
     */
    public void update() {
        // some basic AI/movement
        if (x != playerX) {
            int temp = playerX - x;
            if (temp < 0) {
                x -= speed;
            } else {
                x += speed;
            }
            //I did not just use the term AI 💀
        } else {
            //when the enemy reaches location that was set before, this method is called to set new directions
            setDir();
        }
        //this makes the enemy to shoot at the player
        if (x == player.x) {
            panel.projectileList.add(new Projectile(x + 40, y, "up", true, false, player, panel));
            soundM.play(6);
        }
        //when the enemy dies, this removes it from enemy Arraylist in Panel class
        if (health <= 0) {
            panel.enemyList.remove(this);
        }
    }

    /**
     * this method sets new direction for the enemy,
     * new direction is player position
     */
    public void setDir() {
        boolean opt = rnd.nextBoolean();
        if (opt) {
            playerX = player.x + 24;
        } else {
            playerX = player.x - 24;
        }

    }

    /**
     * method for drawing the enemy
     * @param g2 Graphic 2D
     */
    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, entitySize, entitySize, null);
    }
}