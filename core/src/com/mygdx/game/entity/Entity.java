package com.mygdx.game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {
	protected Sprite sprite;
	private BodyType type;
	protected Body body;
	protected float width, height;
	protected float x, y;
	protected ArrayList<Entity>bodies = new ArrayList<Entity>();

	private World world;
	protected FixtureDef circleFixture = new FixtureDef();
	public Entity(BodyType type, World world,float width,float height) {
		this.width = width;
		this.height = height;
		this.type = type;
		this.world = world;

	}

	public void addBodyDef(float x,float y,float width, float height,float density,float friction,float restition) {
		if(body==null) {
			BodyDef def = new BodyDef();
			def.type = type;
			body = world.createBody(def);
			body.getPosition().x = -width;
			body.getPosition().y = -height;
		}
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width,height);
		circleFixture.shape = shape;
		circleFixture.density = density;
		circleFixture.friction = friction;
		circleFixture.restitution = restition;
		body.createFixture(circleFixture);

	}

	public void addBodyDef(float x,float y,float radius,float density,float friction,float restition) {
		if(body==null) {
			BodyDef def = new BodyDef();
			def.type = type;
			body = world.createBody(def);
			body.getPosition().x = -radius;
			body.getPosition().y = -radius;
		}
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);

		shape.setPosition(new Vector2(x,y));

		circleFixture.shape = shape;
		circleFixture.density = density;
		circleFixture.friction = friction;
		circleFixture.restitution = restition;
		

		body.createFixture(circleFixture);

	}





	public abstract void render(Camera camera,SpriteBatch batch);
	public abstract void update(Camera camera);



	public void setActive(boolean a) {
		if(body!=null)body.setActive(a);
	}


	public Sprite getSprite() {
		return sprite;
	}



	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}


	public float getX() {
		return x;
	}

	public void setX(float x) {
		if(body!=null)body.setTransform(x,y,0);
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		if(body!=null)body.setTransform(x,y,0);
		this.y = y;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;

	}


}
