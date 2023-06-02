# Game - readme
There is a bug that breaks the game, and it has to be restarted.
Weird thing is that it is happening only on my laptop when I have my integrated graphical card turned off.
SMH

<img src="https://i.guim.co.uk/img/media/3aab8a0699616ac94346c05f667b40844e46322f/0_123_5616_3432/master/5616.jpg?width=700&quality=85&auto=format&fit=max&s=a476da702aff265ce6f586be1412b1e1" width="200" height="100" alt="hide the pain Harold">

original constructor of Enemy class

public Enemy(int x, int y, int pX, int pY, Player player, Panel panel, SoundManager soundM){
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
    }⠀⠀