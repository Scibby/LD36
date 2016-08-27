package scibby.states;

import java.awt.Graphics2D;
import java.util.ArrayList;

import scibby.events.Event;
import scibby.layer.Layer;

public abstract class State{

	protected final int stateId;
	protected ArrayList<Layer> layers = new ArrayList<Layer>();

	public State(int stateId){
		this.stateId = stateId;
	}

	public void start(){}

	public void tick(){
		for(int i = 0; i < layers.size(); i++){
			layers.get(i).tick();
		}
	}

	public void render(Graphics2D g){
		for(int i = 0; i < layers.size(); i++){
			layers.get(i).render(g);
		}
	}

	public void onEvent(Event event){
		for(int i = layers.size() - 1; i >= 0; i--){
			layers.get(i).onEvent(event);
		}
	}

	public void addLayer(Layer layer){
		layers.add(layer);
	}

	public void removeLayer(Layer layer){
		layers.remove(layer);
	}

	public void clearLayers(){
		layers.clear();
	}

	public ArrayList<Layer> getLayers(){
		return layers;
	}
	
	public int getID(){
		return stateId;
	}
}
