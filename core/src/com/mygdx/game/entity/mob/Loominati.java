package com.mygdx.game.entity.mob;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Game;
import com.mygdx.game.level.Classifications.MobType;

public class Loominati extends Mob {

	public Loominati(World world) {
		super(BodyType.DynamicBody, world, 32 / Game.scale, 32 / Game.scale, MobType.Dick);
		totalHealth = 100;
		health = 100;
		addBodyDef(new float[] { 0, height, -height, -width, width, -height }, 0.1f, 1, 0);
		body.setUserData(this);

		sprite = new Sprite(new Texture("res/loominati.png"));
	}

	public void render(OrthographicCamera camera, SpriteBatch batch) {
		if (!dead) {
			sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
					body.getPosition().y - sprite.getHeight() / 2);
			sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
			sprite.setSize(width * 2, height * 2);
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			sprite.draw(batch);

			renderHealthBar(camera, batch);
		}
	}

	public void update(OrthographicCamera camera) {
		if (!dead) {
			if (health <= 0) {
				delete();
				kill();
			}
		}
	}

}
