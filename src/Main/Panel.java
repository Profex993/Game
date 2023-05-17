package Main;

import Entity.Enemy;
import Entity.Player;
import Entity.Projectile;
import Entity.Strike;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

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
    public ArrayList<Strike> strikeList = new ArrayList<>();
    public ArrayList<Projectile> projectileList = new ArrayList<>();
    public ArrayList<Enemy> enemyList = new ArrayList<>();

    public Panel() {
        this.setPreferredSize(new Dimension(960, 720));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();

        //addEnemy();
        soundM.play(0);
    }

    public void addEnemy() {
        if (player.active && enemyList.size() < 6) {
            enemyList.add(new Enemy(rnd.nextInt(960), rnd.nextInt(250) + 400, player.x, player.y, player, this, soundM));
        }
    }

    public void addStrike() {
        if(player.active){
            strikeList.add(new Strike(this, player, soundM));
        }
    }

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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileM.drawTiles(g2);
        g2.setColor(Color.black);
        g2.drawString("fps: " + fps, 5, 20);
        if(strikeList.size() > 0){
            strikeList.get(0).draw(g2);
        }
        player.draw(g2);
        for (int i = 0; i < projectileList.size(); i++) {
            Projectile projectile = projectileList.get(i);
            projectile.draw(g2);
        }
        for (int i = 0; i < enemyList.size(); i++) {
            Enemy enemy = enemyList.get(i);
            enemy.draw(g2);
        }
        if (!player.active) {
            Font font2 = new Font("arial", Font.BOLD, 100);
            g2.setFont(font2);
            g2.drawString("You died!", 220, 420);
        }
        g2.dispose();
    }
}