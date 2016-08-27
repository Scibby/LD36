package scibby.ui.components;

import java.awt.Color;
import java.awt.Graphics2D;

import scibby.ui.UIComponent;
import scibby.ui.UIPanel;
import scibby.util.MathsUtil;
import scibby.util.Vector2i;

public class UIProgressBar extends UIComponent{

	public final int MIN_VALUE, MAX_VALUE;

	private int value = 50;

	private Color bColor = Color.RED.darker();
	private Color fColor = Color.GREEN.darker();

	public UIProgressBar(Vector2i position, int width, int height, UIPanel parent, int min, int max){
		super(position, width, height, parent);
		this.MIN_VALUE = min;
		this.MAX_VALUE = max;
	}

	public UIProgressBar(int x, int y, int width, int height, UIPanel parent, int min, int max){
		this(new Vector2i(x, y), width, height, parent, min, max);
	}

	@Override
	public void tick(){
		value = MathsUtil.clamp(value, MIN_VALUE, MAX_VALUE);
	}

	@Override
	public void render(Graphics2D g){
		g.setColor(bColor);
		g.fillRect(getAbsolutePosition().getX(), getAbsolutePosition().getY(), width, height);
		g.setColor(fColor);
		g.fillRect(getAbsolutePosition().getX(), getAbsolutePosition().getY(), (int) getFill(), height);
	}

	public void setValue(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setBackgroundColor(Color bColor){
		this.bColor = bColor;
	}
	
	public void setForegroundColor(Color fColor){
		this.fColor = fColor;
	}

	private double getFill(){
		return (double) width / (double) MAX_VALUE * (double) value;
	}
	
}
