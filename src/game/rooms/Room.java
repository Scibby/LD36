package game.rooms;

import game.Main;
import scibby.core.Game;
import scibby.util.Vector2i;

public class Room{

	private int[] room;

	public int x1, y1;
	public int x2, y2;

	private int width, height;

	private int id;

	public Room(int x, int y, int width, int height, int[] room, int id){
		this.x1 = x - 1;
		this.y1 = y - 1;
		this.x2 = x + width + 1;
		this.y2 = y + height + 1;
		this.width = width;
		this.height = height;
		this.room = room;
		this.id = id;
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

	/*public void collides(Room room){

		int xMove = 0, yMove = 0;

		Vector2i a = new Vector2i(x * 16, y * 16), b = new Vector2i(a).add(width * 16).add(height * 16),
				c = new Vector2i(room.x * 16, room.y * 16), d = new Vector2i(c).add(room.width * 16).add(room.height * 16);

		if(hasInside(c.x, c.y)){
			xMove = Math.abs(c.x - x);
			yMove = Math.abs(c.y - y);

			if(xMove != 0 && yMove != 0){

				if(Math.abs(xMove) > Math.abs(yMove)){
					double shift1 = Math.floor(xMove * 0.5);
					double shift2 = -1 * (xMove - shift1);

					a.x += shift1;
					b.x += shift1;
					c.x += shift2;
					d.x += shift2;
				}else{
					double shift1 = Math.floor(yMove * 0.5);
					double shift2 = -1 * (yMove - shift1);

					System.out.println(a.x + ", " + a.y);
					a.y += shift1;
					System.err.println(a.x + ", " + a.y);
					b.y += shift1;
					c.y += shift2;
					d.y += shift2;

				}
				System.out.println(x + ", " + y);
				x = a.x;
				y = a.y;
				System.out.println(x + ", " + y);

				room.x = c.x;
				room.y = c.y;
			}
		}

	}

	public boolean hasInside(int xp, int yp){
		int w = this.width;
		int h = this.height;
		if((w | h) < 0) return false;

		int x = this.x;
		int y = this.y;

		if(xp < x || yp < y) return false;
		w += x;
		h += y;

		return ((w < x || w > xp) && (h < y || h > yp));
	}*/

}
