package com.mygdx.game.states.gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Game;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.GameObject;
import com.mygdx.game.entity.player.Player;
import com.mygdx.game.level.Level;
import com.mygdx.game.states.State;
import com.mygdx.game.tiles.Tile;

public class Play extends State{
	private Sprite sprite;
	private SpriteBatch batch;
	public static World world;

	private Box2DDebugRenderer renderer;

	private FPSLogger logger;


	private Level level;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private Player player;
	public Play() {
		





		renderer = new Box2DDebugRenderer();
		logger = new FPSLogger();

		world = new World(new Vector2(0, -9.8f * 15), false);

		Tile.initTiles(world);
		batch = new SpriteBatch();
		sprite = new Sprite(new Texture("res/download.png"));

		

		
		level = new Level("res/map.tmx",world);

		player = new Player(world);

	}

	public void render(OrthographicCamera camera) {

		level.render(camera);
		camera.update();
		if(Gdx.input.isButtonPressed(0)) {
			GameObject en = new GameObject(sprite,BodyType.DynamicBody, world, 32/Game.scale,32/Game.scale);
			en.addBodyDef(0, 0,32/Game.scale, 32/Game.scale, 4, 0, 0);
			en.setX(Gdx.input.getX()/Game.scale);
			en.setY((Gdx.graphics.getHeight() - Gdx.input.getY())/Game.scale);
			entities.add(en);

		}

		






		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for(Entity e : entities) {
			e.render(camera, batch);
		}
		//sprite.draw(batch);
		player.render(camera, batch);
		batch.end();

		renderer.render(world, camera.combined);




		
		 
		logger.log();

		world.step(1/60f, 6, 2);
		
	}
	public void update(OrthographicCamera camera) {
		player.update(camera);
	}

	public void onClose() {
		world.dispose();
		level.dispose();
		batch.dispose();

	}

	public int getID() {
		return 0;
	}

}
