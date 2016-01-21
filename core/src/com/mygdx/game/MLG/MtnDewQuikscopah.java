package com.mygdx.game.MLG;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Game;
import com.mygdx.game.entity.player.Player;

public class MtnDewQuikscopah extends Weapon {

	public MtnDewQuikscopah(World world,Player player) {
		super(world,player, new Sprite(new Texture("res/ak47.png")), 32 / Game.scale, 16 / Game.scale, 10, 10);
	}

	public void render(OrthographicCamera camera, SpriteBatch batch) {

		sprite.draw(batch);
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(batch);
		}

	}

	public void update(OrthographicCamera camera) {
		setPosRot(camera);
		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i).hit)
				bullets.remove(i);
			else
				bullets.get(i).update();
		}
		

	}

}
