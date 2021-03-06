package scibby.util;

public class Vector2i{

	public int x;

	public int y;

	public Vector2i(){
		set(0, 0);
	}
	
	public Vector2i(int x, int y){
		set(x, y);
	}

	public Vector2i(Vector2i vector){
		set(vector.x, vector.y);
	}

	public Vector2i add(Vector2i vector){
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}
	
	public Vector2i add(int addition){
		this.x += addition;
		this.y += addition;
		return this;
	}

	public Vector2i subtract(Vector2i vector){
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}
	
	public Vector2i subtract(int subtraction){
		this.x -= subtraction;
		this.y -= subtraction;
		return this;
	}
	
	public static double getDistance(Vector2i vec1, Vector2i vec2){
		return Math.sqrt(Math.abs((vec1.x - vec2.x) * (vec1.x - vec2.x) + (vec1.y - vec2.y) * (vec1.y - vec2.y)));
	}
	
	@Override
	public boolean equals(Object obj){
		
		if(obj instanceof Vector2i){
			Vector2i vec = (Vector2i) obj;
			if(vec.x == x && vec.y == y){
				return true;
			}
		}
		return false;
	}
	
	public Vector2i set(int x, int y){
		this.x = x;
		this.y = y;
		return this;
	}

	@Deprecated
	public int getX(){
		return x;
	}

	public Vector2i setX(int x){
		this.x = x;
		return this;
	}

	@Deprecated
	public int getY(){
		return y;
	}

	public Vector2i setY(int y){
		this.y = y;
		return this;
	}

}
