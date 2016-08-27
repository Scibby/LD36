package scibby.level;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import scibby.entities.Entity;
import scibby.entities.Mob;
import scibby.entities.Particle;
import scibby.entities.Projectile;
import scibby.entities.Tile;
import scibby.events.Event;
import scibby.graphics.Camera;
import scibby.layer.Layer;
import scibby.util.Vector2i;

public abstract class Level extends Layer{

	private static int currentLevel;

	private static Level level;

	protected final int WIDTH, HEIGHT;

	protected final int TILE_SIZE;

	private static ArrayList<Level> levels = new ArrayList<Level>();

	protected ArrayList<Particle> particles = new ArrayList<Particle>();

	protected ArrayList<Mob> mobs = new ArrayList<Mob>();

	protected HashMap<Integer, Tile> tiles = new HashMap<Integer, Tile>();

	protected Camera camera;

	public Level(int width, int height, int viewSizeX, int viewSizeY, int tileSize){
		this.WIDTH = width;
		this.HEIGHT = height;
		this.TILE_SIZE = tileSize;
		camera = new Camera(viewSizeX, viewSizeY, width * tileSize, height * tileSize);
	}

	public void tick(){

		for(int i = 0; i < mobs.size(); i++){
			Mob m = mobs.get(i);

			m.tick();
			if(m.isRemoved()){
				remove(m);
			}
			for(int j = 0; j < m.projectiles.size(); j++){
				if(m.projectiles.get(j).isRemoved()){
					m.projectiles.remove(j);
				}
			}
		}

		level.camera.tick(mobs.get(0));

		for(int i = 0; i < particles.size(); i++){
			Particle p = particles.get(i);

			p.tick();
			if(p.isRemoved()){
				remove(p);
			}
		}
	}

	public void render(Graphics2D g){

		g.translate(-camera.camX, -camera.camY);

		for(int y = 0; y < level.HEIGHT; y++){
			for(int x = 0; x < level.WIDTH; x++){
				tiles.get(x + y * WIDTH).render(x * TILE_SIZE, y * TILE_SIZE, g);
			}
		}

		for(Mob m : mobs){
			m.render(g);
			for(Projectile p : m.projectiles){
				p.render(g);
			}
		}

		for(int i = 0; i < particles.size(); i++){
			Particle p = particles.get(i);
			p.render(g);
		}
		g.translate(camera.camX, camera.camY);
	}

	@Override
	public abstract void onEvent(Event event);

	public Tile getTile(int x, int y){
		return tiles.get(x + y * level.WIDTH);
	}

	public ArrayList<Mob> getMobsInRadius(Entity e, int radius){
		ArrayList<Mob> result = new ArrayList<Mob>();

		for(int i = 0; i < mobs.size(); i++){
			Mob mob = mobs.get(i);
			int distance = Vector2i.getDistance(new Vector2i((int) e.x + e.width / 2, (int) e.y + e.height / 2),
					new Vector2i((int) mob.x + mob.width / 2, (int) mob.y + mob.height / 2));
			if(distance <= radius){
				result.add(mob);
			}
		}

		return result;
	}

	public void add(Entity entity){
		if(currentLevel == 0) return;

		if(entity instanceof Mob){
			mobs.add((Mob) entity);
		}else if(entity instanceof Particle){
			particles.add((Particle) entity);
		}
	}

	public void remove(Entity entity){
		if(currentLevel == 0) return;

		if(entity instanceof Mob){
			mobs.remove((Mob) entity);
		}else if(entity instanceof Particle){
			particles.remove((Particle) entity);
		}
	}

	public static void addLevel(Level level){
		levels.add(level);
	}

	public static void setLevel(int levelNumber){
		currentLevel = levelNumber;
		level = levels.get(levelNumber - 1);
	}

	public static Level getCurrentLevel(){
		return level;
	}

	public Camera getCamera(){
		return camera;
	}

	public int getTileSize(){
		return TILE_SIZE;
	}

	public int getHeight(){
		return HEIGHT;
	}

	public int getWidth(){
		return WIDTH;
	}
}
