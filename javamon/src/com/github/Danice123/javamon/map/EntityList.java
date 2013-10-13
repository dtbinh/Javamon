package com.github.Danice123.javamon.map;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayers;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.entity.Sign;
import com.github.Danice123.javamon.entity.sprite.Spriteset;
import com.github.Danice123.javamon.script.Script;

public class EntityList {
	
	public ArrayList<EntityInfo> entities;
	
	public void load(AssetManager assets, MapHandler assign, MapLayers map) {
		for (EntityInfo e : entities) {
			Entity entity = null;
			switch (e.type) {
			case Sign:
				entity = new Sign(e.name, 
						new Spriteset((Texture) assets.get("res/entity/sprites/" + e.spriteset + ".png")), 
						(Script) assets.get("res/scripts/Sign.ps"));
				break;
			default:
				break;
			}
			if (entity == null)
				System.out.println("Problem");
			entity.setCoords(e.x, e.y, e.layer);
			entity.addStrings(e.strings);
			entity.place(assign);
			map.get(e.layer).getObjects().add(entity);
		}
	}
	
	public enum Type {
		Sign;
	}
}
