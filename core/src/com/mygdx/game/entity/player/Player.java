package com.mygdx.game.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Game;
import com.mygdx.game.entity.Entity;

public class Player extends Entity {

	public Player(World world) {
		super(BodyType.DynamicBody, world, 16 / Game.scale, 32 / Game.scale);
		super.addBodyDef(0, -height / 2, width, 1, 1, 0);
		super.addBodyDef(-(1 / Game.scale), height / 2, width - (2 / Game.scale), height / 2 - (1 / Game.scale), 1, 1,
				0);
		sprite = new Sprite(new Texture("res/player.png"));
		body.setFixedRotation(true);
		setX(100);
		setY(100);
	}

	public void render(OrthographicCamera camera, SpriteBatch batch) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.setSize(width * 2, height * 2);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.draw(batch);
	}

	public void update(OrthographicCamera camera) {
		//body.setLinearVelocity(0, body.getLinearVelocity().y);

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			body.setLinearVelocity(50, body.getLinearVelocity().y);
		} else 
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			body.setLinearVelocity(-50, body.getLinearVelocity().y);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			body.setLinearVelocity(body.getLinearVelocity().x, 50);
		}
		x = body.getPosition().x;
		y = body.getPosition().y;
	}
}
