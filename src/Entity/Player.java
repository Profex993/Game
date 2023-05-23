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

public class Player extends Entity implements DrawUpdate {
    Panel panel;
    KeyHandler key;
    SoundManager soundM;
    boolean projectileAdded = false;

    public Player(Panel panel, KeyHandler key, SoundManager soundM) {
        this.panel = panel;
        this.key = key;
        this.soundM = soundM;
        this.entitySize = 64;
        this.health = 100;
        this.active = true;
        this.speed = 16;
        setDefault();
        getPlayerImage();
    }

    public void setDefault() {
        x = 400;
        y = 100;
        speed = 8;
        direction = "down";
    }

    public void getPlayerImage() {
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

    public void update() {
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
        } else if (key.shot && !projectileAdded && !direction.equals("up")) {
            panel.projectileList.add(new Projectile(x, y, "down", true, true, this, panel));
            soundM.play(5);
            projectileAdded = true;
        } else if (!key.shot && projectileAdded) {
            projectileAdded = false;
        }

        if (health <= 0) {
            soundM.play(2);
            active = false;
            health = 0;
        }
    }


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