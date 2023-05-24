package Entity;

import Interfaces.DrawUpdate;
import Main.KeyHandler;
import Main.Panel;
import Main.SoundManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * player Class
 */
public class Player extends Entity implements DrawUpdate {
    Panel panel;
    KeyHandler key;
    SoundManager soundM;
    boolean projectileAdded = false;

    /**
     * constructor for this class
     * @param panel Panel obj
     * @param key KeyHandler obj
     * @param soundM SoundManager obj
     */
    public Player(Panel panel, KeyHandler key, SoundManager soundM) {
        this.panel = panel;
        this.key = key;
        this.soundM = soundM;
        this.entitySize = 64;
        this.health = 100;
        this.active = true;
        this.speed = 16;
        x = 400;
        y = 100;
        speed = 8;
        direction = "down";
        getImage();
    }

    /**
     * method for getting the texture of the player
     */
    public void getImage() {
        try {
            up = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/soldierUp.png")));
            down = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/soldierDown.png")));
            left = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/soldierDown.png")));
            right = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/soldierDown.png")));
            death = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/death.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method for updating the player
     */
    public void update() {
        //movement
        if (key.up) {
            direction = "up";
            if (y < 0){
                y = 200;
            }
            y -= speed;
        } else if (key.down) {
            direction = "down";
            if (y > 360){
                health = 0;
            }
            y += speed;
        } else if (key.left) {
            direction = "left";
            if (x != 0 && x != 960) {
                x -= speed;
            } else {
                x = 480;
            }
        } else if (key.right) {
            direction = "right";
            if (x != 0 && x != 960) {
                x += speed;
            } else {
                x = 480;
            }
        //shooting
        } else if (key.shot && !projectileAdded && !direction.equals("up")) {
            panel.projectileList.add(new Projectile(x, y, "down", true, true, this, panel));
            soundM.play(5);
            projectileAdded = true;
        } else if (!key.shot && projectileAdded) {
            projectileAdded = false;
        }
        //death
        if (health <= 0) {
            soundM.play(2);
            active = false;
            health = 0;
        }
    }


    /**
     * draw method for player
     * @param g2 Graphic 2D
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case "up" -> up;
            case "down" -> down;
            case "left" -> left;
            case "right" -> right;
            default -> null;
        };
        if (!active) {
            image = death;
        }
        g2.setColor(Color.red);
        Font font = new Font("arial", Font.BOLD, 35);
        g2.setFont(font);
        g2.drawString("+" + health, 10, 700);
        g2.drawImage(image, x, y, entitySize, entitySize, null);
    }
}