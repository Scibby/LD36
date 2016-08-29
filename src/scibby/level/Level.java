package scibby.level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import game.levels.Node;
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

	public boolean isComplete = false;

	public Level(int width, int height, int viewSizeX, int viewSizeY, int tileSize){
		this.WIDTH = width;
		this.HEIGHT = height;
		this.TILE_SIZE = tileSize;
		camera = new Camera(viewSizeX, viewSizeY, width * tileSize, height * tileSize);
	}

	public void tick(){
		if(!isComplete) return;
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
		if(!isComplete){
			g.setColor(Color.WHITE);
			g.setFont(new Font("opium", Font.PLAIN, 35));
			g.drawString("Creating level...", 400, 200);
			return;
		}
		g.translate(-camera.camX, -camera.camY);

		for(int y = 0; y < level.HEIGHT; y++){
			for(int x = 0; x < level.WIDTH; x++){
				Tile tile = tiles.get(x + y * WIDTH);
				if(tile != null){
					tile.render(x * TILE_SIZE, y * TILE_SIZE, g);
				}

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

	public void drawHallways(){}

	@Override
	public abstract void onEvent(Event event);

	public Tile getTile(int x, int y){
		return tiles.get(x + y * level.WIDTH);
	}

	public ArrayList<Mob> getMobsInRadius(Entity e, int radius){
		ArrayList<Mob> result = new ArrayList<Mob>();

		for(int i = 0; i < mobs.size(); i++){
			Mob mob = mobs.get(i);
			double distance = Vector2i.getDistance(new Vector2i((int) e.x + e.width / 2, (int) e.y + e.height / 2),
					new Vector2i((int) mob.x + mob.width / 2, (int) mob.y + mob.height / 2));
			if(distance <= radius){
				result.add(mob);
			}
		}

		return result;
	}

	Vector2i oldVec;

	public ArrayList<Node> AStarSearch(Vector2i start, Vector2i goal){
		ArrayList<Node> openList = new ArrayList<Node>();
		ArrayList<Node> closedList = new ArrayList<Node>();
		Comparator<Node> sorter = new Comparator<Node>(){
			@Override
			public int compare(Node n0, Node n1){
				if(n1.fCost < n0.fCost) return +1;
				if(n1.fCost > n0.fCost) return -1;
				return 0;
			}
		};

		Node current = new Node(start, null, 0, Vector2i.getDistance(start, goal));
		openList.add(current);

		while(openList.size() > 0){
			System.out.println(openList.size());

			if(openList.size() > 400){
				openList.clear();
				closedList.clear();
				System.exit(0);
			}

			Collections.sort(openList, sorter);
			current = openList.get(0);
			if(current.tile.equals(goal)){
				ArrayList<Node> path = new ArrayList<Node>();
				while(current.parent != null){
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}

			openList.remove(current);
			closedList.add(current);
			for(int i = 0; i < 9; i++){
				if(i == 0) continue;
				if(i == 2) continue;
				if(i == 4) continue;
				if(i == 6) continue;
				if(i == 8) continue;

				int x = current.tile.x;
				int y = current.tile.y;

				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;

				Vector2i vec = new Vector2i(x + xi, y + yi);

				/*
				 * int goTo = i; if(oldVec != null){ if(oldVec.equals(vec)){
				 * //System.out.println(i); switch(i){ case 1: goTo = 7; break; case
				 * 3: goTo = 5; break; case 5: goTo = 3; break; case 7: goTo = 1;
				 * break; } } }
				 */

				Tile tile = getTile(x + xi, y + yi);

				if(tile == null) continue;
				if(!tile.canPath()) continue;

				double gCost = current.gCost + Vector2i.getDistance(current.tile, vec);

				double hCost = Vector2i.getDistance(vec, goal);
				//if(i != goTo) gCost += 0.5;

				Node node = new Node(vec, current, gCost, hCost);

				if(vecInList(closedList, vec) && gCost >= node.gCost) continue;
				if(!vecInList(openList, vec) || gCost < node.gCost) openList.add(node);
				oldVec = vec;
			}

		}

		openList.clear();
		closedList.clear();
		System.out.println("null");
		return null;
	}

	private boolean vecInList(ArrayList<Node> list, Vector2i vec){
		for(Node node : list){
			if(node.tile.equals(vec)) return true;
		}
		return false;
	}

	public Mob getPlayer(){
		return mobs.get(0);
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

	public void clear(){
		tiles.clear();
		mobs.clear();
		for(Mob mob : mobs){
			mob.projectiles.clear();
		}
		particles.clear();
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

	public static int getLevelNumber(){
		return currentLevel;
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
