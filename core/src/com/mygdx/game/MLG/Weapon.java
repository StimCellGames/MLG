package com.mygdx.game.MLG;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.Game;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.mob.Mob;
import com.mygdx.game.entity.player.Player;
import com.mygdx.game.level.Classifications.MobType;
import com.mygdx.game.level.Classifications.ObjType;
import com.mygdx.game.physics.Physics;

public abstract class Weapon {
	// boullet per second
	protected float fireRate;

	protected float damage;
	protected Player player;
	protected float width, height;

	public Sprite sprite;

	protected float angle;

	protected Vector2 pos;

	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	private World world;

	private ShapeRenderer sr = new ShapeRenderer();

	public Weapon(World world, Player player, Sprite sprite, float width, float height, float damage, float fireRate) {
		this.width = width;
		this.height = height;
		this.damage = damage;
		this.fireRate = fireRate;
		this.sprite = sprite;
		sprite.setSize(width, height);
		this.player = player;
		this.world = world;
	}

	public abstract void render(OrthographicCamera camera, SpriteBatch batch);

	public abstract void update(OrthographicCamera camera);

	protected void setPosRot(OrthographicCamera camera) {
		sprite.setX(player.getX());
		sprite.setY(player.getY());
		sprite.setOrigin(player.getWidth() / 2, player.getHeight() / 2);
		pos = new Vector2(player.getX() + Gdx.graphics.getWidth() / 2, player.getY() + Gdx.graphics.getHeight() / 2);
		Vector2 end = new Vector2(Gdx.input.getX(), ((Gdx.graphics.getHeight() - Gdx.input.getY())));

		angle = pos.sub(end).angle() + 180.0f;
		if (angle < 270 || angle > 450) {
			sprite.setFlip(false, true);
		} else {
			sprite.setFlip(false, false);
		}

		sprite.setRotation(angle);
	}

	public void fireAt(OrthographicCamera camera, float x, float y) {
		bullets.add(new Bullet(camera, player.getX() + Gdx.graphics.getWidth() / 2,
				player.getY() + Gdx.graphics.getHeight() / 2, x, y));
	}

	public class Bullet {
		private float x, y, ex, ey;
		private Sprite bulletSprite;
		private Vector2 velocity = new Vector2(0, 0);
		public boolean hit = false;

		private float lifeSpan = 100, lived = 0;

		private RayCastCallback rccb;

		private OrthographicCamera camera;
		private Fixture fix;

		private Sprite muzzleFlash;

		public Bullet(OrthographicCamera camera, float x, float y, float ex, float ey) {
			this.x = x;
			this.y = y;
			this.ex = ex;
			this.ey = ey;
			this.camera = camera;

			bulletSprite = new Sprite(new Texture("res/bullet.png"));
			bulletSprite.setOrigin(player.getWidth() / 2, player.getHeight() / 2);
			bulletSprite.setX(x - Gdx.graphics.getWidth() / 2);
			bulletSprite.setY(y - Gdx.graphics.getHeight() / 2);
			bulletSprite.setScale(1 / Game.scale);
			bulletSprite.rotate(angle);

			velocity.x = (ex - x) / new Vector2(x, y).dst(ex, ey) * 100 / Game.scale;
			velocity.y = (ey - y) / new Vector2(x, y).dst(ex, ey) * 100 / Game.scale;

			rccb = new RayCastCallback() {
				public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
					fix = fixture;
					if (fixture.getBody().getUserData().equals(MobType.Dick)) {
						((Mob) fixture.getBody().getUserData()).addHealth(-1);
					}
					return fraction;

				}

			};

			muzzleFlash = new Sprite(new Texture("res/muzzleFlash.png"));
			//muzzleFlash.setOrigin(sprite.getOriginX() , sprite.getOriginY());
			//muzzleFlash.rotate(angle);	

			
			muzzleFlash.setX(0);
			muzzleFlash.setY(0);
			
			muzzleFlash.setScale(0.35f / Game.scale, 0.25f / Game.scale);

			world.rayCast(rccb, new Vector2(x - Gdx.graphics.getWidth() / 2, y - Gdx.graphics.getHeight() / 2),
					new Vector2(ex, ey));

		}

		public void update() {
			if (fix.testPoint(Gdx.input.getX() / Game.scale, Gdx.input.getY() / Game.scale))
				System.out.println("sdf");
			lived++;
			if (lived > lifeSpan) {
				hit = true;
			}
			if (!hit) {

				for (int i = 0; i < Math.floor(Math.abs(velocity.x)); i++) {
					if (fix.testPoint(bulletSprite.getX(), bulletSprite.getY()))
						hit = true;
					if (velocity.x > 0)
						bulletSprite.translateX(1);
					else
						bulletSprite.translateX(-1);
				}
				if (fix.testPoint(bulletSprite.getX(), bulletSprite.getY()))
					hit = true;
				if (velocity.x > 0)
					bulletSprite.translateX((float) (Math.abs(velocity.x) - Math.floor(Math.abs(velocity.x))));
				else
					bulletSprite.translateX((float) -(Math.abs(velocity.x) - Math.floor(Math.abs(velocity.x))));

				for (int i = 0; i < Math.floor(Math.abs(velocity.y)); i++) {
					if (fix.testPoint(bulletSprite.getX(), bulletSprite.getY()))
						hit = true;
					if (velocity.y > 0)
						bulletSprite.translateY(1);
					else
						bulletSprite.translateY(-1);
				}
				if (fix.testPoint(bulletSprite.getX(), bulletSprite.getY()))
					hit = true;
				if (velocity.y > 0)
					bulletSprite.translateY((float) (Math.abs(velocity.y) - Math.floor(Math.abs(velocity.y))));
				else
					bulletSprite.translateY((float) -(Math.abs(velocity.y) - Math.floor(Math.abs(velocity.y))));

			}

		}

		private boolean drawn = false;

		public void render(SpriteBatch batch) {
			muzzleFlash.setFlip(sprite.isFlipX(), sprite.isFlipY());
			//if (!drawn) {
				muzzleFlash.draw(batch);
				drawn = true;
			//}
			// muzzleFlash.draw(batch);
			if (!hit)
				bulletSprite.draw(batch);
		}

	}
}
