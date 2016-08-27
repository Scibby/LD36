package game.states;

import java.awt.Font;
import java.awt.Graphics2D;

import game.Main;
import scibby.core.Game;
import scibby.states.GameStateManager;
import scibby.states.State;
import scibby.ui.UIActionListener;
import scibby.ui.UIPanel;
import scibby.ui.components.UIButton;
import scibby.ui.components.UILabel;

public class MenuState extends State{

	public MenuState(int stateId){
		super(stateId);
	}

	@Override
	public void start(){
		UIPanel panel = new UIPanel(0, 0, Game.getGc().getWidth(), Game.getGc().getHeight());
		UILabel label = new UILabel(50, 50, 100, 50, panel, Game.getGc().getTitle(), new Font("Arial", Font.BOLD, 32));
		UIButton button = new UIButton(50, 75, 150, 50, panel, new UIActionListener(){
			
			@Override
			public void onAction(){
				clearLayers();
				Game.getUI().clearUI();
				Main.gameState.start();
				GameStateManager.currentState = 1;
			}
		});
		
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
		super.render(g);

	}

}
