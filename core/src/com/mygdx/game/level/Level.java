package com.mygdx.game.level;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.Game;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.GameObject;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

//using Tiled map editor
public class Level {

	private String path;
	private World world;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapDrawer;

	private ArrayList<Entity> entities = new ArrayList<Entity>();

	
	public Level(String path, World world) {
		this.path = path;
		this.world = world;

		map = loadMap(path);
		mapDrawer = new OrthogonalTiledMapRenderer(map,1/Game.scale);
		
		GameObject floor = new GameObject(BodyType.StaticBody,world,Gdx.graphics.getWidth()/Game.scale,1);
		floor.addBodyDef(0, 0, Gdx.graphics.getWidth()/Game.scale,0.1f, 4, 4, 0);
		
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("Tile Layer 1");
		
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

	}
	
	private static TiledMap loadMap(String path) {
		return new TmxMapLoader().load("res/map.tmx");
	}
	
	public void render(OrthographicCamera camera) {
		mapDrawer.setView(camera);
		mapDrawer.render();

		
	}
	
	public TiledMap getTiledMap() {
		return map;
	}
	
	public void dispose() {
		map.dispose();
		mapDrawer.dispose();
	}
	
	


	
}
