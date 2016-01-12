package com.mygdx.game.level;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Game;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.GameObject;
import com.mygdx.game.entity.Missile;
import com.mygdx.game.entity.mob.Loominati;
import com.mygdx.game.level.Classifications.ObjType;
import com.mygdx.game.physics.Physics;

//using Tiled map editor
public class Level {

	private String path;
	private World world;

	private TiledMap map;
	private OrthogonalTiledMapRenderer mapDrawer;
	private ShapeRenderer sr;

	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private float tileWidth, tileHeight;
	
	private Loominati confirmed;

	public Level(String path, SpriteBatch batch, final World world) {

		this.path = path;
		this.world = world;

		map = loadMap(path);

		mapDrawer = new OrthogonalTiledMapRenderer(map, 1 / Game.scale, batch);
		sr = new ShapeRenderer();
		GameObject floor = new GameObject(BodyType.StaticBody, world, Gdx.graphics.getWidth() / Game.scale, 1);
		floor.addBodyDef(0, -10, Gdx.graphics.getWidth() / Game.scale, 10f, 4, 4, 0);
		floor.setType(ObjType.unMovable);

		addLayer("Solid", ObjType.Tile);
		// addLayer("unMovable",ObjType.unMovable);

		confirmed = new Loominati(world);

	}

	public void addLayer(String layerName, ObjType type) {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerName);
		tileWidth = layer.getTileWidth() / 2;
		tileHeight = layer.getTileHeight() / 2;

		for (int row = 0; row < layer.getHeight(); row++) {
			for (int col = 0; col < layer.getWidth(); col++) {
				Cell cell = layer.getCell(col, row);

				if (cell == null || cell.getTile() == null)
					continue;
				GameObject object = new GameObject(new Sprite(cell.getTile().getTextureRegion()), BodyType.StaticBody,
						world, tileWidth / 2 / Game.scale, tileHeight / 2 / Game.scale);
				object.addBodyDef(0, 0, tileWidth / 2 / Game.scale, tileHeight / 2 / Game.scale, 1, 1, 0f);
				object.setX((col * tileWidth + tileWidth / 2) / Game.scale);
				object.setY((row * tileHeight + tileHeight / 2) / Game.scale);
				object.setType(type);
				entities.add(object);
			}
		}
	}

	private static TiledMap loadMap(String path) {
		return new TmxMapLoader().load("res/map.tmx");
	}

	public void render(SpriteBatch batch, OrthographicCamera camera) {
		mapDrawer.setView(camera);
		// mapDrawer.render();
		mapDrawer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("stuff"));

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(camera, batch);
		}
		confirmed.render(camera, batch);

	}

	private ArrayList<Vector2> implosions = new ArrayList<Vector2>();

	public void update(OrthographicCamera camera) {

		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			float x = (Gdx.input.getX()) / Game.scale + camera.position.x - (Gdx.graphics.getWidth() / 2) / Game.scale;
			float y = (Gdx.graphics.getHeight() - Gdx.input.getY()) / Game.scale + camera.position.y
					- (Gdx.graphics.getHeight() / 2) / Game.scale;

			Physics.explosion(world, -0.1f, x, y);

		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
			float x = (Gdx.input.getX()) / Game.scale + camera.position.x - (Gdx.graphics.getWidth() / 2) / Game.scale;
			float y = (Gdx.graphics.getHeight() - Gdx.input.getY()) / Game.scale + camera.position.y
					- (Gdx.graphics.getHeight() / 2) / Game.scale;
			implosions.add(new Vector2(x, y));
		}

		for (Vector2 vec2 : implosions) {
			Physics.explosion(world, -1f, vec2.x, vec2.y);

		}

		confirmed.update(camera);
	}

	public TiledMap getTiledMap() {
		return map;
	}

	public void dispose() {
		map.dispose();
		mapDrawer.dispose();
	}
}
