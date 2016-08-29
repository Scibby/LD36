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
import scibby.util.AudioPlayer;
import scibby.util.ResourceLoader;

public class MenuState extends State{

	private BufferedImage background = new ResourceLoader().loadImage("menu");

	private AudioPlayer song = new AudioPlayer("music");

	public MenuState(int stateId){
		super(stateId);
		song.loop();
	}

	@Override
	public void start(){
		UIPanel panel = new UIPanel(0, 0, Game.getGc().getWidth(), Game.getGc().getHeight());
		UILabel label = new UILabel(450, 50, 100, 50, panel, Game.getGc().getTitle(), new Font("opium", Font.PLAIN, 32));
		UIButton button = new UIButton(570, 75, 150, 50, panel, new UIActionListener(){

			@Override
			public void onAction(){
				clearLayers();
				Game.getUI().clearUI();
				UIPanel tempPanel = new UIPanel(0, 0, Game.getGc().getWidth(), Game.getGc().getHeight());
				UILabel tempLabel = new UILabel(450, 100, 10, 10, panel, "Creating Level...", new Font("opium", Font.PLAIN, 26));
				tempLabel.setColor(Color.BLACK);
				tempPanel.addComponent(tempLabel);
				Game.getUI().addPanel(tempPanel);
				addLayer(Game.getUI());
				Game.getUI().clearUI();
				Main.gameState.start();
				GameStateManager.currentState = 1;
			}
		});

		label.setColor(Color.BLACK);
		button.setText("Play");
		button.setColor(Color.BLACK);

		panel.addComponent(label);
		panel.addComponent(button);
		Game.getUI().addPanel(panel);
		addLayer(Game.getUI());

	}

	@Override
	public void render(Graphics2D g){
		g.drawImage(background, 0, 0, Game.getGc().getWidth(), Game.getGc().getHeight(), null);
		super.render(g);

	}

}
