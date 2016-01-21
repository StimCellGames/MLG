package com.mygdx.game.level;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Classifications {
	public enum ObjType {
		unMovable(0,BodyType.StaticBody), Tile(1,BodyType.StaticBody), Player(2,BodyType.DynamicBody);
		
		private int value;
		private BodyType type;
		private ObjType(int value,BodyType type) {
			this.value = value;
		}
		
		public BodyType getType() {
			return type;
		}
		
		public int getValue () {
			return value;
		}
	}
	
	public enum MobType {
		Harmless(0), getsPissedOff(1), Dick(2), Player(3);
		private int value;
		private MobType(int value) {
			this.value = value;
		}
	
		
		public int getValue () {
			return value;
		}
		
	}
	
	
}
