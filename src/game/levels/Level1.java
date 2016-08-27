package game.levels;

import game.Images;
import game.entities.mobs.Player;
import game.entities.mobs.enemies.BasicEnemy;
import scibby.entities.Mob;
import scibby.entities.Tile;
import scibby.events.Event;
import scibby.level.Level;
import scibby.util.ResourceLoader;

public class Level1 extends Level{

	public Level1(int width, int height, int viewSizeX, int viewSizeY, int tileSize){
		super(width, height, viewSizeX, viewSizeY, tileSize);
		camera.useCamera = true;

		int[] level1 = new ResourceLoader().loadLevel("level1", 30, 30);

		Tile voidTile = new Tile(Images.blocks[0], false);
		Tile wallTile = new Tile(Images.blocks[1], true);
		Tile wallCracked = new Tile(Images.blocks[2], true);
		Tile wallMossy = new Tile(Images.blocks[3], true);
		
		
		mobs.add(new Player(10 * 32, 7 * 32, 64, 64, null));
		mobs.add(new BasicEnemy(15 * 32, 7 * 32, 64, 64, null));
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(level1[x + y * WIDTH] == 0){
					tiles.put(x + y * WIDTH, voidTile);
				}else if(level1[x + y * WIDTH] == 1){
					tiles.put(x + y * WIDTH, wallTile);
				}else if(level1[x + y * WIDTH] == 2){
					tiles.put(x + y * WIDTH, wallCracked);
				}else if(level1[x + y * WIDTH] == 3){
					tiles.put(x + y * WIDTH, wallMossy);
				}

				if(level1[x + y * WIDTH] == -1){
					tiles.put(x + y * WIDTH, voidTile);
				}
			}
		}
	}

	@Override
	public void onEvent(Event event){
		for(Mob mob : mobs){
			mob.onEvent(event);
		}
	}

}
