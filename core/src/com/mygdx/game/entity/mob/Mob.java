package com.mygdx.game.entity.mob;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.level.Classifications.MobType;

public abstract class Mob extends Entity{

	private MobType mobType;
	public Mob(BodyType type, World world, float width, float height,MobType mobType) {
		super(type, world, width, height);
		this.mobType=mobType;
	}
	
	public void setMobType(MobType type) {
		this.mobType = type;
	}

}
