package scibby.util;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class Fonts{

	private String fileLocation;

	public Fonts(String fileName){
		if(fileName == null) throw new IllegalArgumentException("argument cannot be null!");
		this.fileLocation = ResourceLoader.FONT_LOCATION + fileName + ResourceLoader.FONT_ENDING;
		registerFont();
	}

	private void registerFont(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try{
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(fileLocation)));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}