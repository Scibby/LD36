package scibby.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import scibby.level.Level;

public class Particle extends Entity{

	private double xa, ya, za;
	private double z;

	private Random random;

	private int life;

	public Particle(double x, double y, int width, int height, int life){
		super(x, y, width, height);
		random = new Random();
		this.life = life + (random.nextInt(20) - 10);
		xa = random.nextGaussian();
		ya = random.nextGaussian();
		za = 2;
	}

	public Particle(double x, double y, int width, int height, int life, int amount){
		this(x, y, width, height, life);
		for(int i = 0; i < amount - 1; i++){
			Level.getCurrentLevel().add(new Particle(x, y, width, height, life));
		}
	}

	@Override
	public void tick(){
		za -= 0.1;
		
		if(z <= 0){
			z = 0;
			za *= -0.8;
			xa *= 0.9;
			ya *= 0.9;
		}
		
		x += xa;
		y += ya;
		z += za;
		
		life--;
		if(life <= 0){
			remove();
		}
		
		if(isColliding(xa, ya)){
			this.xa *= -0.8;
			this.ya *= -0.8;
			this.za *= -0.8;
		}
	}

	@Override
	public void render(Graphics2D g){
		g.setColor(Color.GRAY);
		g.fillRect((int) x, (int) ((int) y - z), width, height);
	}

	protected boolean isColliding(double xa, double ya){
		for(int c = 0; c < 4; c++){
			double xt = ((x + xa) - c % 2) / Level.getCurrentLevel().getTileSize();
			double yt = ((y + ya) - c / 2) / Level.getCurrentLevel().getTileSize();
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if(c % 2 == 0) ix = (int) Math.floor(xt);
			if(c / 2 == 0) iy = (int) Math.floor(yt);
			Tile tile = Level.getCurrentLevel().getTile(ix, iy);
			if(tile != null) return tile.isSolid();
		}
		return false;
	}
}
