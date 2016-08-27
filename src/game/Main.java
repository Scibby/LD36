package game;

import game.states.GameState;
import game.states.MenuState;
import scibby.core.Game;
import scibby.core.GameContainer;
import scibby.states.GameStateManager;

public class Main extends Game{

	private GameContainer gc;
	
	public static MenuState menuState = new MenuState(0); 
	public static GameState gameState = new GameState(1); 
	
	private void init(){
		gc = init(1280, 960, gc, "Title");
		GameStateManager.addState(menuState);
		GameStateManager.addState(gameState);
		GameStateManager.currentState = 0;
		menuState.start();
		Images.loadImages();
	}

	public static void main(String[] args){
		new Main().init();
	}
}
