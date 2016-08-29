package game.entities.mobs.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import game.Images;
import game.entities.mobs.Player;
import scibby.entities.Mob;
import scibby.graphics.Animation;
import scibby.level.Level;

public class BasicEnemy extends Mob{

	private int speed = 3;
	private double xa = 0, ya = 0;
	private int time = 1;

	private Animation walkUp = new Animation(speed, Images.basicEnemyWalkUp);
	private Animation walkDown = new Animation(speed, Images.basicEnemyWalkDown);
	private Animation walkLeft = new Animation(5, Images.basicEnemyWalkLeft);
	private Animation walkRight = new Animation(5, Images.basicEnemyWalkRight);

	
	
	public BasicEnemy(int x, int y, int width, int height, BufferedImage image){
		super(x, y, width, height, image);
		health = 40;
	}

	@Override
	public void tick(){
		super.tick();

		/*
		 * xa = 0; ya = 0;
		 */

		time++;

		ArrayList<Mob> mobs = Level.getCurrentLevel().getMobsInRadius(this, 256);

		Player player = (Player) Level.getCurrentLevel().getPlayer();

		if(mobs.contains(player)){

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

		if(xa > 0){
			walkRight.runAnimation();
		}else if(xa < 0){
			walkLeft.runAnimation();
		}else if(ya > 0){
			walkDown.runAnimation();
		}else if(ya < 0){
			walkUp.runAnimation();
		}else{
			if(mobs.contains(player)){
				if(player.invinsible < 0){
					player.health--;
					player.invinsible = 10;
				}

			}
		}

		move(xa, ya);
	}

	@Override
	public void render(Graphics2D g){
		super.render(g);
		if(xa > 0){
			walkRight.drawAnimation(g, (int) x, (int) y, width, height);
		}else if(xa < 0){
			walkLeft.drawAnimation(g, (int) x, (int) y, width, height);
		}else if(ya > 0){
			walkDown.drawAnimation(g, (int) x, (int) y, width, height);
		}else if(ya < 0){
			walkUp.drawAnimation(g, (int) x, (int) y, width, height);
		}else{
			g.drawImage(Images.basicEnemyWalkDown[0], (int) x, (int) y, width, height, null);
		}
	}

}
