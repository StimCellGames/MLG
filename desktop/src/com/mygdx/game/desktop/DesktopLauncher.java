package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Game;

public class DesktopLauncher {
	private static boolean FPSCap = true;
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.vSyncEnabled = false;
		cfg.title = "Game";
		cfg.width=1280;
		cfg.height=720;
		if(!FPSCap) {
			cfg.vSyncEnabled = false;
			cfg.foregroundFPS = 0;
			cfg.backgroundFPS = 0;
		}
		new LwjglApplication(new Game(), cfg);
	}
}
