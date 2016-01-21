package com.mygdx.game.entity.projectiles;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entity.Entity;

public class Bullet extends Entity{

	public Bullet(BodyType type, World world, float width, float height) {
		super(type, world, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(OrthographicCamera camera, SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(OrthographicCamera camera) {
		// TODO Auto-generated method stub
		
	}
	
	

}
