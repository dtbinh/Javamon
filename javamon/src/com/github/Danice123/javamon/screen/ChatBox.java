package com.github.Danice123.javamon.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.Danice123.javamon.Display;
import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.control.MenuControl;

public class ChatBox extends Screen implements MenuControl {
	
	private BitmapFont font;
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
		font = new BitmapFont();
		font.setColor(0f, 0f, 0f, 1f);
		batch = new SpriteBatch();
		border = new BorderBox((Texture) getGame().getAssets().get("res/gui/border.png"));
	}
	
	@Override
	public void render2(float delta) {
		batch.begin();
		border.drawBox(batch, 0, 0, Display.WIDTH, 50);
		font.draw(batch, text[index], border.WIDTH + 2, 50 - border.HEIGHT);
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
			disposeMe = true;
		} else
			index++;
	}
}