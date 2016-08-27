package scibby.ui;

import java.awt.Graphics2D;
import java.util.ArrayList;

import scibby.events.Event;
import scibby.util.Vector2i;

public class UIPanel{

	public Vector2i position;

	public int width;
	public int height;

	private ArrayList<UIComponent> components = new ArrayList<UIComponent>();

	public UIPanel(Vector2i position, int width, int height){
		this.position = position;
		this.width = width;
		this.height = height;
	}

	public UIPanel(int x, int y, int width, int height){
		this.position = new Vector2i(x, y);
		this.width = width;
		this.height = height;
	}

	public void tick(){
		for(UIComponent component : components){
			component.tick();
		}
	}

	public void onEvent(Event event){
		for(UIComponent component : components){
			component.onEvent(event);
		}
	}
	
	public void render(Graphics2D g){
		for(UIComponent component : components){
			component.render(g);
		}
	}

	public void addComponent(UIComponent component){
		components.add(component);
	}

	public void removeComponent(UIComponent component){
		components.remove(component);
	}

	public ArrayList<UIComponent> getComponents(){
		return components;
	}

}
