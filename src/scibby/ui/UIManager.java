package scibby.ui;

import java.awt.Graphics2D;
import java.util.ArrayList;

import scibby.core.Game;
import scibby.events.Event;
import scibby.events.EventListener;
import scibby.layer.Layer;

public class UIManager extends Layer{

	EventListener listener = Game.getGc();

	public static ArrayList<UIPanel> panels = new ArrayList<UIPanel>();

	public UIManager(EventListener listener){
		this.listener = listener;
	}

	public void tick(){
		for(UIPanel panel : panels){
			panel.tick();
		}
	}

	public void render(Graphics2D g){
		for(UIPanel panel : panels){
			panel.render(g);
		}
	}

	@Override
	public void onEvent(Event event){
		for(UIPanel panel : panels){
			panel.onEvent(event);
		}
	}

	public void addPanel(UIPanel panel){
		panels.add(panel);
	}

	public void removePanel(UIPanel panel){
		panels.add(panel);
	}

	public void clearUI(){
		panels.clear();
	}

}
