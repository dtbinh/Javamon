package com.github.Danice123.javamon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.Danice123.javamon.Game;

public class Loading extends Screen {
	
	private BitmapFont font;
	private SpriteBatch batch;
	
	public Loading(Game game) {
		super(game);
	}
	
	@Override
	protected void init() {
		font = new BitmapFont();
		batch = new SpriteBatch();
	}

	@Override
	public void render2(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		font.setColor(1f, 1f, 1f, 1f);
		font.draw(batch, "Loading... " + (getGame().getAssets().getProgress() * 100), 10, 20);
		batch.end();
	}
	
	@Override
	public void tick() {
		if(getGame().getAssets().update())
			synchronized (this) {notify();}
	}
}
