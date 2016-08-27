package scibby.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import scibby.events.Event;
import scibby.events.EventDispatcher;
import scibby.events.EventHandler;
import scibby.events.types.MousePressedEvent;
import scibby.events.types.MouseReleasedEvent;
import scibby.util.Vector2i;

public class UIPanel{

	public Vector2i position;

	public int width;
	public int height;

	public boolean visible = false;

	private Color color = new Color(0x333333);

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
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) e));
	}

	private boolean onMousePressed(MousePressedEvent e){
		int mx = e.getX();
		int my = e.getY();

		if(hasInside(mx, my)) return true;

		return false;
	}

	private boolean onMouseReleased(MouseReleasedEvent e){
		int mx = e.getX();
		int my = e.getY();

		if(hasInside(mx, my)) return true;

		return false;
	}

	public void render(Graphics2D g){
		if(visible){
			g.setColor(color);
			g.fillRect(position.getX(), position.getY(), width, height);
		}
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

	public void setColor(Color color){
		this.color = color;
	}

	public boolean hasInside(int xp, int yp){
		int w = this.width;
		int h = this.height;
		if((w | h) < 0) return false;

		int x = position.getX();
		int y = position.getY();

		if(xp < x || yp < y) return false;
		w += x;
		h += y;

		return ((w < x || w > xp) && (h < y || h > yp));
	}

}
