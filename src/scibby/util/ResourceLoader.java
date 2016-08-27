package scibby.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class ResourceLoader{

	public final static String IMAGE_LOCATION = "/images/";

	public final static String LEVEL_LOCATION = "/levels/";

	public final static String FONT_LOCATION = "/fonts/";

	public final static String AUDIO_LOCATION = "/audio/";

	public final static String IMAGE_ENDING = ".png";

	public final static String LEVEL_ENDING = ".csv";

	public final static String FONT_ENDING = ".ttf";

	public final static String AUDIO_ENDING = ".wav";

	public BufferedImage loadImage(String imageName){
		
		if(imageName == null) throw new IllegalArgumentException("argument cannot be null!");
		
		BufferedImage image = null;
		try{
			image = ImageIO.read(getClass().getResource(IMAGE_LOCATION + imageName + IMAGE_ENDING));
		}catch(IOException e){
			e.printStackTrace();
		}
		return image;
	}

	public int[] loadLevel(String levelName, int width, int height){

		if(levelName == null) throw new IllegalArgumentException("argument cannot be null!");
		
		int[] levelMap = new int[width * height];

		BufferedReader br = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream(LEVEL_LOCATION + levelName + LEVEL_ENDING)));

		String rawLine = null;
		
		int j = 0;
		try{
			while((rawLine = br.readLine()) != null){
				
				String[] nums = rawLine.split(",");
				
				for(int i = 0; i < width; i++){
					levelMap[i + j * width] = Integer.parseInt(nums[i]);
				}
				
				j++;
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}

		return levelMap;
	}

	public AudioInputStream loadAudio(String audioName){
		
		if(audioName == null) throw new IllegalArgumentException("argument cannot be null!");;
		
		AudioInputStream ais = null;
		try{
			ais = AudioSystem.getAudioInputStream(getClass().getResource(AUDIO_LOCATION + audioName + AUDIO_ENDING));
		}catch(Exception e){
			e.printStackTrace();
		}

		return ais;
	}
}
