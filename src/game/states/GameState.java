package game.states;

import java.awt.Graphics2D;

import game.levels.Level1;
import scibby.level.Level;
import scibby.states.State;

public class GameState extends State{

	public GameState(int stateId){
		super(stateId);
	}
	
	@Override
	public void start(){
		Level level1 = new Level1(30, 30, 1280, 960, 64);
		Level.addLevel(level1);
		Level.setLevel(1);
		addLayer(level1);
	}

	
	@Override
	public void tick(){
		super.tick();
	}
	
	@Override
	public void render(Graphics2D g){
		super.render(g);
	}
}
