package com.github.Danice123.javamon.entity;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.github.Danice123.javamon.entity.sprite.Spriteset;
import com.github.Danice123.javamon.map.MapHandler;
import com.github.Danice123.javamon.script.Script;
import com.github.Danice123.javamon.script.ScriptHandler;

public abstract class Entity extends TextureMapObject implements Runnable {
	
	protected Spriteset sprites;
	private int layer;
	protected MapHandler map;
	protected Script script;
	
	private Object lock = new Object();
	private int tickCount = 0;
	private boolean exists = true;
	public boolean busy = false;
	
	public Entity(String name, Spriteset sprites, Script script) {
		super();
		this.sprites = sprites;
		this.script = script;
		
		setName(name);
		setVisible(true);
		getProperties();
	}
	
	public void addStrings(HashMap<String, String> map) {
		script.strings.putAll(map);
	}
	
	public void place(MapHandler map) {
		this.map = map;
	}
	
	protected void setSprite(TextureRegion tex) {
		setTextureRegion(tex);
	}
	
	public void setCoords(int x, int y, int layer) {
		setX(x * 16);
		setY(y * 16);
		this.layer = layer;
	}
	
	public int getLayer() {
		return layer;
	}
	
	public int trueX() {
		return (int) (getX() / 16);
	}
	
	public int trueY() {
		return (int) (getY() / 16);
	}
	
	public int[] getHitBox() {
		return new int[]{trueX(), trueY()};
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(getTextureRegion(), getX() / 16, getY() / 16, 1, 1);
	}
	
	public void activate(Player player) {
		if (script != null) {
			new Thread(new ScriptHandler(player.game, script, this)).start();
		}
	}
	
	public void run() {
		do {
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
				}
			}
			threaded();
		} while (exists);
	}
	
	protected abstract void threaded();
	
	protected abstract int threadDelay();
	
	public void tick() {
		tickCount++;
		if (tickCount >= threadDelay() && !busy) {
			synchronized (lock) {
				lock.notifyAll();
			}
			tickCount = 0;
		}
	}

}
