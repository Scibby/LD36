package game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.Main;
import scibby.core.Game;
import scibby.states.GameStateManager;
import scibby.states.State;
import scibby.ui.UIActionListener;
import scibby.ui.UIPanel;
import scibby.ui.components.UIButton;
import scibby.ui.components.UILabel;
import scibby.util.ResourceLoader;

public class GameOverState extends State{

	private BufferedImage background = new ResourceLoader().loadImage("menu");
	public GameOverState(int stateId){
		super(stateId);
	}
	
	
	@Override
	public void start(){
		State state = this;
		Game.getUI().clearUI();
		UIPanel panel = new UIPanel(0, 0, Game.getGc().getWidth(), Game.getGc().getHeight());
		UILabel label = new UILabel(550, 50, 100, 50, panel, "Game Over", new Font("opium", Font.PLAIN, 32));
		UIButton button = new UIButton(570, 75, 200, 50, panel, new UIActionListener(){
			
			@Override
			public void onAction(){
				clearLayers();
				Game.getUI().clearUI();
				Main.menuState.start();
				
				GameStateManager.currentState = 0;
			}
		});
		
		label.setColor(Color.BLACK);
		button.setText("Play Again");
		button.setColor(Color.BLACK);
		
		panel.addComponent(label);
		panel.addComponent(button);
		Game.getUI().addPanel(panel);
		addLayer(Game.getUI());
	}
	
	@Override
	public void tick(){
		super.tick();
	}
	
	@Override
	public void render(Graphics2D g){
		g.drawImage(background, 0, 0, Game.getGc().getWidth(), Game.getGc().getHeight(), null);
		super.render(g);
	}

	
	
}
