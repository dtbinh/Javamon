package com.github.Danice123.javamon.screen.menu.animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MultiAnimation extends Animation {
	
	private Animation[] animations;

	public MultiAnimation(Animation... animations) {
		this.animations = animations;
	}

	@Override
	public void render(SpriteBatch batch) {
		boolean d = true;
		for (int i = 0; i < animations.length; i++) {
			animations[i].render(batch);
			d = animations[i].isDone;
		}
		if (d)
			done();
	}

}
