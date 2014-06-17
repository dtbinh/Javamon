package com.github.Danice123.javamon.entity;

import com.github.Danice123.javamon.entity.sprite.Spriteset;
import com.github.Danice123.javamon.script.Script;

public class NPC extends Walkable {

	public NPC(String name, Spriteset sprites, Script script) {
		super(name, sprites, script);
		setTextureRegion(sprites.getSprite(getFacing()));
	}

	@Override
	protected void threaded() {
		
	}

	@Override
	protected int threadDelay() {
		return 0;
	}

}
