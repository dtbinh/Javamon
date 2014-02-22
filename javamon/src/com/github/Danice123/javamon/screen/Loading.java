package com.github.Danice123.javamon.screen;

import com.github.Danice123.javamon.Display;
import com.github.Danice123.javamon.Game;

public class Loading extends Screen {
	
	public Loading(Game game) {
		super(game);
	}
	
	@Override
	protected void init() {
		
	}

	@Override
	public void render2(float delta) {
		batch.begin();
		Display.font.setColor(0f, 0f, 0f, 1f);
		Display.font.draw(batch, "Loading... " + (getGame().getAssets().getProgress() * 100), 10 , 11 * Display.scale);
		batch.end();
	}
	
	@Override
	public void tick() {
		if(getGame().getAssets().update())
			synchronized (this) {notify();}
	}

	@Override
	protected void handleKey(Key key) {
		
	}
	
	public void finished() {
		disposeMe = true;
	}
}
