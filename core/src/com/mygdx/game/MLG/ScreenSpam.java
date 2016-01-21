package com.mygdx.game.MLG;

import java.util.ArrayList;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ScreenSpam {
	private static ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private static ArrayList<Spam> currentlyRendering = new ArrayList<Spam>();
	private static int amount;
	private static Random rand = new Random();
	private ScreenSpam() {
	}

	public static void init(String path) {
		int i = 0;
		while (true) {
			i++;
			try {
				sprites.add(new Sprite(new Texture(path + "/spam" + i + ".png")));
			} catch (GdxRuntimeException g) {
				amount = i;
				break;
			}
		}

	}

	public static void render(SpriteBatch batch) {
		for(int i = 0;i < currentlyRendering.size();i++) {
			currentlyRendering.get(i).render(batch);
		}
	}

	public static void displayRandomSpam(float x, float y) {
		
	}

	public static void displaySpam(float x, float y, int num) {

	}
	//class inside a class, inception
	private class Spam {
		float frameCounter=0;
		private Animation anim;
		public Spam(Animation sprite) {
			this.anim = sprite;
		}
		
		public void render(SpriteBatch batch) {
			frameCounter += Gdx.graphics.getDeltaTime();
			batch.draw(anim.getKeyFrame(frameCounter, true),200, 200);
		}
		
		
		
	}
}
