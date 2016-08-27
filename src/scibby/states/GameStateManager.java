package scibby.states;

import java.awt.Graphics2D;
import java.util.LinkedList;

public class GameStateManager{

	private static LinkedList<State> stateList = new LinkedList<State>();

	public static int currentState;

	private GameStateManager(){}

	public static void tick(){
		for(int i = 0; i < stateList.size(); i++){
			if(stateList.get(i).getID() == currentState){
				stateList.get(i).tick();
			}
		}
	}

	public static void render(Graphics2D g){
		for(int i = 0; i < stateList.size(); i++){
			if(stateList.get(i).getID() == currentState){
				stateList.get(i).render(g);
			}
		}
	}

	public static void addState(State state){
		stateList.add(state);
	}

	public static void removeState(State state){
		stateList.remove(state);
	}

	public static State getCurrentState(){
		if(stateList.isEmpty()) return null;
		return stateList.get(currentState);
	}

}
