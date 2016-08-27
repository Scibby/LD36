package game.levels;

import java.util.ArrayList;
import java.util.Random;

import game.Images;
import game.entities.mobs.Player;
import game.rooms.Room;
import scibby.entities.Mob;
import scibby.entities.Tile;
import scibby.events.Event;
import scibby.level.Level;
import scibby.util.MathsUtil;
import scibby.util.ResourceLoader;

public class Level1 extends Level{

	public Level1(int width, int height, int viewSizeX, int viewSizeY, int tileSize){
		super(width, height, viewSizeX, viewSizeY, tileSize);
		camera.useCamera = true;

		Random random = new Random();

		ArrayList<int[]> rooms = new ArrayList<int[]>();
		ArrayList<Room> drawnRooms = new ArrayList<Room>();

		int[] roomArray1 = new ResourceLoader().loadLevel("room1", 10, 10);
		int[] roomArray2 = new ResourceLoader().loadLevel("room2", 10, 10);

		rooms.add(roomArray1);
		rooms.add(roomArray2);

		int numRooms = 25;

		Tile voidTile = new Tile(Images.blocks[0], false);
		Tile wallTile = new Tile(Images.blocks[1], true);
		Tile wallCracked = new Tile(Images.blocks[2], true);
		Tile wallMossy = new Tile(Images.blocks[3], false);

		mobs.add(new Player(10 * 32, 7 * 32, 64, 64, null));

		for(int i = 0; i < numRooms; i++){

			int[] roomArray = rooms.get(random.nextInt(2));

			int xOffset = random.nextInt(100 - 10) + 10;
			int yOffset = random.nextInt(100 - 10) + 10;

			xOffset = MathsUtil.clamp(xOffset, 0, 90);
			yOffset = MathsUtil.clamp(yOffset, 0, 90);

			Room room = new Room(xOffset, yOffset, 10, 10, roomArray, i);

			drawnRooms.add(room);
		}
		int iterations = 500;

		boolean continueLoop = false;
		do{
			//Loop through all rooms
			for(int q = 0; q < drawnRooms.size(); q++){
				//Loop through the room pairings that haven't yet been checked
				for(int j = q + 1; j < drawnRooms.size(); j++){

					//Get the first room
					Room r1 = drawnRooms.get(q);
					//Get the second room
					Room r2 = drawnRooms.get(j);

					//Check room intersection on both axes
					double xCollide = rangeIntersect(r1.x1, r1.x2, r2.x1, r2.x2);
					double yCollide = rangeIntersect(r1.y1, r1.y2, r2.y1, r2.y2);
					//If the rooms intersect on both axes
					if(xCollide != 0 && yCollide != 0){

						//There is a proven room intersection, noIntersection becomes false
						continueLoop = true;
						//Determine the smallest movement it would take for the rooms to no longer intersect
						//If the distance moved for x would be shorter
						if(Math.abs(xCollide) < Math.abs(yCollide)){

							//Get distance to shift both rooms by splitting the returned collision amount in half
							double shift1 = Math.floor(xCollide * 0.5);
							double shift2 = -1 * (xCollide - shift1);

							//Add shift amounts to both room's location data
							r1.x1 += shift1;
							r1.x2 += shift1;
							r2.x1 += shift2;
							r2.x2 += shift2;

							//Else, the distance moved for y would be shorter or the same
						}else{

							//Get distance to shift both rooms by splitting the returned collision amount in half
							double shift1 = Math.floor(yCollide * 0.5);
							double shift2 = -1 * (yCollide - shift1);

							//Add shift amounts to both room's location data
							r1.y1 += shift1;
							r1.y2 += shift1;
							r2.y1 += shift2;
							r2.y2 += shift2;
						}
					}
				}
			}
			iterations--;
			if(iterations <= 0){
				continueLoop = false;
			}
		}while(continueLoop == true);

		/*
		 * do{ //Seperate rooms for(int i = 0; i < drawnRooms.size(); i++){
		 * for(int j = i; j < drawnRooms.size(); j++){
		 * //if(drawnRooms.get(j).equals(room1)) continue; Room room1 =
		 * drawnRooms.get(i);
		 * 
		 * Room room2 = drawnRooms.get(j);
		 * 
		 * room1.collides(room2);
		 * 
		 * } } iterations--; System.out.println(iterations); }while(iterations >
		 * 0);
		 */

		for(int i = 0; i < drawnRooms.size(); i++){
			Room room = drawnRooms.get(i);
			/*if(room.x1 < 0) continue;
			if(room.x2 > WIDTH) continue;
			if(room.y1 < 0) continue;
			if(room.y2 > HEIGHT) continue;*/
			int[] array = room.getRoom();
			for(int yy = 0; yy < 10; yy++){
				int y = room.y1 + yy - 1;
				for(int xx = 0; xx < 10; xx++){
					int x = room.x1 + xx - 1;
					if(array[xx + yy * 10] == 0){
						tiles.put(x + y * WIDTH, voidTile);
					}else if(array[xx + yy * 10] == 1){
						tiles.put(x + y * WIDTH, wallTile);
					}else if(array[xx + yy * 10] == 2){
						tiles.put(x + y * WIDTH, wallCracked);
					}else if(array[xx + yy * 10] == 3){
						tiles.put(x + y * WIDTH, wallMossy);
					}

				}
			}
		}
		for(int y = 0; y < HEIGHT; y++){
			for(int x = 0; x < WIDTH; x++){
				if(tiles.get(x + y * WIDTH) == null){
					tiles.put(x + y * WIDTH, voidTile);
				}
			}
		}
		/*
		 * int[] level1 = new ResourceLoader().loadLevel("level1", 30, 30);
		 * 
		 * 
		 * 
		 * mobs.add(new Player(10 * 32, 7 * 32, 64, 64, null)); mobs.add(new
		 * BasicEnemy(18 * 32, 7 * 32, 64, 64, null)); for(int y = 0; y < height;
		 * y++){ for(int x = 0; x < width; x++){ if(level1[x + y * WIDTH] == 0){
		 * tiles.put(x + y * WIDTH, voidTile); }else if(level1[x + y * WIDTH] ==
		 * 1){ tiles.put(x + y * WIDTH, wallTile); }else if(level1[x + y * WIDTH]
		 * == 2){ tiles.put(x + y * WIDTH, wallCracked); }else if(level1[x + y *
		 * WIDTH] == 3){ tiles.put(x + y * WIDTH, wallMossy); }
		 * 
		 * if(level1[x + y * WIDTH] == -1){ tiles.put(x + y * WIDTH, voidTile); }
		 * } }
		 */
	}

	private int rangeIntersect(int low1, int high1, int low2, int high2){
		int min1 = Math.min(low1, high1);
		int max1 = Math.max(low1, high1);
		int min2 = Math.min(low2, high2);
		int max2 = Math.max(low2, high2);
		//if the ranges intersect
		if((max1 >= min2) && (min1 <= max2)){
			//calculate by how much the ranges intersect
			int dist1 = max2 - min1;
			int dist2 = max1 - min2;
			//if dist2 is smaller
			if(dist2 < dist1){
				//that means range 0 must be shifted down to no longer intersect
				return dist2 * -1;
				//else, dist2 is larger or equal to dist1
			}else{
				//that means range 0 must be shifted up to no longer intersect
				return dist1;
			}
		}else{
			return 0;
		}
	}
	
	public int getBiasedInt(int min, int max) {
	    int rand = (int) (Math.random() * max);
	    while (rand < min) {
	        rand = (int) Math.random();
	    }
	    int mid = (max / 2) - (min / 2);
	    int halfmid = mid / 2;
	    if (rand > mid) {
	        rand -= Math.random() * halfmid;
	    } else {
	        rand += Math.random() * halfmid;
	    }
	    return rand;
	}

	@Override
	public void onEvent(Event event){
		for(Mob mob : mobs){
			mob.onEvent(event);
		}
	}

}
