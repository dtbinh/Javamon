package com.github.Danice123.javamon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.github.Danice123.javamon.entity.Player;
import com.github.Danice123.javamon.entity.sprite.Spriteset;
import com.github.Danice123.javamon.map.EntityList;
import com.github.Danice123.javamon.map.EntityLoader;
import com.github.Danice123.javamon.screen.Loading;
import com.github.Danice123.javamon.screen.World;
import com.github.Danice123.javamon.script.Script;
import com.github.Danice123.javamon.script.ScriptLoader;

public class Game implements Runnable {
	
	private Display display;
	private AssetManager assets;
	
	private Player player;
	private World world;
	
	public static Game game;
	
	public Game(Display display) {
		this.display = display;
		load();
		game = this;
	}
	
	private void load() {
		assets = new AssetManager();
		assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assets.setLoader(Script.class, new ScriptLoader(new InternalFileHandleResolver()));
		assets.setLoader(EntityList.class, new EntityLoader(new InternalFileHandleResolver()));
		
		//Maps
		assets.load("res/maps/Pallet_Town/map.tmx", TiledMap.class);
		assets.load("res/maps/Pallet_Town/entity.lst", EntityList.class);
		
		//Sprites
		assets.load("res/entity/sprites/Sign.png", Texture.class);
		assets.load("res/entity/sprites/Red.png", Texture.class);
		
		//Gui
		assets.load("res/gui/border.png", Texture.class);
		assets.load("res/gui/arrow.png", Texture.class);
		
		//Scripts
		assets.load("res/scripts/Sign.ps", Script.class);
	}
	
	public AssetManager getAssets() {
		return assets;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public World getWorld() {
		return world;
	}

	@Override
	public void run() {
		//Loading
		Loading load = new Loading(this);
		display.setScreen(load);
		synchronized (load) {
			try {load.wait();} catch (InterruptedException e) {e.printStackTrace();}
		}
		
		//Startup
		world = new World(this);
		player = new Player(this, new Spriteset((Texture) assets.get("res/entity/sprites/Red.png")));
		player.setCoords(3, 3, 0);
		new Thread(player).start();
		Gdx.input.setInputProcessor(player.control);
		
		world.loadMap("Pallet_Town");
		
		//Starting game
		display.setScreen(world);
		load.dispose();
	}
	
}
