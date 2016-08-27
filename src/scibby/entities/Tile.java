package scibby.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import scibby.level.Level;

public class Tile{

	private BufferedImage image;

	private boolean solid;
	
	public Tile(BufferedImage image, boolean solid){
		this.image = image;
		this.solid = solid;
	}

	public void render(int x, int y, Graphics2D g){
		if(image != null){
			g.drawImage(image, (int) x, (int) y, Level.getCurrentLevel().getTileSize(), Level.getCurrentLevel().getTileSize(), null);
		}
	}

	public boolean isSolid(){
		return solid;
	}

}
