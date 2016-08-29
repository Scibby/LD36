package scibby.ui.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

import scibby.events.Event;
import scibby.events.EventDispatcher;
import scibby.events.types.MouseMovedEvent;
import scibby.events.types.MousePressedEvent;
import scibby.events.types.MouseReleasedEvent;
import scibby.ui.UIActionListener;
import scibby.ui.UIButtonListener;
import scibby.ui.UIComponent;
import scibby.ui.UIPanel;
import scibby.util.Vector2i;

public class UIButton extends UIComponent{

	private UIActionListener actionListener;

	private UIButtonListener buttonListener = new UIButtonListener(){};

	private UILabel label;

	private boolean inside = false;
	private boolean pressed = false;
	
	private Color color = Color.RED;
	
	public UIButton(Vector2i position, int width, int height, UIPanel parent, UIActionListener actionListener){
		super(position, width, height, parent);
		this.actionListener = actionListener;
		label = new UILabel(new Vector2i(position), width, height, parent, "Button", new Font("opium", Font.PLAIN, 26), this);
		parent.addComponent(label);
	}

	public UIButton(int x, int y, int width, int height, UIPanel parent, UIActionListener actionListener){
		this(new Vector2i(x, y), width, height, parent, actionListener);
	}

	@Override
	public void onEvent(Event event){
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> onMouseMoved((MouseMovedEvent) event));
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) event));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) event));
	}
	
	public void action(){
		actionListener.onAction();
	}

	private boolean onMouseMoved(MouseMovedEvent e){
		int mx = e.getX();
		int my = e.getY();

		if(hasInside(mx, my) && !inside){
			buttonListener.buttonEntered(this);
			inside = true;
			return true;
		}

		if(!hasInside(mx, my) && inside){
			buttonListener.buttonExited(this);
			inside = false;
			return true;
		}

		return false;
	}

	private boolean onMousePressed(MousePressedEvent e){
		int mx = e.getX();
		int my = e.getY();
		int button = e.getButton();
		
		if(inside && button == MouseEvent.BUTTON1 && !pressed){
			buttonListener.buttonPressed(this);
			pressed = true;
			return true;
		}

		return false;
	}
	
	private boolean onMouseReleased(MouseReleasedEvent e){
		int mx = e.getX();
		int my = e.getY();
		int button = e.getButton();
		
		if(pressed){
			if(inside) actionListener.onAction();
			buttonListener.buttonReleased(this);
			pressed = false;
			return true;
		}
		
		return false;
	}

	public void setButtonListener(UIButtonListener buttonListener){
		this.buttonListener = buttonListener;
	}

	@Override
	public void render(Graphics2D g){
		g.setColor(color);
		g.setStroke(new BasicStroke(3));
		g.drawRect(getAbsolutePosition().getX(), getAbsolutePosition().getY(), width, height);
		g.setStroke(new BasicStroke(1));
	}

	public void setText(String text){
		label.text = text;
	}

	public void setColor(Color color){
		this.color = color;
		label.setColor(color);
	}
	
	public boolean hasInside(int xp, int yp){
		int w = this.width;
		int h = this.height;
		if((w | h) < 0) return false;

		int x = getAbsolutePosition().getX();
		int y = getAbsolutePosition().getY();

		if(xp < x || yp < y) return false;
		w += x;
		h += y;

		return ((w < x || w > xp) && (h < y || h > yp));
	}

}
