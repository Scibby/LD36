package scibby.input;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import scibby.events.EventListener;
import scibby.events.types.MouseMovedEvent;
import scibby.events.types.MousePressedEvent;
import scibby.events.types.MouseReleasedEvent;

public class Mouse implements MouseInputListener{

	@Deprecated
	public static Rectangle MOUSE = new Rectangle(0, 0, 1, 1);

	@Deprecated
	private static boolean buttons[] = new boolean[65535];

	@Deprecated
	private static int mx;

	@Deprecated
	private static int my;
	
	private EventListener listener;
	
	public Mouse(EventListener listener){
		this.listener = listener;
	}
	
	@Override
	public void mousePressed(MouseEvent e){
		buttons[e.getButton()] = true;
		MousePressedEvent event = new MousePressedEvent(e.getButton(), e.getX(), e.getY());
		listener.onEvent(event);
	}

	@Override
	public void mouseReleased(MouseEvent e){
		buttons[e.getButton()] = false;
		MouseReleasedEvent event = new MouseReleasedEvent(e.getButton(), e.getX(), e.getY());
		listener.onEvent(event);
	}

	@Override
	public void mouseMoved(MouseEvent e){
		MOUSE.setLocation(e.getX(), e.getY());
		mx = e.getX();
		my = e.getY();
		MouseMovedEvent event = new MouseMovedEvent(e.getX(), e.getY(), false);
		listener.onEvent(event);
	}

	@Override
	public void mouseDragged(MouseEvent e){
		MOUSE.setLocation(e.getX(), e.getY());
		mx = e.getX();
		my = e.getY();
		MouseMovedEvent event = new MouseMovedEvent(e.getX(), e.getY(), true);
		listener.onEvent(event);
	}
	
	public static int getX(){
		return mx;
	}
	
	public static int getY(){
		return my;
	}

	@Deprecated
	public static boolean isButtonPressed(int button){
		return buttons[button];
	}
	
	@Override
	public void mouseClicked(MouseEvent e){}

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e){}

}
