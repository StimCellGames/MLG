package com.mygdx.game.entity.mob;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Game;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.level.Classifications.MobType;

public abstract class Mob extends Entity{
	protected float health, totalHealth;
	private Sprite healthBarSpriteBlack,healthBarSpriteRed;
	private MobType mobType;
	protected boolean dead = false;
	public Mob(BodyType type, World world, float width, float height,MobType mobType) {
		super(type, world, width, height);
		
		
		
		healthBarSpriteRed = new Sprite(new Texture("res/health.png"));
		healthBarSpriteRed.setRegion(0,0, (int)healthBarSpriteRed.getWidth(), (int)healthBarSpriteRed.getHeight()/2);
		healthBarSpriteRed.setSize(width*2, width*0.1f);
		
		
		healthBarSpriteBlack = new Sprite(new Texture("res/health.png"));
		healthBarSpriteBlack.setRegion(0,(int)healthBarSpriteBlack.getHeight()/2, (int)healthBarSpriteBlack.getWidth(), (int)healthBarSpriteBlack.getHeight());
		healthBarSpriteBlack.setSize(width*2, width*0.1f);
		this.mobType=mobType;
	}
	
	public void renderHealthBar(OrthographicCamera camera, SpriteBatch batch) {
		healthBarSpriteBlack.setY(getY() + height);
		healthBarSpriteBlack.setX(getX()- width);
		healthBarSpriteBlack.draw(batch);
		
		healthBarSpriteRed.setSize((float)width*2* (health/totalHealth), healthBarSpriteRed.getHeight());
		healthBarSpriteRed.setY(getY() + height);
		healthBarSpriteRed.setX(getX()- width);
		healthBarSpriteRed.draw(batch);
		
	}
	
	public void kill() {
		delete();
		dead = true;
	}
	
	public void addHealth(float a) {
		health +=a;
		if(health > totalHealth) health = totalHealth;
	//	if(health < 0)kill();
		
	}
	
	
	
	public void setMobType(MobType type) {
		this.mobType = type;
	}

}
