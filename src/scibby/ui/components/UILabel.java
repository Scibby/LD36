package scibby.ui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import scibby.ui.UIComponent;
import scibby.ui.UIPanel;
import scibby.util.Vector2i;

public class UILabel extends UIComponent{

	public String text;

	private Font font;

	private UIButton parentButton;
	
	private Color color = Color.RED;
	
	public UILabel(Vector2i position, int width, int height, UIPanel parent, String text, Font font){
		super(position, width, height, parent);
		this.text = text;
		this.font = font;
	}

	public UILabel(int x, int y, int width, int height, UIPanel parent, String text, Font font){
		this(new Vector2i(x, y), width, height, parent, text, font);
	}

	public UILabel(Vector2i position, int width, int height, UIPanel parent, String text, Font font, UIButton parentButton){
		this(position, width, height, parent, text, font);
		this.parentButton = parentButton;
	}

	public UILabel(int x, int y, int width, int height, UIPanel parent, String text, Font font, UIButton parentButton){
		this(new Vector2i(x, y), width, height, parent, text, font, parentButton);
	}
	
	@Override
	public void render(Graphics2D g){
		super.render(g);
		g.setColor(color);
		g.setFont(font);

		FontMetrics fm;

		fm = g.getFontMetrics(font);

		if(text == null) return;
		else if(parentButton != null){
			g.drawString(text, parentButton.getAbsolutePosition().getX() + parentButton.width / 2 - (fm.stringWidth(text) / 2),
					parentButton.getAbsolutePosition().getY() + parentButton.height / 2 + 12);
		}else{
			g.drawString(text, getAbsolutePosition().getX(), getAbsolutePosition().getY());
		}
		
	}
	
	public void setColor(Color color){
		this.color = color;
	}

}
