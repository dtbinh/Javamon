package com.github.Danice123.javamon.screen.menu;

import com.github.Danice123.javamon.Display;
import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.screen.Screen;

public class ChatBox extends Screen {
	
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
		
	}
	
	@Override
	public void render2(float delta) {
		batch.begin();
		Display.border.drawBox(batch, 0, 0, Display.WIDTH, 50 * Display.scale);
		Display.font.drawWrapped(batch, text[index], Display.border.WIDTH + 2, 50 * Display.scale - Display.border.HEIGHT, 
				Display.WIDTH - 2* (Display.border.WIDTH + 2));
		batch.end();
	}
	
	@Override
	public void tick() {
		
	}

	@Override
	protected void handleKey(Key key) {
		if (key == Key.accept || key == Key.deny) {
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
}
