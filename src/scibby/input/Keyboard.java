package scibby.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{

	private static boolean[] keys = new boolean[65536];
	
	@Override
	public void keyPressed(KeyEvent e){
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e){
		keys[e.getKeyCode()] = false;
	}

	public static boolean isKeyPressed(int key){
		return keys[key];
	}
	
	public static void releaseKeys(){
		for(boolean k : keys){
			k = false;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e){}

}
