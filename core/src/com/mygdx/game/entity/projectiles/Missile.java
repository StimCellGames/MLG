package com.mygdx.game.entity.projectiles;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.entity.Entity;
import com.badlogic.gdx.physics.box2d.World;

public class Missile extends Entity{

	public Missile(BodyType type, World world, float width, float height) {
		super(type, world, width, height);
		sprite = new Sprite(new Texture("res/loominati.png"));
	}

	public void render(OrthographicCamera camera, SpriteBatch batch) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.setSize(width * 2, height * 2);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.draw(batch);
	}

	public void update(OrthographicCamera camera) {
		
	}

}
