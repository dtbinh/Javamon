package com.github.Danice123.javamon.entity.sprite;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.Danice123.javamon.entity.Dir;

public class Spriteset {
	
	private HashMap<Dir, TextureRegion> sprites;
	private HashMap<Dir, TextureRegion> alt;
	private boolean s = false;
	
	private static Dir list[] = {Dir.South, Dir.North, Dir.East, Dir.West, Dir.SouthW, Dir.NorthW, Dir.EastW, Dir.WestW};
	
	public Spriteset(Texture tex) {
		sprites = new HashMap<Dir, TextureRegion>();
		alt = new HashMap<Dir, TextureRegion>();
		int i = 0;
		for(int y = 0; y*16 < tex.getHeight();y++) {
			for(int x = 0; x*16 < tex.getWidth(); x++) {
				if (i == list.length)
					return;
				sprites.put(list[i], new TextureRegion(tex, x*16, y*16, 16, 16));
				if (Dir.toWalk(list[i]) == list[i] && list[i] != Dir.EastW && list[i] != Dir.WestW) {
					TextureRegion a = new TextureRegion(tex, x*16, y*16, 16, 16);
					a.flip(true, false);
					alt.put(list[i], a);
				}
				i++;
			}
		}
	}
	
	public TextureRegion getSprite(Dir type) {
		if (Dir.toWalk(type) == type && type != Dir.EastW && type != Dir.WestW) {
			s = !s;
			if (s)
				return alt.get(type);
		}
		return sprites.get(type);
	}
}