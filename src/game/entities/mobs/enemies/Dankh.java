package game.entities.mobs.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import game.Images;
import game.entities.mobs.Player;
import game.entities.projectiles.AnkhProjectile;
import scibby.entities.Mob;
import scibby.graphics.Animation;
import scibby.level.Level;
import scibby.util.AudioPlayer;

public class Dankh extends Mob{

	private Animation dankhAnim = new Animation(5, Images.danhk);

	private double xa = 0, ya = 0;
	private int time = 0;
	private int speed = 2;
	private int rate = 0;
	
	private AudioPlayer shootSFX = new AudioPlayer("ankhShoot");
	
	public Dankh(int x, int y, int width, int height, BufferedImage image){
		super(x, y, width, height, image);
		health = 40;
	}

	@Override
	public void tick(){
		super.tick();
		dankhAnim.runAnimation();

		time++;

		ArrayList<Mob> shootMobs = Level.getCurrentLevel().getMobsInRadius(this, 256);
		ArrayList<Mob> followMobs = Level.getCurrentLevel().getMobsInRadius(this, 512);
		Player player;
		if(Level.getCurrentLevel().getPlayer() instanceof Player){
			player = (Player) Level.getCurrentLevel().getPlayer();
			
			
			if(followMobs.contains(player)){
				xa = ya = 0;
				if((int) player.x + 2 > x) xa += speed;
				if((int) player.x < x + 2) xa -= speed;
				if((int) player.y + 2 > y) ya += speed;
				if((int) player.y < y + 2) ya -= speed;
			}else{
				Random rand = new Random();
				if(time % (rand.nextInt(50) + 30) == 0){

					xa = (rand.nextInt(3) - 1) * speed;
					ya = (rand.nextInt(3) - 1) * speed;
					if(rand.nextInt(4) == 0){
						xa = 0;
						ya = 0;
					}
				}
			}
			
			if(shootMobs.contains(player)){
				xa = ya = 0;
				updateShooting(player);
				
			}
		}
		
		rate--;
		
		move(xa, ya);

	}

	private void updateShooting(Player player){

		double dx = player.x + player.width / 2 - (x + width / 2);
		double dy = player.y + player.height / 2 - (y + height / 2);

		double dir = Math.atan2(dy, dx);
		shoot(dir);
	}
	
	private void shoot(double angle){
		//System.out.println(angle);
		if(rate <= 0){
			new AnkhProjectile(x + width / 2, y + height / 2, 8, 8, angle, 2000, this, Images.ankhProjectile);
			shootSFX.play();
			rate = AnkhProjectile.RATE_OF_FIRE;
		}
	}
	
	@Override
	public void render(Graphics2D g){
		dankhAnim.drawAnimation(g, (int) x, (int) y, width, height);
	}

}
