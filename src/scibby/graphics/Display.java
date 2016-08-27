package scibby.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import scibby.core.GameContainer;
import scibby.states.GameStateManager;

public class Display extends Canvas{

	private int width;

	private int height;

	public Display(final int width, final int height, GameContainer gc){

		this.width = width;
		this.height = height;

		Dimension dim = new Dimension(width, height);
		setMinimumSize(dim);
		setPreferredSize(dim);
		setMaximumSize(dim);
	}

	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);

		GameStateManager.getCurrentState().render(g);
		
		g.dispose();

		bs.show();
	}
}
