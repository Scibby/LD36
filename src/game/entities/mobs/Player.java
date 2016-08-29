package game.entities.mobs;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import game.Images;
import game.Main;
import game.entities.projectiles.Bullet;
import game.states.GameState;
import scibby.entities.Mob;
import scibby.events.Event;
import scibby.events.EventDispatcher;
import scibby.events.types.MousePressedEvent;
import scibby.events.types.MouseReleasedEvent;
import scibby.graphics.Animation;
import scibby.input.Keyboard;
import scibby.input.Mouse;
import scibby.level.Level;
import scibby.states.GameStateManager;
import scibby.ui.components.UIProgressBar;
import scibby.util.AudioPlayer;

public class Player extends Mob{

	private int speed = 5;

	public int health = 100;

	private double shots = 0;

	public double hits = 0;

	public int accuracy = 0;

	private Animation walkDown = new Animation(8, Images.playerWalkDown);
	private Animation walkLeft = new Animation(6, Images.playerWalkLeft);
	private Animation walkUp = new Animation(8, Images.playerWalkUp);
	private Animation walkRight = new Animation(6, Images.playerWalkRight);

	private AudioPlayer shootSFX = new AudioPlayer("shootEffect");

	public boolean isInvincible = true;
	public int invinsible;

	public enum Direction{
		UP, DOWN, LEFT, RIGHT;
	}

	private boolean shooting = false;

	private int rate;

	private Direction facing = Direction.DOWN;

	public Player(int x, int y, int width, int height, BufferedImage image){
		super(x, y, width, height, image);
	}

	@Override
	public void tick(){
		super.tick();

		double xa = 0, ya = 0;

		if(Keyboard.isKeyPressed(KeyEvent.VK_W)){
			ya -= speed;
			walkUp.runAnimation();
			facing = Direction.UP;
			isInvincible = false;
		}
		if(Keyboard.isKeyPressed(KeyEvent.VK_S)){
			ya += speed;
			walkDown.runAnimation();
			facing = Direction.DOWN;
			isInvincible = false;
		}
		if(Keyboard.isKeyPressed(KeyEvent.VK_D)){
			xa += speed;
			walkRight.runAnimation();
			facing = Direction.RIGHT;
			isInvincible = false;
		}
		if(Keyboard.isKeyPressed(KeyEvent.VK_A)){
			xa -= speed;
			walkLeft.runAnimation();
			facing = Direction.LEFT;
			isInvincible = false;
		}

		move(xa, ya);

		if(shooting){
			updateShooting();
		}
		rate--;

		if(x < 0) x = 0;
		if(y < 0) y = 0;
		if(x + width >= Level.getCurrentLevel().getWidth() * Level.getCurrentLevel().getTileSize()){
			x = Level.getCurrentLevel().getWidth() * Level.getCurrentLevel().getTileSize() - width;
		}
		if(y + height >= Level.getCurrentLevel().getHeight() * Level.getCurrentLevel().getTileSize()){
			y = Level.getCurrentLevel().getHeight() * Level.getCurrentLevel().getTileSize() - height;
		}

		UIProgressBar healthBar = GameState.healthBar;
		healthBar.setValue(health);

		System.out.println(isInvincible);
		if(!isInvincible){
			invinsible--;
		}

		if(health <= 0){
			Main.gameOverState.start();
			GameStateManager.currentState = 2;
		}

		GameState.shots.text = "Shots: " + shots;
		GameState.hits.text = "Hits: " + hits;
		if(hits != 0) accuracy = (int) (hits / shots * 100);

		GameState.accuracy.text = "Accuracy: " + accuracy + "%";

	}

	private void updateShooting(){
		int mx = Mouse.getX();
		int my = Mouse.getY();

		double dx = mx - (x + width / 2) + Level.getCurrentLevel().getCamera().camX;
		double dy = my - (y + height / 2) + Level.getCurrentLevel().getCamera().camY;

		double dir = Math.atan2(dy, dx);

		if(dir > (Math.PI / 4) && dir < (Math.PI / 4 * 3)){
			facing = Direction.DOWN;
		}else if(dir > -(Math.PI) && dir < -(Math.PI / 4 * 3) || dir > (Math.PI / 4 * 3)){
			facing = Direction.LEFT;
		}else if(dir > -(Math.PI / 4) && dir < (Math.PI)){
			facing = Direction.RIGHT;
		}else if(dir > -(Math.PI / 4 * 3) && dir < -(Math.PI / 4)){
			facing = Direction.UP;
		}

		shoot();
	}

	private void shoot(){
		int mx = Mouse.getX();
		int my = Mouse.getY();
		double nx = 0, ny = 0;
		double dx, dy;
		if(rate <= 0){
			if(facing == Direction.LEFT){
				nx = (x + width / 2) - 20;
				ny = (y + height / 2) + 8;
			}else if(facing == Direction.DOWN){
				nx = (x + width / 2) - 20;
				ny = (y + height / 2) - 8;
			}else if(facing == Direction.RIGHT){
				nx = (x + width / 2) + 20;
				ny = (y + height / 2) + 8;
			}else if(facing == Direction.UP){
				nx = (x + width / 2) + 20;
				ny = y + 8;
			}

			dx = mx - nx + Level.getCurrentLevel().getCamera().camX;
			dy = my - ny + Level.getCurrentLevel().getCamera().camY;

			double angle = Math.atan2(dy, dx);

			new Bullet(nx, ny, 8, 8, angle, 2000, this, Images.bullet);

			shootSFX.play();

			shots++;

			rate = Bullet.RATE_OF_FIRE;
			//System.out.println(angle);
		}
	}

	@Override
	public void onEvent(Event event){
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressedEvent((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleasedEvent((MouseReleasedEvent) e));
		//isInvincible = false;
	}

	private boolean onMousePressedEvent(MousePressedEvent e){

		if(e.getButton() == MouseEvent.BUTTON1){
			if(!isInvincible) shooting = true;
			return true;
		}

		return false;
	}

	private boolean onMouseReleasedEvent(MouseReleasedEvent e){
		if(e.getButton() == MouseEvent.BUTTON1){
			shooting = false;
			return true;
		}
		return false;
	}

	@Override
	public void render(Graphics2D g){
		if(Keyboard.isKeyPressed(KeyEvent.VK_W)){
			walkUp.drawAnimation(g, (int) x, (int) y, width, height);
		}else if(Keyboard.isKeyPressed(KeyEvent.VK_S)){
			walkDown.drawAnimation(g, (int) x, (int) y, width, height);
		}else if(Keyboard.isKeyPressed(KeyEvent.VK_A)){
			walkLeft.drawAnimation(g, (int) x, (int) y, width, height);
		}else if(Keyboard.isKeyPressed(KeyEvent.VK_D)){
			walkRight.drawAnimation(g, (int) x, (int) y, width, height);
		}else{
			switch(facing){
				case UP:
					g.drawImage(Images.playerIdle[3], (int) x, (int) y, width, height, null);
					break;
				case LEFT:
					g.drawImage(Images.playerIdle[1], (int) x, (int) y, width, height, null);
					break;
				case RIGHT:
					g.drawImage(Images.playerIdle[2], (int) x, (int) y, width, height, null);
					break;
				case DOWN:
					g.drawImage(Images.playerIdle[0], (int) x, (int) y, width, height, null);
					break;
			}
		}
	}
}
