package com.github.Danice123.javamon.entity;

import com.github.Danice123.javamon.entity.sprite.Spriteset;
import com.github.Danice123.javamon.script.Script;

public class Stationary extends Entity {

	public Stationary(String name, Spriteset sprites, Script script) {
		super(name, sprites, script);
		if (sprites != null)
			setTextureRegion(sprites.getSprite(Dir.South));
	}

	@Override
	protected void threaded() {
		
	}

	@Override
	protected int threadDelay() {
		return 0;
	}
}
