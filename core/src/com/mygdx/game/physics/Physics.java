package com.mygdx.game.physics;

import javax.security.sasl.RealmCallback;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.Game;

public class Physics {
	private Physics() {
		
	}
	
	public static Vector2[] explosion(World world,float x,float y) {
		
		int numRays = 32;
		float blastPower = 0.5f;
		int blastRadius = 100;
		Vector2[] vectors = new Vector2[numRays*2];
		

		for (int i = 0; i < numRays; i++){
			RayCastCallback rccb = new RayCastCallback() {

				@Override
				public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
					fixture.getBody().setType(BodyType.StaticBody);
					return -1;
				}
				
			};
			double angle = ((i) * (360));
			Vector2 rayDir = new Vector2((float)Math.sin(angle), (float)Math.cos(angle));
			Vector2 rayEnd = new Vector2(x + blastRadius * rayDir.x, y + blastRadius * rayDir.y);
			world.rayCast(rccb, new Vector2(x,y), rayEnd);
			//System.out.println(new Vector2(x,y) + "  " + rayEnd);
			
			vectors[i*2] = new Vector2(x,y);
			vectors[i*2+1] = new Vector2(rayEnd.x,rayEnd.y);

		}
		
		return vectors;

	}
		
	

}
