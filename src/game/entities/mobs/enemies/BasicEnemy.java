package game.entities.mobs.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Images;
import scibby.entities.Mob;
import scibby.graphics.Animation;
import scibby.level.Level;
import scibby.util.Vector2i;

public class BasicEnemy extends Mob{

	private int speed = 3;
	private double xa = 0, ya = 0;

	private Animation walkUp = new Animation(speed, Images.basicEnemyWalkUp);
	private Animation walkDown = new Animation(speed, Images.basicEnemyWalkDown);
	private Animation walkLeft = new Animation(5, Images.basicEnemyWalkLeft);
	private Animation walkRight = new Animation(5, Images.basicEnemyWalkRight);

	public BasicEnemy(int x, int y, int width, int height, BufferedImage image){
		super(x, y, width, height, image);
	}

	@Override
	public void tick(){
		super.tick();

		xa = 0;
		ya = 0;

		ArrayList<Mob> mobs = Level.getCurrentLevel().getMobsInRadius(this, 256);

		Mob player = Level.getCurrentLevel().getPlayer();

		if(mobs.contains(player)){
			int distance = (int) Vector2i.getDistance(new Vector2i((int) player.x, (int) player.y),
					new Vector2i((int) x, (int) y));
			if(distance > speed){
				if(player.x + 2 > x) xa += speed;
				if(player.x < x + 2) xa -= speed;
				if(player.y + 2 > y) ya += speed;
				if(player.y < y + 2) ya -= speed;
			}else{
				if(player.x > x) xa += distance;
				if(player.x < x) xa -= distance;
				if(player.y > y) ya += distance;
				if(player.y < y) ya -= distance;
			}
			
			if(xa > 0){
				walkRight.runAnimation();
			}else if(xa < 0){
				walkLeft.runAnimation();
			}else if(ya > 0){
				walkDown.runAnimation();
			}else if(ya < 0){
				walkUp.runAnimation();
			}

		}

		if(xa < 1 && xa > 0) xa = 0;
		if(xa > -1 && xa < 0) xa = 0;
		if(ya < 1 && ya > 0) ya = 0;
		if(ya < 1 && ya > 0) ya = 0;

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
