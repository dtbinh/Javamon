package com.github.Danice123.javamon.screen.menu.animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Animation {
	
	public boolean isDone = false;

	public abstract void render(SpriteBatch batch);
	
	protected void done() {
		isDone = true;
		synchronized (this) {
			notifyAll();
		}
	}
}
