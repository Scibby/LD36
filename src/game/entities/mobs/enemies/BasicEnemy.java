package game.entities.mobs.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import scibby.entities.Mob;
import scibby.level.Level;
import scibby.util.Vector2i;

public class BasicEnemy extends Mob{

	private int speed = 3;

	public BasicEnemy(int x, int y, int width, int height, BufferedImage image){
		super(x, y, width, height, image);
	}

	@Override
	public void tick(){
		super.tick();

		double xa = 0, ya = 0;

		ArrayList<Mob> mobs = Level.getCurrentLevel().getMobsInRadius(this, 256);

		Mob player = Level.getCurrentLevel().getPlayer();

		if(mobs.contains(player)){
			int distance = Vector2i.getDistance(new Vector2i((int) player.x, (int) player.y), new Vector2i((int) x, (int) y));
			if(distance > speed){
				if(player.x > x) xa += speed;
				if(player.x < x) xa -= speed;
				if(player.y > y) ya += speed;
				if(player.y < y) ya -= speed;
			}else{
				if(player.x > x) xa += distance;
				if(player.x < x) xa -= distance;
				if(player.y > y) ya += distance;
				if(player.y < y) ya -= distance;
			}
			
		}
		move(xa, ya);
	}

	@Override
	public void render(Graphics2D g){
		super.render(g);
		g.setColor(Color.BLUE);
		g.fillRect((int) x, (int) y, width, height);
	}

}
