package com.mygdx.game.states;

import com.badlogic.gdx.graphics.Camera;

public abstract class State {
	
	
	public abstract void render(Camera camera);
	public abstract void update(Camera camera);
	public abstract int getID();
	
	
	
	protected void onClose() {
		
	}
}
