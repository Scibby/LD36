package game.entities.projectiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.entities.mobs.Player;
import scibby.entities.Mob;
import scibby.entities.Particle;
import scibby.entities.Projectile;
import scibby.level.Level;

public class AnkhProjectile extends Projectile{

	public static final int RATE_OF_FIRE = 90;

	private int time = 0;
	
	public AnkhProjectile(double x, double y, int width, int height, double angle, int range, Mob shooter, BufferedImage image){
		super(x, y, width, height, angle, range, shooter, image);
	}

	@Override
	public void tick(){
		super.tick();
		
		time++;
		if(time % 2 == 0){
			Level.getCurrentLevel().add(new Particle(x + width / 2, y + height / 2, 5, 5, 30, new Color(0xffaa00), 1));
		}

		if(isColliding(nx, ny)){
			Level.getCurrentLevel().add(new Particle(x, y, 5, 5, 60, new Color(0xffaa00), 20));
		}

		ArrayList<Mob> close = Level.getCurrentLevel().getMobsInRadius(this, 32);
		if(!close.isEmpty()){
			for(int i = 0; i < close.size(); i++){
				if(close.get(i).equals(shooter)){
					continue;
				}
				if(close.get(i) instanceof Player){
					Player player = (Player) close.get(i);
					if(player.invinsible < 0){
						player.health--;
					}
					remove();
					Level.getCurrentLevel().add(new Particle(x, y, 6, 6, 20, new Color(0x333333), 20));
				}
			}
		}
	}
	
	@Override
	public void render(Graphics2D g){
		super.render(g);

	}

}
