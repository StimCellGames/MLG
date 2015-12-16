package com.mygdx.game.states.gamestates;

import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.states.State;

public class Play extends State{
	private Sprite sprite;
	private SpriteBatch batch;
	public static World world;
	
	private Box2DDebugRenderer renderer;
	
	private FPSLogger logger;
	
	
	public Play() {
		renderer = new Box2DDebugRenderer();
		logger = new FPSLogger();

		world = new World(new Vector2(0, -9.8f * 15), false);
		
		

		batch = new SpriteBatch();
		sprite = new Sprite(new Texture("res/download.png"));
		
		
		

	}
	
	
	public void render(OrthographicCamera camera) {

		
		
		

		

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		sprite.draw(batch);
		batch.end();

		renderer.render(world, camera.combined);

		
		logger.log();
		
		world.step(1/60f, 6, 2);
		
	}

	public void update(OrthographicCamera camera) {
		
	}
	
	public void onClose() {
		world.dispose();

	}

	public int getID() {
		return 0;
	}

}
