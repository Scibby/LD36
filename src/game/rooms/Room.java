package game.rooms;

import java.util.ArrayList;
import java.util.Random;

public class Room{

	private int[] room;

	public int x1, y1;
	public int x2, y2;

	private int width, height;

	private int id;

	private int numOfDoors;

	public ArrayList<Door> doors = new ArrayList<Door>();

	public ArrayList<Room> connectedTo = new ArrayList<Room>();

	public Room(int x, int y, int width, int height, int[] room, int id){
		this.x1 = x - 1;
		this.y1 = y - 1;
		this.x2 = x + width + 1;
		this.y2 = y + height + 1;
		this.width = width;
		this.height = height;
		this.room = room;
		this.id = id;

		for(int yy = 0; yy < height; yy++){
			for(int xx = 0; xx < width; xx++){
				if(room[xx + yy * width] == 4){
					doors.add(new Door(xx, yy, this));
				}
			}
		}
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public int[] getRoom(){
		return room;
	}

	public static int roomIntersect(int low1, int high1, int low2, int high2){
		int min1 = Math.min(low1, high1);
		int max1 = Math.max(low1, high1);
		int min2 = Math.min(low2, high2);
		int max2 = Math.max(low2, high2);

		if((max1 >= min2) && (min1 <= max2)){
			int dist1 = max2 - min1;
			int dist2 = max1 - min2;
			if(dist2 < dist1){
				return dist2 * -1;
			}else{
				return dist1;
			}
		}else{
			return 0;
		}
	}

	public Door getDoor(){
		return doors.get(new Random().nextInt(doors.size()));
	}

}
