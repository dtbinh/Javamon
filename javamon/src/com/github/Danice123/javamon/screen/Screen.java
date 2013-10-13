package com.github.Danice123.javamon.screen;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.control.MenuControl;

public abstract class Screen implements com.badlogic.gdx.Screen, MenuControl {

	private boolean initialized;
	private Game game;
	protected boolean renderBehind;
	
	private boolean isChild;
	private Screen parent;
	
	private boolean hasChild;
	private Screen child;
	
	protected boolean disposeMe;
	
	public Screen(Game game) {
		this.game = game;
		isChild = false;
		hasChild = false;
		disposeMe = false;
	}
	
	public Screen(Game game, Screen parent) {
		this(game);
		if (parent.hasChild) {
			throw new IllegalArgumentException();
		}
		isChild = true;
		this.parent = parent;
		
		parent.hasChild = true;
		parent.child = this;
		
		renderBehind = false;
	}
	
	private boolean renderBehind() {
		if (hasChild) {
			return child.renderBehind();
		}
		return renderBehind;
	}
	
	protected abstract void render2(float delta);
	
	protected abstract void tick();
	
	protected abstract void init();
	
	protected Game getGame() {
		return game;
	}
	
	@Override
	public void render(float delta) {
		if (!initialized) {
			init();
			initialized = true;
		}
		if (hasChild) {
			if (child.renderBehind()) {
				tick();
				render2(delta);
			}
			child.render(delta);
			if (child.disposeMe) {
				hasChild = false;
				child.dispose();
				child = null;
			}
		} else {
			tick();
			render2(delta);
		}
	}
	
	@Override
	public Screen getScreen() {
		return this;
	}

	@Override
	public void up() {
		if (hasChild) {
			child.up();
			return;
		}
	}
	
	@Override
	public void down() {
		if (hasChild) {
			child.down();
			return;
		}
	}
	
	@Override
	public void right() {
		if (hasChild) {
			child.right();
			return;
		}
	}
	
	@Override
	public void left() {
		if (hasChild) {
			child.left();
			return;
		}
	}
	
	@Override
	public void accept() {
		if (hasChild) {
			child.accept();
			return;
		}
	}
	
	@Override
	public void deny() {
		if (hasChild) {
			child.deny();
			return;
		}
	}
	
	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}
}
