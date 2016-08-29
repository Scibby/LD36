package game.levels;

import java.util.ArrayList;
import java.util.Random;

import game.Images;
import game.entities.mobs.Player;
import game.entities.mobs.enemies.BasicEnemy;
import game.entities.mobs.enemies.Dankh;
import game.rooms.Door;
import game.rooms.Room;
import scibby.entities.Mob;
import scibby.entities.Tile;
import scibby.events.Event;
import scibby.level.Level;
import scibby.util.ResourceLoader;
import scibby.util.Vector2i;

public class Level1 extends Level{

	ArrayList<Room> drawnRooms = new ArrayList<Room>();

	Tile voidTile = new Tile(Images.blocks[0], false, false);
	Tile voidTile2 = new Tile(Images.blocks[1], true, true);
	Tile wallTile = new Tile(Images.blocks[1], true, false);
	Tile wallCracked = new Tile(Images.blocks[2], true, false);
	Tile wallMossy = new Tile(Images.blocks[3], true, false);
	Tile path = new Tile(Images.blocks[0], false, true);
	Tile door = new Tile(Images.blocks[0], false, true);

	public Level1(int width, int height, int viewSizeX, int viewSizeY, int tileSize){
		super(width, height, viewSizeX, viewSizeY, tileSize);
		camera.useCamera = true;

		isComplete = false;
		
		boolean spwanedPlayer = false;
		
		Random random = new Random();

		ArrayList<int[]> rooms = new ArrayList<int[]>();

		int[] roomArray1 = new ResourceLoader().loadLevel("room1", 16, 16);
		int[] roomArray2 = new ResourceLoader().loadLevel("room2", 24, 24);
		int[] roomArray3 = new ResourceLoader().loadLevel("room3", 16, 16);
		int[] roomArray4 = new ResourceLoader().loadLevel("room4", 16, 16);
		int[] roomArray5 = new ResourceLoader().loadLevel("room5", 8, 8);
		
		rooms.add(roomArray1);
		rooms.add(roomArray2);
		rooms.add(roomArray3);
		rooms.add(roomArray4);
		rooms.add(roomArray5);
		
		int numRooms = 10;

		for(int i = 0; i < numRooms; i++){

			int[] roomArray = rooms.get(random.nextInt(5));

			int xOffset = random.nextInt(100 - 20) + 20;
			int yOffset = random.nextInt(100 - 20) + 20;

			Room room;
			
			if(roomArray.equals(roomArray1)){
				room = new Room(xOffset, yOffset, 16, 16, roomArray, i);
			}else if(roomArray.equals(roomArray2)){				
				room = new Room(xOffset, yOffset, 24, 24, roomArray2, i);
			}else if(roomArray.equals(roomArray3)){				
				room = new Room(xOffset, yOffset, 16, 16, roomArray3, i);
			}else if(roomArray.equals(roomArray4)){				
				room = new Room(xOffset, yOffset, 16, 16, roomArray4, i);
			}else{
				room = new Room(xOffset, yOffset, 8, 8, roomArray5, i);
			}
			
			drawnRooms.add(room);
		}
		int iterations = 50;

		boolean continueLoop = false;

		do{

			for(int q = 0; q < drawnRooms.size(); q++){

				for(int j = q + 1; j < drawnRooms.size(); j++){

					Room r1 = drawnRooms.get(q);

					Room r2 = drawnRooms.get(j);

					double xCollide = Room.roomIntersect(r1.x1, r1.x2, r2.x1, r2.x2);
					double yCollide = Room.roomIntersect(r1.y1, r1.y2, r2.y1, r2.y2);

					if(xCollide != 0 && yCollide != 0){

						continueLoop = true;

						if(Math.abs(xCollide) < Math.abs(yCollide)){

							double shift1 = Math.floor(xCollide * 0.5);
							double shift2 = -1 * (xCollide - shift1);

							r1.x1 += shift1;
							r1.x2 += shift1;
							r2.x1 += shift2;
							r2.x2 += shift2;

						}else{

							double shift1 = Math.floor(yCollide * 0.5);
							double shift2 = -1 * (yCollide - shift1);

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

		for(int i = 0; i < drawnRooms.size(); i++){
			Room room = drawnRooms.get(i);
			/*
			 * if(room.x1 < 0) continue; if(room.x2 > WIDTH) continue; if(room.y1 <
			 * 0) continue; if(room.y2 > HEIGHT) continue;
			 */
			int[] array = room.getRoom();
			int w = 0, h = 0;
			if(array.equals(roomArray1)){
				w = h = 16;
			}else if(array.equals(roomArray2)){
				w = h = 24;
			}else if(array.equals(roomArray3)){
				w = h = 16;
			}else if(array.equals(roomArray4)){
				w = h = 16;
			}else if(array.equals(roomArray5)){
				w = h = 8;
			}
			for(int yy = 0; yy < h; yy++){
				int y = room.y1 + yy - 1;
				for(int xx = 0; xx < w; xx++){
					int x = room.x1 + xx - 1;
					if(array[xx + yy * w] == 0){
						tiles.put(x + y * WIDTH, voidTile);
						if(!spwanedPlayer){
							spwanedPlayer = true;
							mobs.add(new Player(x * tileSize, y * tileSize, 64, 64, null));
						}
					}else if(array[xx + yy * w] == 1){
						tiles.put(x + y * WIDTH, wallTile);
					}else if(array[xx + yy * w] == 2){
						tiles.put(x + y * WIDTH, wallCracked);
					}else if(array[xx + yy * w] == 3){
						tiles.put(x + y * WIDTH, wallMossy);
					}else if(array[xx + yy * w] == 4){
						tiles.put(x + y * WIDTH, door);
					}else if(array[xx + yy * w] == 6){
						
						if(random.nextInt(5) < 2){
							mobs.add(new Dankh(x * tileSize, y * tileSize, 64, 64, null));
						}else{
							mobs.add(new BasicEnemy(x * tileSize, y * tileSize, 64, 64, null));
						}
						
						tiles.put(x + y * WIDTH, voidTile);
					}
				}
			}
		}
		for(int y = 0; y < HEIGHT; y++){
			for(int x = 0; x < WIDTH; x++){
				if(tiles.get(x + y * WIDTH) == null){
					tiles.put(x + y * WIDTH, voidTile2);
				}
			}
		}

		//Draw Hallways

	}

	public void drawHallways(){

		for(int i = 0; i < drawnRooms.size(); i++){
			Room room1 = drawnRooms.get(i);
			for(int j = i + 1; j < drawnRooms.size(); j++){
				Room room2 = drawnRooms.get(j);
				if(room1.equals(room2)) continue;
				if(room1.connectedTo.contains(room2)) continue;
				for(int q = 0; q < room1.doors.size(); q++){
					Door door1 = room1.doors.get(q);
					for(int p = 0; p < room2.doors.size(); p++){
						Door door2 = room2.doors.get(p);
						door1.parent.connectedTo.add(door2.parent);
						door2.parent.connectedTo.add(door1.parent);

						ArrayList<Node> path;

						path = AStarSearch(new Vector2i((door1.x + door1.parent.x1 - 1), (door1.y + door1.parent.y1 - 1)),
								new Vector2i((door2.x + door2.parent.x1 - 1), (door2.y + door2.parent.y1 - 1)));

						if(path == null) break;

						for(int h = 1; h < path.size(); h++){
							if(path.get(h) == null) continue;
							System.err.println(path.size());
							if(getTile(path.get(h).tile.x, path.get(h).tile.y) == this.path) break;
							if(getTile(path.get(h).tile.x, path.get(h).tile.y) == this.door) continue;
							tiles.put(path.get(h).tile.x + path.get(h).tile.y * WIDTH, this.path);
						}
					}
				}
			}
		}
		/*
		 * for(int i = 0; i < drawnRooms.size(); i++){ for(int j = i + 1; j <
		 * drawnRooms.size(); j++){ Door door1 = drawnRooms.get(i).getDoor();
		 * //System.out.println((door1.x + door1.parent.x1) + ", " + (door1.y +
		 * door1.parent.y1)); Door door2 = drawnRooms.get(j).getDoor();
		 * //System.out.println((door2.x + door2.parent.x1) + ", " + (door2.y +
		 * door2.parent.y1));
		 * 
		 * door1.connected = true; door2.connected = true;
		 * 
		 * if(door1.parent.connectedTo.contains(door2.parent)){ continue; }
		 * door1.parent.connectedTo.add(door2.parent);
		 * door2.parent.connectedTo.add(door1.parent);
		 * 
		 * ArrayList<Node> path;
		 * 
		 * path = AStarSearch(new Vector2i((door1.x + door1.parent.x1 - 1),
		 * (door1.y + door1.parent.y1 - 1)), new Vector2i((door2.x +
		 * door2.parent.x1 - 1), (door2.y + door2.parent.y1 - 1)));
		 * 
		 * if(path == null) break;
		 * 
		 * for(int q = 1; q < path.size(); q++){ if(path.get(q) == null) continue;
		 * System.err.println(path.size()); if(getTile(path.get(q).tile.x,
		 * path.get(q).tile.y) == this.path) break; if(getTile(path.get(q).tile.x,
		 * path.get(q).tile.y) == this.wallMossy) break;
		 * tiles.put(path.get(q).tile.x + path.get(q).tile.y * WIDTH, this.path);
		 * 
		 * }
		 * 
		 * } }
		 */

		for(int yy = 0; yy < HEIGHT; yy++){
			for(int xx = 0; xx < WIDTH; xx++){
				Tile tile = getTile(xx, yy);
				if(tile.isSolid()) continue;
				if(!tile.equals(this.path)) continue;

				for(int e = 0; e < 9; e++){
					if(e == 4){
						continue;
					}
					int xi = (e % 3) - 1;
					int yi = (e / 3) - 1;
					if(getTile((xx + xi), (yy + yi)).equals(this.path)) continue;
					if(getTile((xx + xi), (yy + yi)).equals(this.door)) continue;
					if(!getTile((xx + xi), (yy + yi)).canPath()) continue;
					tiles.put((xx + xi) + (yy + yi) * WIDTH, this.wallTile);
				}
			}
		}
		isComplete = true;
	}

	@Override
	public void onEvent(Event event){
		for(Mob mob : mobs){
			mob.onEvent(event);
		}
	}

}
