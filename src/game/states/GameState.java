package game.states;

import java.awt.Graphics2D;

import game.Main;
import game.levels.Level1;
import scibby.core.Game;
import scibby.level.Level;
import scibby.states.State;
import scibby.ui.UIActionListener;
import scibby.ui.UIPanel;
import scibby.ui.components.UIButton;
import scibby.ui.components.UIProgressBar;

public class GameState extends State{

	public static UIPanel panel;
	public static UIProgressBar healthBar;
	public static UIButton genLevel;

	private Level level1;

	public GameState(int stateId){
		super(stateId);
	}

	@Override
	public void start(){
		level1 = new Level1(100, 100, 1280 - 290, 960, 64);
		Level.addLevel(level1);
		Level.setLevel(1);
		addLayer(level1);

		panel = new UIPanel(Game.getGc().getWidth() - 300, 0, 300, Game.getGc().getHeight());
		healthBar = new UIProgressBar(10, 500, 280, 40, panel, 0, 100);
		genLevel = new UIButton(10, 20, 150, 50, panel, new UIActionListener(){

			@Override
			public void onAction(){
				clearLayers();
				level1 = new Level1(100, 100, 1280 - 290, 960, 64);
				Level.addLevel(level1);
				Level.setLevel(Level.getLevelNumber() + 1);
				addLayer(level1);
				addLayer(Game.getUI());
			}
		});
		healthBar.setValue(100);
		panel.visible = true;
		panel.addComponent(healthBar);
		panel.addComponent(genLevel);
		Game.getUI().addPanel(panel);
		addLayer(Game.getUI());
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
