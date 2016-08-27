package scibby.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import scibby.level.Level;

public abstract class Projectile extends Entity{

	protected int xOrigin, yOrigin;
	protected int speed = 10;
	protected Mob shooter;
	protected double nx, ny;
	protected double angle;
	protected int range;
	protected BufferedImage image;

	public Projectile(double x, double y, int width, int height, double angle, int range, Mob shooter, BufferedImage image){
		super(x, y, width, height);
		xOrigin = (int) x;
		yOrigin = (int) y;
		this.shooter = shooter;
		this.angle = angle;
		this.range = range;
		this.image = image;

		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		shooter.projectiles.add(this);
	}

	@Override
	public void tick(){
		if(isColliding(nx, ny)){
			remove();
		}

		move(nx, ny);
		if(outOfRange()) remove();
	}

	protected boolean outOfRange(){
		double dist;
		dist = Math.sqrt(Math.abs((x - xOrigin) * (x - xOrigin) + (y - yOrigin) * (y - yOrigin)));
		if(dist > range) return true;
		return false;
	}

	protected void move(double xa, double ya){
		x += xa;
		y += ya;
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

	@Override
	public void render(Graphics2D g){
		if(image != null){
			g.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
		}
	}

}
