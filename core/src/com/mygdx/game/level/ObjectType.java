package com.mygdx.game.level;

public class ObjectType {
	public enum ObjType {
		unMovable(0), Tile(1), Player(2);
		
		private int value;

		private ObjType(int value) {
			this.value = value;
		}

		public int getValue () {
			return value;
		}
	}
}
