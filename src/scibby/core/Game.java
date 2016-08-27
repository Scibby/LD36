package scibby.core;

import scibby.ui.UIManager;

public abstract class Game{
	
	private static GameContainer gc;
	
	protected GameContainer init(int width, int height, GameContainer gc, String title){
		gc = new GameContainer(width, height, 60, title);
		gc.start();
		this.gc = gc;
		return gc;
	}

	public static GameContainer getGc(){
		return gc;
	}
	
	public static UIManager getUI(){
		return GameContainer.getUI();
	}
	
}
