package game;

import java.awt.image.BufferedImage;

import scibby.graphics.SpriteSheet;
import scibby.util.ResourceLoader;

public class Images{

	private Images(){}
	
	private static SpriteSheet playerSheet;
	
	private static SpriteSheet blockSheet;
	
	public static BufferedImage[] blocks = new BufferedImage[16];
	
	public static BufferedImage player;
	
	public static BufferedImage[] walkUp = new BufferedImage[2];
	
	public static BufferedImage[] walkDown = new BufferedImage[2];
	
	public static BufferedImage[] walkLeft = new BufferedImage[2];
	
	public static BufferedImage[] walkRight = new BufferedImage[2];
	
	public static BufferedImage[] playerIdle = new BufferedImage[4];

	public static BufferedImage bullet;
	
	private static ResourceLoader resLoader = new ResourceLoader();
	
	public static void loadImages(){
		playerSheet = new SpriteSheet("playerSpriteSheet");
		blockSheet = new SpriteSheet("blockSpriteSheet");
		
		player = playerSheet.getImage(0, 0, 64, 64);
		
		walkUp[0] = playerSheet.getImage(1, 3, 64, 64);
		walkUp[1] = playerSheet.getImage(2, 3, 64, 64);
		
		walkDown[0] = playerSheet.getImage(1, 0, 64, 64);
		walkDown[1] = playerSheet.getImage(2, 0, 64, 64);
		
		walkLeft[0] = playerSheet.getImage(1, 1, 64, 64);
		walkLeft[1] = playerSheet.getImage(2, 1, 64, 64);
		
		walkRight[0] = playerSheet.getImage(1, 2, 64, 64);
		walkRight[1] = playerSheet.getImage(2, 2, 64, 64);
		
		playerIdle[0] = playerSheet.getImage(0, 0, 64, 64);
		playerIdle[1] = playerSheet.getImage(0, 1, 64, 64);
		playerIdle[2] = playerSheet.getImage(0, 2, 64, 64);
		playerIdle[3] = playerSheet.getImage(0, 3, 64, 64);
		
		bullet = resLoader.loadImage("bullet");
		
		for(int y = 0; y < 4; y++){
			for(int x = 0; x < 4; x++){
				blocks[x + y * 4] = blockSheet.getImage(x, y, 32, 32);
			}
		}
	}
	
}
