package scibby.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation{

	private int speed;
	private int frames;

	private int index = 0;
	private int count = 0;

	private BufferedImage currentImage;
	private BufferedImage[] images;

	public Animation(int speed, BufferedImage[] images){
		this.speed = speed;
		this.images = images;

		frames = images.length;
	}

	public void runAnimation(){
		index++;

		if(index > speed){
			index = 0;
			nextFrame();
		}
	}

	private void nextFrame(){
		for(int i = 0; i < frames; i++){
			if(count == i){
				currentImage = images[i];
			}
		}

		count++;

		if(count > frames){
			count = 0;
		}
	}

	public void drawAnimation(Graphics g, int x, int y, int width, int height){
		g.drawImage(currentImage, x, y, width, height, null);
	}

}