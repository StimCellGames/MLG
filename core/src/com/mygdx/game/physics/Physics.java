package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.Game;
import com.mygdx.game.level.Classifications.ObjType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

public class Physics {
	private Physics() {

	}

	public static Vector2[] explosion(World world,final float blastPower, final float x, final float y) {
		int blastRadius = (int) ((blastPower/Game.scale)*50);
		int numRays = blastRadius *10;

		Vector2[] vectors = new Vector2[numRays * 2];

		for (int i = 0; i < numRays; i++) {
			RayCastCallback rccb = new RayCastCallback() {
				public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
					if(fixture.getBody().getUserData() instanceof ObjType) {
						if(fixture.getBody().getUserData().equals(ObjType.unMovable)) {
							return fraction;
						}
					} 
					fixture.getBody().setType(BodyType.DynamicBody);
					applyBlastImpulse(fixture.getBody(),new Vector2(x, y),point,blastPower);
					return -1;
					
				}

			};
			double angle = Math.toRadians((i * 360) / numRays);
			Vector2 rayDir = new Vector2((float) Math.sin(angle), (float) Math.cos(angle));
			Vector2 rayEnd = new Vector2(x + blastRadius * rayDir.x, y + blastRadius * rayDir.y);
			world.rayCast(rccb, new Vector2(x, y), rayEnd);

			vectors[i * 2] = new Vector2(x, y);
			vectors[i * 2 + 1] = rayEnd;

		}

		return vectors;

	}
	
	public static Vector2[] implosion(World world,final float blastPower, final float x, final float y) {
		int blastRadius = (int) (blastPower*50);
		int numRays = blastRadius *10;
		Vector2[] vectors = new Vector2[numRays * 2];

		for (int i = 0; i < numRays; i++) {
			RayCastCallback rccb = new RayCastCallback() {

				@Override
				public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
					if(fixture.getBody().getUserData() instanceof ObjType) {
						if(fixture.getBody().getUserData().equals(ObjType.unMovable)) {
							return 0;
						}
						if(!fixture.getBody().getUserData().equals(ObjType.unMovable)) {
							fixture.getBody().setType(BodyType.DynamicBody);
							applyNegativeBlastImpulse(fixture.getBody(),new Vector2(x, y),point,blastPower);
						}
					} 
					return -1;
				}

			};
			double angle = Math.toRadians((i * 360) / numRays);
			Vector2 rayDir = new Vector2((float) Math.sin(angle), (float) Math.cos(angle));
			Vector2 rayEnd = new Vector2(x + blastRadius * rayDir.x, y + blastRadius * rayDir.y);
			world.rayCast(rccb, new Vector2(x, y), rayEnd);

			vectors[i * 2] = new Vector2(x, y);
			vectors[i * 2 + 1] = rayEnd;

		}

		return vectors;
	}

	public static void applyBlastImpulse(Body body, Vector2 pos, Vector2 applyPoint, float blastPower) {
		if (body.getType() != BodyType.DynamicBody)
			return;
		Vector2 blastDir = new Vector2(applyPoint.x - pos.x, applyPoint.y - pos.y);
		float distance = body.getPosition().dst(pos);
		if(distance == 0) {
			return;

		}

		float invDistance = 1 / distance;
		float impulseMag = blastPower * invDistance * invDistance;
		impulseMag = Math.min( impulseMag, 500);
		body.applyLinearImpulse( new Vector2(impulseMag * blastDir.x , impulseMag * blastDir.y ),
				applyPoint ,true);
	}
	
	public static void applyNegativeBlastImpulse(Body body, Vector2 pos, Vector2 applyPoint, float blastPower) {
		if (body.getType() != BodyType.DynamicBody)
			return;
		Vector2 blastDir = new Vector2(applyPoint.x - pos.x, applyPoint.y - pos.y);
		float distance = body.getPosition().dst(pos);
		if(distance == 0) {
			return;

		}

		float invDistance = 1 / distance;
		float impulseMag = blastPower * invDistance * invDistance;
		impulseMag = Math.min( impulseMag, 500);
		body.applyLinearImpulse( new Vector2(-impulseMag * blastDir.x , -impulseMag * blastDir.y ),
				applyPoint ,true);




	}

}
