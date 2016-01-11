package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.states.StateManager;
import com.mygdx.game.states.gamestates.Play;

public class Game extends ApplicationAdapter {
	
	
	private Play play;
	public static final float scale = 25;
	
	private OrthographicCamera camera;
	private float width, height;
	public void create () {
		
		width = Gdx.graphics.getWidth()/scale;
		height = Gdx.graphics.getHeight()/scale;
		
		camera = new OrthographicCamera(width, height);
		camera.position.set(width*0.5f, height*0.5f, 0);
		camera.update();		
		
		
		play = new Play();
		StateManager.addState(play);
	}

	public void render () {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		
		
		
		StateManager.renderStates(camera);
		StateManager.updateStates(camera);
		
	}
	
	public void dispose() {
		StateManager.onClose();
	}
}
