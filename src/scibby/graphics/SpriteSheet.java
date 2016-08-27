package scibby.graphics;

import java.awt.image.BufferedImage;

import scibby.util.ResourceLoader;

public class SpriteSheet{

	public BufferedImage image;

	public SpriteSheet(String path){
		this.image = new ResourceLoader().loadImage(path);
	}

	public BufferedImage getImage(int x, int y, int width, int height){
		return image.getSubimage((x * width), (y * height), width, height);
	}

}