package com.mygdx.game.desktop;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Game;
import com.badlogic.gdx.utils.GdxNativesLoader;

public class DesktopLauncher {
	private static boolean FPSCap = true;

	public static void main(String[] arg) {
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true"); 
		//System.out.println(GL11.glGetString(GL11.GL_VERSION));
		 GdxNativesLoader.load();
			
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.vSyncEnabled = false;
		cfg.title = "Game";
		cfg.width = 1280;
		cfg.height = 720;
		if (!FPSCap) {
			cfg.vSyncEnabled = false;
			cfg.foregroundFPS = 0;
			cfg.backgroundFPS = 0;
		}
		new LwjglApplication(new Game(), cfg);
		
	}
}
