package com.github.Danice123.javamon.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.entity.Player;

public class MapHandler {
	
	private OrthogonalTiledMapRenderer renderer;
	
	public void loadMap(AssetManager assets, String map, Player player) {
		TiledMap tiles = assets.get("res/maps/" + map + "/map.tmx");
		renderer = new OrthogonalTiledMapRenderer(tiles, 1/16f);
		
		player.place(this);
		tiles.getLayers().get(player.getLayer()).getObjects().add(player);
		
		EntityList l = assets.get("res/maps/" + map + "/entity.lst");
		l.load(assets, this, renderer.getMap().getLayers());
	}
	
	public void render(OrthographicCamera camera) {
		renderer.setView(camera);
		renderer.getSpriteBatch().begin();
		for (MapLayer layer : renderer.getMap().getLayers()) {
			renderer.renderTileLayer((TiledMapTileLayer) layer);
			for (int i = 0; i < layer.getObjects().getCount(); i++) {
				Entity e = (Entity) layer.getObjects().get(i);
				e.render(renderer.getSpriteBatch());
			}
		}
		renderer.getSpriteBatch().end();
	}
	
	public void tick() {
		for (int j = 0; j < renderer.getMap().getLayers().getCount(); j++) {
			for (int i = 0; i < renderer.getMap().getLayers().get(j).getObjects().getCount(); i++) {
				Entity e = (Entity) renderer.getMap().getLayers().get(j).getObjects().get(i);
				e.tick();
			}
		}
	}
	
	public boolean collide(int x, int y, int layer) {
		if (renderer == null) {
			return true;
		}
		for (MapObject o : renderer.getMap().getLayers().get(layer).getObjects()) {
			Entity e = (Entity) o;
			if (x == e.getHitBox()[0] && y == e.getHitBox()[1])
				return true;
			if (e.getHitBox().length > 2 && x == e.getHitBox()[2] && y == e.getHitBox()[3])
				return true;
		}
		
		TiledMapTileLayer l = (TiledMapTileLayer) renderer.getMap().getLayers().get(layer + 1);
		if (l.getCell(x, y) != null)
			return true;
		return false;
	}
	
	public Entity getEntity(int x, int y, int layer) {
		for (MapObject o : renderer.getMap().getLayers().get(layer).getObjects()) {
			Entity e = (Entity) o;
			if (x == e.getHitBox()[0] && y == e.getHitBox()[1])
				return e;
		}
		return null;
	}
}
