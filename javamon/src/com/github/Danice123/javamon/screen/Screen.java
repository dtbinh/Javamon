package com.github.Danice123.javamon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
	
	protected SpriteBatch batch;
	protected ShapeRenderer shape;
	
	private FPSLogger fps;
	
	public Screen(Game game) {
		this.game = game;
		isChild = false;
		hasChild = false;
		disposeMe = false;
		fps = new FPSLogger();
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
			batch = new SpriteBatch();
			shape = new ShapeRenderer();
			init();
			initialized = true;
		}
		tick();
		if (hasChild) {
			if (child.renderBehind()) {
				render2(delta);
			}
			child.render(delta);
			if (child.disposeMe) {
				hasChild = false;
				child.dispose();
				child = null;
			}
		} else {
			if (!renderBehind) {
				Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
				Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			}
			render2(delta);
			fps.log();
		}
		if (!isChild && disposeMe) {
			dispose();
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
		handleKey(Key.up);
	}
	
	@Override
	public void down() {
		if (hasChild) {
			child.down();
			return;
		}
		handleKey(Key.down);
	}
	
	@Override
	public void right() {
		if (hasChild) {
			child.right();
			return;
		}
		handleKey(Key.right);
	}
	
	@Override
	public void left() {
		if (hasChild) {
			child.left();
			return;
		}
		handleKey(Key.left);
	}
	
	@Override
	public void accept() {
		if (hasChild) {
			child.accept();
			return;
		}
		handleKey(Key.accept);
	}
	
	@Override
	public void deny() {
		if (hasChild) {
			child.deny();
			return;
		}
		handleKey(Key.deny);
	}
	
	protected abstract void handleKey(Key key);
	
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
		batch.dispose();
		shape.dispose();
	}
	
	protected enum Key {
		up, down, left, right, accept, deny
	}
}
