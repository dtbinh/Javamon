package com.github.Danice123.javamon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.map.MapHandler;

public class World extends Screen  {
	
	private Game game;
	private MapHandler map;
	private OrthographicCamera camera;
	
	private String mapName;
	private boolean isMapLoaded;
	
	public World(Game game) {
		super(game);
		this.game = game;
		map = new MapHandler();
	}
	
	public void loadMap(String name) {
		mapName = name;
		isMapLoaded = false;
	}
	
	public Entity getEntity(int x, int y, int layer) {
		return map.getEntity(x, y, layer);
	}
	
	@Override
	protected void init() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 15, 10);
		camera.update();
	}
	
	@Override
	public void render2(float delta) {
		if (!isMapLoaded) {
			map.loadMap(game.getAssets(), mapName, game.getPlayer());
			isMapLoaded = true;
		}
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.translate(game.getPlayer().getX()/16 - camera.position.x, game.getPlayer().getY()/16 - camera.position.y);
		camera.update();
		map.render(camera);
	}
	
	@Override
	public void tick() {
		if (isMapLoaded)
			map.tick();
	}
}
