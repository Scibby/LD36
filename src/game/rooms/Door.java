package game.rooms;

public class Door{

	public int x, y;
	public Room parent;
	public boolean connected;
	
	public Door(int x, int y, Room parent){
		this.x = x;
		this.y = y;
		this.parent = parent;
	}
	
}
