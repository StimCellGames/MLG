package com.mygdx.game.states.gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Game;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.GameObject;
import com.mygdx.game.entity.player.Player;
import com.mygdx.game.states.State;
import com.mygdx.game.tiles.Tile;

public class Play extends State{
	private Sprite sprite;
	private SpriteBatch batch;
	public static World world;

	private Box2DDebugRenderer renderer;

	private FPSLogger logger;


	private TiledMap map;
	private OrthogonalTiledMapRenderer mapDrawer;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private Player player;
	public Play() {
		TmxMapLoader loader = new TmxMapLoader();
		map = loader.load("res/map.tmx");

		mapDrawer = new OrthogonalTiledMapRenderer(map,1/Game.scale);

		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("Tile Layer 1");




		renderer = new Box2DDebugRenderer();
		logger = new FPSLogger();

		world = new World(new Vector2(0, -9.8f * 15), false);

		Tile.initTiles(world);
		batch = new SpriteBatch();
		sprite = new Sprite(new Texture("res/download.png"));

		GameObject floor = new GameObject(BodyType.StaticBody,world,Gdx.graphics.getWidth()/Game.scale,1);
		floor.addBodyDef(0, 0, Gdx.graphics.getWidth()/Game.scale,0.1f, 4, 4, 0);

		for(int row = 0; row < layer.getHeight();row++) {
			for(int col = 0; col < layer.getWidth();col++) {
				Cell cell = layer.getCell(col, row);
				

				if(cell==null || cell.getTile()==null) continue;
			
				
				if( ((String) cell.getTile().getProperties().get("solid")).equals("true")){
					GameObject object = new GameObject(BodyType.StaticBody,world,32,32);
					object.addBodyDef(0,0, layer.getTileWidth()/2/Game.scale, layer.getTileHeight()/2/Game.scale, 1, 0, 0);
					object.setX((col * layer.getTileWidth() + layer.getTileWidth()/2)/Game.scale);
					object.setY((row * layer.getTileHeight() + layer.getTileWidth()/2)/Game.scale) ;
					//entities.add(object);
				}
				

			}
		}
		
		player = new Player(world);

	}

	public void render(OrthographicCamera camera) {

		mapDrawer.render();

		mapDrawer.setView(camera);
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
		map.dispose();
		mapDrawer.dispose();
		batch.dispose();

	}

	public int getID() {
		return 0;
	}

}
