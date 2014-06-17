package com.github.Danice123.javamon.map;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.github.Danice123.javamon.entity.Dir;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.entity.Player;
import com.github.Danice123.javamon.script.Script;

public class MapHandler {
	
	private AssetManager assets;
	private HashMap<String, TiledMap> mapCache;
	
	private OrthogonalTiledMapRenderer renderer;
	private Player player;
	
	private Script[][][] triggers;
	
	private MapHandler topMap;
	private MapHandler bottomMap;
	private MapHandler leftMap;
	private MapHandler rightMap;
	
	private int upTweak;
	private int bottomTweak;
	private int leftTweak;
	private int rightTweak;
	
	public Script mapScript;
	
	public void loadMap(String map, Player player) {
		this.player = player;
		if (renderer != null) {
			dispose();
		}
		
		if (mapCache.get(map) == null) {
			TiledMap tiles = assets.get("res/maps/" + map + "/map.tmx");
			renderer = new OrthogonalTiledMapRenderer(tiles, 1/16f);
		
			EntityList l = assets.get("res/maps/" + map + "/entity.lst");
			l.load(assets, this, renderer.getMap().getLayers(), map);
			
			TriggerList t = assets.get("res/maps/" + map + "/trigger.lst");
			triggers = t.load(assets, this, map);
			
			mapCache.put(map, tiles);
		} else {
			renderer = new OrthogonalTiledMapRenderer(mapCache.get(map), 1/16f);
			TriggerList t = assets.get("res/maps/" + map + "/trigger.lst");
			triggers = t.load(assets, this, map);
		}
		
		try {
			mapScript = assets.get("res/maps/" + map + "/mapScript.ps");
		} catch (NullPointerException e) {
			mapScript = null;
		}
		
		topMap = null;
		if (renderer.getMap().getProperties().get("Up") != null) {
			topMap = new MapHandler(assets);
			topMap.loadMap((String) renderer.getMap().getProperties().get("Up"), mapCache);
			upTweak = Integer.parseInt((String) renderer.getMap().getProperties().get("UpTweak"));
		}
		
		bottomMap = null;
		if (renderer.getMap().getProperties().get("Down") != null) {
			bottomMap = new MapHandler(assets);
			bottomMap.loadMap((String) renderer.getMap().getProperties().get("Down"), mapCache);
			bottomTweak = Integer.parseInt((String) renderer.getMap().getProperties().get("DownTweak"));
		}
		
		leftMap = null;
		if (renderer.getMap().getProperties().get("Left") != null) {
			leftMap = new MapHandler(assets);
			leftMap.loadMap((String) renderer.getMap().getProperties().get("Left"), mapCache);
			leftTweak = Integer.parseInt((String) renderer.getMap().getProperties().get("LeftTweak"));
		}
		
		rightMap = null;
		if (renderer.getMap().getProperties().get("Right") != null) {
			rightMap = new MapHandler(assets);
			rightMap.loadMap((String) renderer.getMap().getProperties().get("Right"), mapCache);
			rightTweak = Integer.parseInt((String) renderer.getMap().getProperties().get("RightTweak"));
		}
	}
	
	public void loadMap(String map, HashMap<String, TiledMap> mapCache) {
		if (mapCache.get(map) == null) {
			TiledMap tiles = assets.get("res/maps/" + map + "/map.tmx");
			renderer = new OrthogonalTiledMapRenderer(tiles, 1/16f);
		
			EntityList l = assets.get("res/maps/" + map + "/entity.lst");
			l.load(assets, this, renderer.getMap().getLayers(), map);
			mapCache.put(map, tiles);
		} else {
			renderer = new OrthogonalTiledMapRenderer(mapCache.get(map), 1/16f);
		}
	}
	
	public void render(OrthographicCamera camera) {
		Matrix4 m = camera.combined;
		if (topMap != null) {
			topMap.render(m.translate(upTweak, getY(), 0), 0, 0);
			m.translate(-upTweak, -getY(), 0);
		}
		if (bottomMap != null) {
			bottomMap.render(camera.combined.translate(bottomTweak, -bottomMap.getY(), 0), 0, 0);
			m.translate(-bottomTweak, bottomMap.getY(), 0);
		}
		if (leftMap != null) {
			leftMap.render(camera.combined.translate(-leftMap.getX(), leftTweak, 0), 0, 0);
			m.translate(leftMap.getX(), -leftTweak, 0);
		}
		if (rightMap != null) {
			rightMap.render(camera.combined.translate(getX(), rightTweak, 0), 0, 0);
			m.translate(-getX(), -rightTweak, 0);
		}
		
		renderer.setView(camera);
		renderer.getSpriteBatch().begin();
		for (int i = 0; i < renderer.getMap().getLayers().getCount(); i++) {
			renderer.renderTileLayer((TiledMapTileLayer) renderer.getMap().getLayers().get(i));
			if (i - 1 >= 0) {
				MapLayer layer = renderer.getMap().getLayers().get(i - 1);
				for (int j = 0; j < layer.getObjects().getCount(); j++) {
					Entity e = (Entity) layer.getObjects().get(j);
					if (!e.hidden)
						e.render(renderer.getSpriteBatch());
				}
				if (player.getLayer() == i - 1)
					player.render(renderer.getSpriteBatch());
			}
		}
		renderer.getSpriteBatch().end();
	}
	
	public void render(Matrix4 projection, float x, float y) {
		renderer.setView(projection, x, y, getX(), getY());
		renderer.getSpriteBatch().begin();
		for (MapLayer layer : renderer.getMap().getLayers()) {
			renderer.renderTileLayer((TiledMapTileLayer) layer);
			for (int i = 0; i < layer.getObjects().getCount(); i++) {
				Entity e = (Entity) layer.getObjects().get(i);
				if (!e.hidden)
					e.render(renderer.getSpriteBatch());
			}
		}
		renderer.getSpriteBatch().end();
	}
	
	public MapHandler(AssetManager assets) {
		this.assets = assets;
		mapCache = new HashMap<String, TiledMap>();
	}
	
	public void dispose() {
		if (topMap != null)
			topMap.dispose();
		if (bottomMap != null)
			bottomMap.dispose();
		if (leftMap != null)
			leftMap.dispose();
		if (rightMap != null)
			rightMap.dispose();
		renderer.dispose();
	}

	public void tick() {
		player.tick();
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
			if (e.hidden)
				continue;
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
	
	public Entity getEntity(String name) {
		for (MapLayer l : renderer.getMap().getLayers()) {
			for (MapObject o : l.getObjects()) {
				Entity e = (Entity) o;
				if (e.getName().equals(name))
					return e;
			}
		}
		return null;
	}
	
	public Script getTrigger(int x, int y, int layer) {
		System.out.println(x + "," + y + "," + layer);
		try {
			return triggers[layer][x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public int getX() {
		return ((TiledMapTileLayer) renderer.getMap().getLayers().get(0)).getWidth();
	}
	
	public int getY() {
		return ((TiledMapTileLayer) renderer.getMap().getLayers().get(0)).getHeight();
	}
	
	public int getLayer() {
		return renderer.getMap().getLayers().getCount();
	}
	
	public String getMapBorder(Dir d) {
		switch (d) {
		case North:
			return (String) renderer.getMap().getProperties().get("Up");
		case South:
			return (String) renderer.getMap().getProperties().get("Down");
		case East:
			return (String) renderer.getMap().getProperties().get("Left");
		case West:
			return (String) renderer.getMap().getProperties().get("Right");
		default:
			return "";
		}
	}
	
	public int getMapBorderTweak(Dir d) {
		switch (d) {
		case North:
			return upTweak;
		case South:
			return bottomTweak;
		case East:
			return rightTweak;
		case West:
			return leftTweak;
		default:
			return 0;
		}
	}
	
	public int getMapBorderBottomHeight() {
		return bottomMap.getY();
	}
}
