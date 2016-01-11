package com.mygdx.game.entity.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Game;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.GameObject;
import com.mygdx.game.entity.Missile;
import com.mygdx.game.level.Classifications.ObjType;
import com.mygdx.game.physics.Physics;

public class Player extends Entity {

	public Player(World world) {
		super(BodyType.DynamicBody, world, 8 / Game.scale, 16 / Game.scale);
		super.addBodyDef(0, -height / 2, width, 1, 1, 0);
		super.addBodyDef(-(1 / Game.scale), height / 2, width - (2 / Game.scale), height / 2 - (1 / Game.scale), 1, 1,
				0);
		setType(ObjType.Player);
		sprite = new Sprite(new Texture("res/player.png"));
		body.setFixedRotation(true);
		setY(100/Game.scale);
	}

	public void render(OrthographicCamera camera, SpriteBatch batch) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.setSize(width * 2, height * 2);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.draw(batch);


	}
	private ArrayList<Missile> missiles = new ArrayList<Missile>();
	public void update(OrthographicCamera camera) {

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			body.setLinearVelocity(500/Game.scale, body.getLinearVelocity().y);
		} else 
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				body.setLinearVelocity(-500/Game.scale, body.getLinearVelocity().y);
			}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			body.setLinearVelocity(body.getLinearVelocity().x, 250/Game.scale);
		}
		x = body.getPosition().x;
		y = body.getPosition().y;
		
		if( Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			for(int i =0;i < missiles.size();i++) {
				Physics.explosion(world, 5, missiles.get(i).getX(),missiles.get(i).getY());
				missiles.get(i).delete();
			}
			
			missiles.clear();
		}
		
		if( Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			Missile obj = new Missile(BodyType.DynamicBody,world,32/Game.scale,32/Game.scale);
			missiles.add(obj);
			obj.addBodyDef(0,0, 8/Game.scale, 1, 1, 0);
			obj.getBody().setUserData(obj);
			float x = (Gdx.input.getX() )/Game.scale+ camera.position.x -(Gdx.graphics.getWidth()/2)/Game.scale;
			float y = (Gdx.graphics.getHeight()- Gdx.input.getY())/Game.scale + camera.position.y-(Gdx.graphics.getHeight()/2)/Game.scale;
			if(x-this.x>0)
				obj.setX(this.x+ 1f);
			else obj.setX(this.x- 1f);
			obj.setY(this.y);

			Vector2 dir = new Vector2((x-this.x)*10,(y-this.y)*10);
			obj.getBody().setLinearVelocity(dir);

		}

	}
}
