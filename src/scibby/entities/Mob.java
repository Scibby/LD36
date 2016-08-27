package scibby.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import scibby.events.Event;
import scibby.level.Level;

public abstract class Mob extends Entity{

	protected BufferedImage image;

	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public Mob(int x, int y, int width, int height, BufferedImage image){
		super(x, y, width, height);
		this.image = image;
	}

	@Override
	public void tick(){
		for(Projectile p : projectiles){
			p.tick();
		}
	}

	public void onEvent(Event event){
	}
	
	@Override
	public void render(Graphics2D g){
		if(image != null){
			g.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
		}
	}

	protected boolean isColliding(double xa, double ya){
		boolean solid = false;
		for(int c = 0; c < 4; c++){
			int xq = xa > 0 ? 1 : 0;
			int yq = ya > 0 ? 1 : 0;
			double xt = ((x + xa) - c % 2 + xq) / Level.getCurrentLevel().getTileSize();
			double yt = ((y + ya) - c / 2 + yq) / Level.getCurrentLevel().getTileSize();
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if(c % 2 == 0) ix = (int) Math.floor(xt);
			if(c / 2 == 0) iy = (int) Math.floor(yt);
			Tile tile = Level.getCurrentLevel().getTile(ix, iy);
			if(tile != null){
				if(tile.isSolid()){
					solid = true;
				}
			}
		}
		return solid;
	}

	protected void move(double xa, double ya){

		if(xa != 0 && ya != 0){
			move(xa, 0);
			move(0, ya);
			return;
		}

		while(xa != 0){
			if(Math.abs(xa) > 1){
				if(!isColliding(abs(xa), ya)){
					this.x += abs(xa);
				}
				xa -= abs(xa);
			}else{
				if(!isColliding(abs(xa), ya)){
					this.x += xa;
				}
				xa = 0;
			}
		}

		while(ya != 0){
			if(Math.abs(ya) > 1){
				if(!isColliding(xa, abs(ya))){
					this.y += abs(ya);
				}
				ya -= abs(ya);
			}else{
				if(!isColliding(xa, abs(ya))){
					this.y += ya;
				}
				ya = 0;
			}
		}

	}

	private int abs(double value){
		if(value < 0) return -1;
		return 1;
	}

}
