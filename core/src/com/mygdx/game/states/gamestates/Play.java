package com.mygdx.game.states.gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Game;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.GameObject;
import com.mygdx.game.states.State;
import com.mygdx.game.tiles.Tile;

public class Play extends State{
	private Sprite sprite;
	private SpriteBatch batch;
	public static World world;
	
	private Box2DDebugRenderer renderer;
	
	private FPSLogger logger;
	Tile tile;
	public Play() {
		renderer = new Box2DDebugRenderer();
		logger = new FPSLogger();

		world = new World(new Vector2(0, -9.8f * 15), false);
		
		Tile.initTiles(world);
		batch = new SpriteBatch();
		sprite = new Sprite(new Texture("res/download.png"));
		
		GameObject floor = new GameObject(BodyType.StaticBody,world,Gdx.graphics.getWidth()/Game.scale,1);
		floor.addBodyDef(0, 0, Gdx.graphics.getWidth()/Game.scale,0, 4, 4, 0);
		//floor.setY(25);
		
		tile = new Tile(world,Tile.DIRT_TILE);

	}
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	public void render(Camera camera) {
		camera.update();
		if(Gdx.input.isButtonPressed(0)) {
			GameObject en = new GameObject(BodyType.DynamicBody, world, 1/Game.scale,1/Game.scale);
			en.addBodyDef(0, 0,1/Game.scale, 1/Game.scale, 4, 100, 0);
			en.setX(Gdx.input.getX()/Game.scale);
			en.setY((Gdx.graphics.getHeight() - Gdx.input.getY())/Game.scale);
			entities.add(en);
			
		}
		
		
		

		

	batch.setProjectionMatrix(camera.combined);
		batch.begin();
		tile.setX(100);
		tile.setY(100);

		tile.render(camera, batch);
		for(Entity e : entities) {
			e.render(camera, batch);
		}
		//sprite.draw(batch);
		batch.end();
		
		

		

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		

		batch.end();

		renderer.render(world, camera.combined);

		
		logger.log();
		
		world.step(1/60f, 6, 2);
		
		
	}
	public void update(Camera camera) {

	}
	
	public void onClose() {
		world.dispose();

	}

	public int getID() {
		return 0;
	}

}
