package game;

import game.states.GameOverState;
import game.states.GameState;
import game.states.MenuState;
import scibby.core.Game;
import scibby.core.GameContainer;
import scibby.states.GameStateManager;
import scibby.util.Fonts;

public class Main extends Game{

	private GameContainer gc;
	
	public static MenuState menuState = new MenuState(0); 
	public static GameState gameState = new GameState(1); 
	public static GameOverState gameOverState = new GameOverState(2); 
	
	private void init(){
		loadFonts();
		gc = init(1280, 960, gc, "Pyramid Plunder");
		GameStateManager.addState(menuState);
		GameStateManager.addState(gameState);
		GameStateManager.addState(gameOverState);
		GameStateManager.currentState = 0;
		menuState.start();
		Images.loadImages();
	}
	
	private void loadFonts(){
		new Fonts("opium");
	}

	public static void main(String[] args){
		new Main().init();
	}
}
