package com.github.Danice123.javamon.screen.menu.gen1;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.Danice123.javamon.Display;
import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.screen.Screen;

public class PokemonMenu extends com.github.Danice123.javamon.screen.menu.PokemonMenu {
	
	private String text;

	public PokemonMenu(Game game, Screen parent) {
		super(game, parent);
		text = "Choose a Pokemon";
	}
	
	@Override
	protected void init() {
		
	}

	@Override
	protected void render2(float delta) {
		shape.begin(ShapeType.Filled);
		for (int i = 0; i < 6; i++) {
			if (getParty().getPokemon(i) != null) {
				shape.setColor(0f, 0f, 0f, 0f);
				shape.rect(55 * Display.scale, Display.HEIGHT - (18 + 20 * i) * Display.scale, 80 * Display.scale, 4 * Display.scale);
				shape.rect(56 * Display.scale, Display.HEIGHT - (19 + 20 * i) * Display.scale, 78 * Display.scale, 6 * Display.scale);
				shape.setColor(1f, 1f, 1f, 0f);
				shape.rect(56 * Display.scale, Display.HEIGHT - (18 + 20 * i) * Display.scale, 78 * Display.scale, 4 * Display.scale);
				shape.setColor(0f, 1f, 0f, 0f);
				float hp = (float) getParty().getPokemon(i).getCurrentHealth() / (float) getParty().getPokemon(i).getHealth();
				if (hp < .5f)
					shape.setColor(1f, 1f, 0f, 0f);
				if (hp < .1f)
					shape.setColor(1f, 0f, 0f, 0f);
				shape.rect(56 * Display.scale, Display.HEIGHT - (18 + 20 * i) * Display.scale, hp * 78 * Display.scale, 4 * Display.scale);
			}
		}
		shape.end();
		batch.begin();
		for (int i = 0; i < 6; i++) {
			if (getParty().getPokemon(i) != null) {
				Display.font.draw(batch, getParty().getPokemon(i).getName(),
						30 * Display.scale, Display.HEIGHT - (2 + 20 * i) * Display.scale);
				Display.font.draw(batch, ":L" + getParty().getPokemon(i).getLevel(),
						120 * Display.scale, Display.HEIGHT - (2 + 20 * i) * Display.scale);
				Display.font.draw(batch, getParty().getPokemon(i).getCurrentHealth() + "/ " + getParty().getPokemon(i).getHealth(),
						170 * Display.scale, Display.HEIGHT - (12 + 20 * i) * Display.scale);
				Display.font.draw(batch, "HP:",
						30 * Display.scale, Display.HEIGHT - (12 + 20 * i) * Display.scale);
			}
		}
		
		//chatbox
		Display.border.drawBox(batch, 0, 0, Display.WIDTH, 40 * Display.scale);
		Display.font.drawWrapped(batch, text, Display.border.WIDTH + 2, 40 * Display.scale - Display.border.HEIGHT, 
				Display.WIDTH - 2* (Display.border.WIDTH + 2));
		  
		batch.end();
	}

	@Override
	protected void tick() {
		
	}

	@Override
	protected void handleKey(Key key) {
		switch (key) {
		case deny:
			disposeMe = true;
			break;
		}
	}

}
