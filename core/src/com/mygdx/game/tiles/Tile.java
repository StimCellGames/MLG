package com.mygdx.game.tiles;

import java.util.Arrays;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Game;
import com.mygdx.game.entity.Entity;

public class Tile extends Entity{
	public static final float TILE_SIZE = (int) (16/Game.scale);

	private int ID;
	private static boolean isClone = false;

	public static final int VOID_TILE = 0, ROCK_TILE = 1,DIRT_TILE = 2,FLOWER_TILE = 3;

	public static Tile empty;
	public static Tile rock;
	public static Tile flower;
	public static Tile dirt;

	public static Tile[] tiles; 

	public static final int SOLID = 0, BREAKABLE = 1;
	private boolean[] attribs = {false, false};

	private boolean toRender = false;

	private int xCrop,yCrop;
	public String path;
	private static Sprite tileSheet;

	public boolean flaggedForDelete = false;
	boolean on = true;
	
	public Tile(World world, int ID) {
		super(BodyType.StaticBody, world, TILE_SIZE/2, TILE_SIZE/2);
		this.ID = ID;
		setID(ID,true);
		
	}
	
	public static void initTiles(World world) {
		empty = new Tile(world,VOID_TILE);
		dirt = new Tile(world,DIRT_TILE);
		rock = new Tile(world,ROCK_TILE);
		flower = new Tile(world,FLOWER_TILE);
		

		tiles = new Tile[] {empty,rock,dirt,flower};
			for(int i = 0;i < tiles.length;i++) {
				tileSheet = new Sprite(new Texture(tiles[i].path));
				tileSheet.setRegion(tiles[i].getxCrop(),tiles[i].getyCrop(),16,16);
				tiles[i].setSprite(tileSheet);
				//tiles[i].delete();

			}
		
		isClone = true;

	}

	
	public void render(Camera camera, SpriteBatch batch) {
		if(sprite!=null && toRender==true && ID!=VOID_TILE) {
				sprite.setPosition(x- sprite.getWidth()/2,y- sprite.getWidth()/2);
				sprite.setSize(TILE_SIZE,TILE_SIZE);
				sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
				sprite.draw(batch);
			
		}
	}

	
	public void update(Camera camera) {
		
	}
	
	private void setID(int iD,boolean createBody) {
		this.ID = iD;
		flaggedForDelete = false;

		Arrays.fill(attribs, false);
		toRender = false;

		if(ID == VOID_TILE) {
			toRender = false;
			width = height = 32;

			path = ("res/Textures/Spritesheet.png");

		}

		if(ID == ROCK_TILE) {

			toRender = true;
			attribs[SOLID] = true;
			attribs[BREAKABLE] = true;

			xCrop = 32;
			yCrop = 0;
			width = height = 32;

			path = ("res/Textures/Spritesheet.png");
		}

		if(ID == DIRT_TILE) {

			toRender = true;
			attribs[SOLID] = true;
			attribs[BREAKABLE] = true;

			xCrop = yCrop = 0;
			width = height = 32;

			path = ("res/Textures/Spritesheet.png");
		}

		if(ID == FLOWER_TILE) {
			toRender = true;

			xCrop = 16;
			yCrop = 0;
			width = height = 16;	

			path = ("res/Textures/Spritesheet.png");

		}
		if(attribs[SOLID] && createBody)addBodyDef(0,0,TILE_SIZE/2, TILE_SIZE/2, 0,0,0);

		if(isClone){
			setSprite(tiles[ID].getSprite());
			sprite.setRegion(xCrop, yCrop, 16,16);
		}
	}
	
	public int getID() {
		return ID;
	}

	public int getxCrop() {
		return xCrop;
	}

	public int getyCrop() {
		return yCrop;
	}
	
	public void delete() {
		//body.setActive(false);
		flaggedForDelete = true;
		//setID(VOID_TILE);
	}
	public void deleteBody(World world) {
		if(on && body!=null) {
			world.destroyBody(body);
			on=false;
		}
	
	}
	
}
