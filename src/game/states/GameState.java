package game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.levels.Level1;
import scibby.core.Game;
import scibby.level.Level;
import scibby.states.State;
import scibby.ui.UIActionListener;
import scibby.ui.UIPanel;
import scibby.ui.components.UIButton;
import scibby.ui.components.UILabel;
import scibby.ui.components.UIProgressBar;

public class GameState extends State{

	public static UIPanel panel;
	public static UIProgressBar healthBar;
	public static UIButton genLevel;
	public static UILabel shots;
	public static UILabel hits;
	public static UILabel accuracy;

	private Level level1;

	public GameState(int stateId){
		super(stateId);
	}

	@Override
	public void start(){

		Game.getUI().clearUI();
		clearLayers();
		level1 = new Level1(200, 200, 1280 - 290, 960, 64);
		Level.addLevel(level1);
		Level.setLevel(Level.getLevelNumber() + 1);
		addLayer(level1);

		panel = new UIPanel(Game.getGc().getWidth() - 300, 0, 300, Game.getGc().getHeight());
		healthBar = new UIProgressBar(7, 250, 280, 40, panel, 0, 100);
		genLevel = new UIButton(10, 20, 150, 50, panel, new UIActionListener(){

			@Override
			public void onAction(){
				clearLayers();

				level1.clear();
				
				shots.text = "Shots: 0";
				hits.text = "Hits: 0";
				accuracy.text = "Accuracy: ";

				start();
				
//				UIPanel tempPanel = new UIPanel(0, 0, Game.getGc().getWidth(), Game.getGc().getHeight());
//				UILabel tempLabel = new UILabel(450, 100, 10, 10, panel, "Creating Level...", new Font("opium", Font.PLAIN, 26));
//				tempLabel.setColor(Color.WHITE);
//				tempPanel.addComponent(tempLabel);
//				Game.getUI().addPanel(tempPanel);
				addLayer(Game.getUI());

				/*level1 = new Level1(200, 200, 1280 - 290, 960, 64);
				level1.isComplete = false;
				Level.addLevel(level1);
				Level.setLevel(Level.getLevelNumber() + 1);*/
				/*for(int i = 0; i < Game.getUI().panels.size(); i++){
					if(Game.getUI().panels.get(i).equals(tempPanel)){
						Game.getUI().panels.remove(i);
						System.out.println("removed");
					}
					
				}*/
				/*addLayer(level1);
				addLayer(Game.getUI());
				Level.getCurrentLevel().drawHallways();*/
				
				
			}
		});

		genLevel.setText("Restart");
		shots = new UILabel(10, 120, 10, 10, panel, "Shots: ", new Font("Arial", Font.PLAIN, 26));
		hits = new UILabel(10, 170, 10, 10, panel, "Hits: ", new Font("Arial", Font.PLAIN, 26));
		accuracy = new UILabel(10, 220, 10, 10, panel, "Accuracy: ", new Font("Arial", Font.PLAIN, 26));

		genLevel.setColor(new Color(0xeeeeee));
		shots.setColor(new Color(0xeeeeee));
		hits.setColor(new Color(0xeeeeee));
		accuracy.setColor(new Color(0xeeeeee));
		
		Level.getCurrentLevel().drawHallways();

		Game.getUI().clearUI();
		healthBar.setValue(100);
		panel.visible = true;
		panel.addComponent(healthBar);
		panel.addComponent(genLevel);
		panel.addComponent(shots);
		panel.addComponent(hits);
		panel.addComponent(accuracy);
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
