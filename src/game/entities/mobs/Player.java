package game.entities.mobs;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import game.Images;
import game.entities.projectiles.Bullet;
import scibby.entities.Mob;
import scibby.events.Event;
import scibby.events.EventDispatcher;
import scibby.events.types.MousePressedEvent;
import scibby.events.types.MouseReleasedEvent;
import scibby.graphics.Animation;
import scibby.input.Keyboard;
import scibby.input.Mouse;
import scibby.level.Level;

public class Player extends Mob{

	private int speed = 5;
	
	private int health = 100;

	private Animation walkDown = new Animation(8, Images.playerWalkDown);
	private Animation walkLeft = new Animation(6, Images.playerWalkLeft);
	private Animation walkUp = new Animation(8, Images.playerWalkUp);
	private Animation walkRight = new Animation(6, Images.playerWalkRight);

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
		}
		if(Keyboard.isKeyPressed(KeyEvent.VK_A)){
			xa -= speed;
			walkLeft.runAnimation();
			facing = Direction.LEFT;
		}
		if(Keyboard.isKeyPressed(KeyEvent.VK_S)){
			ya += speed;
			walkDown.runAnimation();
			facing = Direction.DOWN;
		}
		if(Keyboard.isKeyPressed(KeyEvent.VK_D)){
			xa += speed;
			walkRight.runAnimation();
			facing = Direction.RIGHT;
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
	}

	private void updateShooting(){
		int mx = Mouse.getX();
		int my = Mouse.getY();

		double dx = mx - (x + width / 2) + Level.getCurrentLevel().getCamera().camX;
		double dy = my - (y + height / 2) + Level.getCurrentLevel().getCamera().camY;

		double dir = Math.atan2(dy, dx);

		if(dir > (Math.PI / 4) && dir < (Math.PI / 4 * 3)){
			facing = Direction.DOWN;
		}else if(dir > -(Math.PI) && dir < -(Math.PI / 4 * 3)  || dir > (Math.PI / 4 * 3)){
			facing = Direction.LEFT;
		}else if(dir > -(Math.PI / 4) && dir < (Math.PI)){
			facing = Direction.RIGHT;
		}else if(dir > -(Math.PI / 4 * 3) && dir < -(Math.PI / 4)){
			facing = Direction.UP;
		}
		
		shoot(dir);
	}

	private void shoot(double angle){
		if(rate <= 0){
			if(facing == Direction.LEFT){
				new Bullet((x + width / 2) - 20, (y + height / 2) + 8, 8, 8, angle, 2000, this, Images.bullet);				
			}else if(facing == Direction.DOWN){
				new Bullet((x + width / 2) - 20, (y + height / 2) - 8, 8, 8, angle, 2000, this, Images.bullet);			
			}else if(facing == Direction.RIGHT){
				new Bullet((x + width / 2) + 20, (y + height / 2) + 8, 8, 8, angle, 2000, this, Images.bullet);				
			}else if(facing == Direction.UP){
				new Bullet((x + width / 2) + 20, y, 8, 8, angle, 2000, this, Images.bullet);				
			}
			rate = Bullet.RATE_OF_FIRE;
			System.out.println(angle);
		}
	}

	@Override
	public void onEvent(Event event){
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressedEvent((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleasedEvent((MouseReleasedEvent) e));
	}

	private boolean onMousePressedEvent(MousePressedEvent e){

		if(e.getButton() == MouseEvent.BUTTON1){
			shooting = true;
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
