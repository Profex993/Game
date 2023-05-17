package Entity;

import Interfaces.DrawUpdate;
import Main.Panel;
import Main.SoundManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Strike implements DrawUpdate {

    public BufferedImage image1 = null;
    public BufferedImage image2 = null;
    public int timeout = 2;
    public int damage = 100;
    int size = 100;
    boolean stage = false;
    boolean soundActive = true;
    boolean soundActive2 = true;
    int playerX;
    int playerY;
    int gameTick;
    Panel panel;
    Player player;
    SoundManager soundM;

    public Strike(Panel panel, Player player, SoundManager soundM) {
        try {
            this.image2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("prefab/explosion.png")));
            this.image1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("prefab/shadow.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.panel = panel;
        this.player = player;
        this.soundM = soundM;
        this.playerX = player.x - 24;
        this.playerY = player.y - 24;
        this.gameTick = panel.frames;
    }

    public void update() {
        if (gameTick + timeout == panel.frames && soundActive) {
            stage = true;
            soundM.play(3);
            soundActive = false;
            if ((player.x >= playerX && playerX + 50 > player.x) && (player.y >= playerY && playerY + 50 > player.y) && stage) {
                player.health =- damage;
            }
        } else if (soundActive2) {
            soundM.play(4);
            soundActive2 = false;
        }


    }

    public void draw(Graphics2D g2) {
        if (!stage) {
            g2.drawImage(image1, playerX, playerY, size, size, null);
        } else {
            g2.drawImage(image2, playerX, playerY, size, size, null);
            if(size > 0){
                size --;
            } else{
                size = 0;
                panel.strikeList.remove(this);
            }
        }
    }
}
