package game;

import java.awt.image.BufferedImage;

import scibby.graphics.SpriteSheet;
import scibby.util.ResourceLoader;

public class Images{

	private Images(){}

	private static SpriteSheet playerSheet;

	private static SpriteSheet blockSheet;

	private static SpriteSheet basicEnemySheet;
	
	public static SpriteSheet danhkSheet;

	public static BufferedImage[] blocks = new BufferedImage[16];

	public static BufferedImage player;

	public static BufferedImage[] playerWalkUp = new BufferedImage[2];

	public static BufferedImage[] playerWalkDown = new BufferedImage[2];

	public static BufferedImage[] playerWalkLeft = new BufferedImage[2];

	public static BufferedImage[] playerWalkRight = new BufferedImage[2];

	public static BufferedImage[] playerIdle = new BufferedImage[4];

	public static BufferedImage[] basicEnemyWalkUp = new BufferedImage[2];

	public static BufferedImage[] basicEnemyWalkDown = new BufferedImage[2];

	public static BufferedImage[] basicEnemyWalkLeft = new BufferedImage[4];

	public static BufferedImage[] basicEnemyWalkRight = new BufferedImage[4];

	public static BufferedImage[] danhk = new BufferedImage[4];
	
	public static BufferedImage ankhProjectile;
	
	public static BufferedImage bullet;

	private static ResourceLoader resLoader = new ResourceLoader();

	public static void loadImages(){
		playerSheet = new SpriteSheet("playerSpriteSheet");
		blockSheet = new SpriteSheet("blockSpriteSheet");
		basicEnemySheet = new SpriteSheet("basicEnemySheet");
		danhkSheet = new SpriteSheet("dankh");

		player = playerSheet.getImage(0, 0, 64, 64);

		playerWalkUp[0] = playerSheet.getImage(1, 3, 64, 64);
		playerWalkUp[1] = playerSheet.getImage(2, 3, 64, 64);

		playerWalkDown[0] = playerSheet.getImage(1, 0, 64, 64);
		playerWalkDown[1] = playerSheet.getImage(2, 0, 64, 64);

		playerWalkLeft[0] = playerSheet.getImage(1, 1, 64, 64);
		playerWalkLeft[1] = playerSheet.getImage(2, 1, 64, 64);

		playerWalkRight[0] = playerSheet.getImage(1, 2, 64, 64);
		playerWalkRight[1] = playerSheet.getImage(2, 2, 64, 64);

		playerIdle[0] = playerSheet.getImage(0, 0, 64, 64);
		playerIdle[1] = playerSheet.getImage(0, 1, 64, 64);
		playerIdle[2] = playerSheet.getImage(0, 2, 64, 64);
		playerIdle[3] = playerSheet.getImage(0, 3, 64, 64);

		basicEnemyWalkUp[0] = basicEnemySheet.getImage(0, 0, 64, 64);
		basicEnemyWalkUp[1] = basicEnemySheet.getImage(1, 0, 64, 64);

		basicEnemyWalkRight[0] = basicEnemySheet.getImage(0, 2, 64, 64);
		basicEnemyWalkRight[1] = basicEnemySheet.getImage(1, 2, 64, 64);
		basicEnemyWalkRight[2] = basicEnemySheet.getImage(2, 2, 64, 64);
		basicEnemyWalkRight[3] = basicEnemySheet.getImage(3, 2, 64, 64);

		basicEnemyWalkLeft[0] = basicEnemySheet.getImage(0, 1, 64, 64);
		basicEnemyWalkLeft[1] = basicEnemySheet.getImage(1, 1, 64, 64);
		basicEnemyWalkLeft[2] = basicEnemySheet.getImage(2, 1, 64, 64);
		basicEnemyWalkLeft[3] = basicEnemySheet.getImage(3, 1, 64, 64);

		basicEnemyWalkDown[0] = basicEnemySheet.getImage(0, 0, 64, 64);
		basicEnemyWalkDown[1] = basicEnemySheet.getImage(1, 0, 64, 64);

		danhk[0] = danhkSheet.getImage(0, 0, 64, 64);
		danhk[1] = danhkSheet.getImage(1, 0, 64, 64);
		danhk[2] = danhkSheet.getImage(2, 0, 64, 64);
		danhk[3] = danhkSheet.getImage(1, 0, 64, 64);
		//danhk[4] = danhkSheet.getImage(0, 0, 64, 64);
		
		bullet = resLoader.loadImage("bullet");
		ankhProjectile = resLoader.loadImage("ankhProjectile");

		for(int y = 0; y < 4; y++){
			for(int x = 0; x < 4; x++){
				blocks[x + y * 4] = blockSheet.getImage(x, y, 32, 32);
			}
		}
	}

}
