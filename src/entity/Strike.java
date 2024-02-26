package entity;

import interfaces.DrawUpdate;
import main.Panel;
import main.SoundManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * this class is for airstrikes
 */
public class Strike implements DrawUpdate {

    protected BufferedImage image1 = null;
    protected BufferedImage image2 = null;
    private int size = 100;
    private boolean stage = false;
    private boolean soundActive = true;
    private boolean soundActive2 = true;
    private final int playerX;
    private final int playerY;
    private final int gameTick;
    private final Panel panel;
    private final Player player;
    private final SoundManager soundM;

    /**
     * constructor for strike
     * @param panel object of the Panel class
     * @param player object of the Player class
     * @param soundM object of the SoundManager class
     */
    public Strike(Panel panel, Player player, SoundManager soundM) {
        this.panel = panel;
        this.player = player;
        this.soundM = soundM;
        this.playerX = player.x - 24;
        this.playerY = player.y - 24;
        this.gameTick = panel.frames;
        getImage();
    }

    /**
     * this method reads teh textures of the airstrike
     */
    private void getImage(){
        try {
            this.image2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("prefab/explosion.png")));
            this.image1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("prefab/shadow.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method updates the airstrike
     */
    public void update() {
        int timeout = 2;
        if (gameTick + timeout == panel.frames && soundActive) {
            stage = true;
            soundM.play(3);
            soundActive = false;
            if ((player.x >= playerX && playerX + 50 > player.x) && (player.y >= playerY && playerY + 50 > player.y) && stage) {
                int damage = 100;
                player.health =-damage;
            }
        } else if (soundActive2) {
            soundM.play(4);
            soundActive2 = false;
        }


    }

    /**
     * draw method to display the airstrike
     * @param g2 2D graphics
     */
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
