package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Missile extends Entity{

	public Missile(BodyType type, World world, float width, float height) {
		super(type, world, width, height);
	}

	public void render(OrthographicCamera camera, SpriteBatch batch) {
		
	}

	public void update(OrthographicCamera camera) {
		
	}

}
