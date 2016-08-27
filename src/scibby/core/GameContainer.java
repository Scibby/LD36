package scibby.core;

import javax.swing.JFrame;

import scibby.events.Event;
import scibby.events.EventListener;
import scibby.graphics.Display;
import scibby.input.Keyboard;
import scibby.input.Mouse;
import scibby.states.GameStateManager;
import scibby.ui.UIManager;

public class GameContainer extends JFrame implements Runnable, EventListener{

	private Thread thread;

	private boolean running = false;

	private int width;

	private int height;

	private int maxFrames;

	private final String TITLE;

	private Display disp;
	
	private static UIManager uiManager;
	
	public GameContainer(int width, int height, int maxFrames, String title){
		this.width = width;
		this.height = height;
		this.maxFrames = maxFrames;
		this.TITLE = title;

		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		init();
	}

	private void init(){
		disp = new Display(width, height, this);
		add(disp);
		pack();
		
		uiManager = new UIManager(this);
		
		Mouse mouse = new Mouse(this);
		disp.addMouseListener(mouse);
		disp.addMouseMotionListener(mouse);
		disp.addKeyListener(new Keyboard());
	}
	
	public void onEvent(Event event){
		GameStateManager.getCurrentState().onEvent(event);
	}
	
	private void tick(){
		GameStateManager.getCurrentState().tick();
	}

	@Override
	public void run(){
		while(running){

			long initialNanoTime = System.nanoTime();
			double ticksPerSecond = 1000000000 / maxFrames;
			double delta = 0;
			int updates = 0;
			int frames = 0;
			long milli = System.currentTimeMillis();
			while(running){
				long now = System.nanoTime();
				delta += (now - initialNanoTime) / ticksPerSecond;
				initialNanoTime = now;
				if(delta >= 1){
					tick();
					updates++;
					disp.render();
					frames++;
					delta--;
				}

				if(System.currentTimeMillis() - milli > 1000){
					milli += 1000;
					//System.out.println("Updates: " + updates + ", frames: " + frames);
					updates = 0;
					frames = 0;
				}
			}
		}

	}

	public synchronized void start(){
		if(running) return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop(){
		if(!running) return;

		running = false;
		try{
			thread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}

		System.exit(1);
	}

	public Display getDisp(){
		return disp;
	}
	
	static UIManager getUI(){
		return uiManager;
	}
	
	public String getTitle(){
		return TITLE;
	}
}
