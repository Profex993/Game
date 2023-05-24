package Main;

import Entity.Enemy;
import Entity.Player;
import Entity.Projectile;
import Entity.Strike;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * this is a class that is the core of the game. It makes the game to loop
 */
public class Panel extends JPanel implements Runnable {

    Random rnd = new Random();
    public int tileSize = 48;
    int fps = 0;
    public int frames = 0;
    public int tick = 0;
    int enemySpawn = 0;
    int enemySpawnPoint = rnd.nextInt(7) + 1;
    int strikeSpawnPoint = rnd.nextInt(10);
    int strikeSpawn;
    Thread thread;
    KeyHandler key = new KeyHandler();
    SoundManager soundM = new SoundManager();
    Player player = new Player(this, key, soundM);
    TileManager tileM = new TileManager(this);

    //Arraylist for updating and drawing entities
    public ArrayList<Strike> strikeList = new ArrayList<>();
    public ArrayList<Projectile> projectileList = new ArrayList<>();
    public ArrayList<Enemy> enemyList = new ArrayList<>();

    /**
     * Constructor for the Panel class
     */
    public Panel() {
        this.setPreferredSize(new Dimension(960, 720));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);
    }

    /**
     * this method starts the thread which makes the game loop
     */
    public void startThread() {
        thread = new Thread(this);
        thread.start();

        //play the soundtrack
        soundM.loop(0);
    }

    /**
     * this method adds enemies to the game after random interval.
     * Adds enemies only if there are less than 6 enemies already
     */
    public void addEnemy() {
        if (player.active && enemyList.size() < 6) {
            enemyList.add(new Enemy(rnd.nextInt(960), rnd.nextInt(250) + 400, player.x, player.y, player, this, soundM));
        }
    }

    /**
     * this method adds an airstrike to the game after a random interval of time. The airstrike will spawn on the player location
     */
    public void addStrike() {
        if(player.active){
            strikeList.add(new Strike(this, player, soundM));
        }
    }

    /**
     * this method is the core of the game. it makes the game to loop and limits the number of fps to 60.
     * Also, once per second, some variables get increased to spawn enemies and airstrikes randomly
     */
    @Override
    public void run() {
        double interval = 16666666;
        double delta = 0;
        long last = System.nanoTime();
        long time;
        long timer = 0;
        int fpsCounter = 0;
        while (thread != null) {

            time = System.nanoTime();
            delta += (time - last) / interval;
            timer += (time - last);
            last = time;
            if (delta >= 1) {
                update();
                repaint();
                tick++;
                delta--;
                fpsCounter++;
            }
            if (timer >= 1000000000) {
                enemySpawn++;
                strikeSpawn++;
                if (enemySpawn == enemySpawnPoint) {
                    addEnemy();
                    enemySpawn = 0;
                    enemySpawnPoint = rnd.nextInt(7) + 1;
                }
                if(strikeSpawn == strikeSpawnPoint){
                    addStrike();
                    strikeSpawn = 0;
                    strikeSpawnPoint = rnd.nextInt(7)+3;
                }
                fps = fpsCounter;
                frames++;
                fpsCounter = 0;
                timer = 0;
            }
        }
    }

    /**
     * this method makes all entities to update
     */
    public void update() {
        if (player.active) {
            player.update();
            for (int i = 0; i < projectileList.size(); i++) {
                projectileList.get(i).update();
            }
            for (int i = 0; i < enemyList.size(); i++) {
                enemyList.get(i).update();
            }
            if(strikeList.size() > 0){
                strikeList.get(0).update();
            }
        }
    }

    /**
     * this method draws all the components of the game.
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //draw the game map
        tileM.drawTiles(g2);
        g2.setColor(Color.black);
        //display fps
        g2.drawString("fps: " + fps, 5, 20);
        //draw airstrikes if there are any
        if(strikeList.size() > 0){
            strikeList.get(0).draw(g2);
        }
        //draw the player
        player.draw(g2);
        //draw the projectiles
        for (int i = 0; i < projectileList.size(); i++) {
            Projectile projectile = projectileList.get(i);
            projectile.draw(g2);
        }
        //draw the enemies
        for (int i = 0; i < enemyList.size(); i++) {
            Enemy enemy = enemyList.get(i);
            enemy.draw(g2);
        }
        //if player dies, this will display the "you died" text
        if (!player.active) {
            Font font2 = new Font("arial", Font.BOLD, 100);
            g2.setFont(font2);
            g2.drawString("You died!", 220, 420);
        }
        g2.dispose();
    }
}