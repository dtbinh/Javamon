package com.github.Danice123.javamon.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.Danice123.javamon.Display;
import com.github.Danice123.javamon.Game;

public class ChatBox extends Screen {
	
	private SpriteBatch batch;
	private BorderBox border;
	private String[] text;
	private int index = 0;
	private Object lock;
	
	public ChatBox(Game game, Screen parent, String text, Object lock) {
		super(game, parent);
		renderBehind = true;
		this.lock = lock;
		if (text.contains("/n")){
			this.text = text.split("/n");
		} else {
			this.text = new String[] {text};
		}
	}
	
	@Override
	protected void init() {
		batch = new SpriteBatch();
		border = new BorderBox((Texture) getGame().getAssets().get("res/gui/border.png"));
	}
	
	@Override
	public void render2(float delta) {
		batch.begin();
		border.drawBox(batch, 0, 0, Display.WIDTH, 50 * Display.scale);
		Display.font.setColor(0f, 0f, 0f, 1f);
		Display.font.drawWrapped(batch, text[index], border.WIDTH + 2, 50 * Display.scale - border.HEIGHT, Display.WIDTH - 2* (border.WIDTH + 2));
		batch.end();
	}
	
	@Override
	public void tick() {
		
	}
	
	@Override
	public void accept() {
		super.accept();
		if (!(index < text.length - 1)) {
			synchronized (lock) {
				lock.notifyAll();
			}
			getGame().getPlayer().menuOpen = false;
			disposeMe = true;
		} else
			index++;
	}
}
