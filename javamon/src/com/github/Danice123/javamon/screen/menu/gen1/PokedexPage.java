package com.github.Danice123.javamon.screen.menu.gen1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.Danice123.javamon.Display;
import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.pokemon.Pokemon;
import com.github.Danice123.javamon.screen.Screen;

public class PokedexPage extends com.github.Danice123.javamon.screen.menu.PokedexPage {
	
	private Texture image;

	public PokedexPage(Game game, Screen parent, Pokemon pokemon) {
		super(game, parent, pokemon);
	}

	@Override
	protected void init() {
		image = new Texture("res/pokemon/" + getPokemon().number + ".png");
	}
	
	@Override
	protected void render2(float delta) {
		shape.begin(ShapeType.Filled);
		shape.setColor(.5f, .5f, .5f, 0f);
		shape.rect(0, 0, Display.WIDTH, Display.HEIGHT);
		shape.setColor(0f, 0f, 0f, 0f);
		shape.rect(5 * Display.scale, 5 * Display.scale, Display.WIDTH - 10 * Display.scale, Display.HEIGHT - 10 * Display.scale);
		shape.setColor(1f, 1f, 1f, 0f);
		shape.rect(6 * Display.scale, 6 * Display.scale, Display.WIDTH - 12 * Display.scale, Display.HEIGHT - 12 * Display.scale);
		shape.setColor(0f, 0f, 0f, 0f);
		shape.rect(6 * Display.scale, 69 * Display.scale, Display.WIDTH - 12 * Display.scale, 2 * Display.scale);
		
		int xoff = 11;
		int yoff = 69 * Display.scale;
		for (int i = 0; i < 13; i++) {
			if (i == 5 || i == 6)
				continue;
			shape.setColor(0f, 0f, 0f, 0f);
			shape.rect(	(xoff + i * 17) * Display.scale,		yoff - 2 * Display.scale,	6 * Display.scale, 6 * Display.scale);
			shape.setColor(.5f, .5f, .5f, 0f);
			shape.rect(	(xoff + i * 17 + 1) * Display.scale,	yoff - 1 * Display.scale,	4 * Display.scale, 4 * Display.scale);
			shape.setColor(1f, 1f, 1f, 0f);
			shape.rect(	(xoff + i * 17 + 1) * Display.scale,	yoff,						4 * Display.scale, 2 * Display.scale);
		}
		shape.end();
		
		batch.begin();
		Display.font.draw(batch, getPokemon().name, 100 * Display.scale, Display.HEIGHT - 10 * Display.scale);
		Display.font.draw(batch, getPokemon().species, 100 * Display.scale, Display.HEIGHT - 26 * Display.scale);
		Display.font.draw(batch, "HT", 100 * Display.scale, Display.HEIGHT - 42 * Display.scale);
		Display.font.draw(batch, Integer.toString(getPokemon().height) + "m",
				140 * Display.scale, Display.HEIGHT - 42 * Display.scale);
		Display.font.draw(batch, "WT", 100 * Display.scale, Display.HEIGHT - 58 * Display.scale);
		Display.font.draw(batch, Integer.toString(getPokemon().weight) + "lb",
				140 * Display.scale, Display.HEIGHT - 58 * Display.scale);
		
		Display.font.drawWrapped(batch, getPokemon().description.replace('\n', ' '), 10 * Display.scale, 60 * Display.scale, 
				220 * Display.scale);
		batch.draw(image, 30 * Display.scale, Display.HEIGHT - 60 * Display.scale, 
				image.getWidth() * Display.scale, image.getHeight() * Display.scale, 
				0, 0, image.getWidth(), image.getHeight(), true, false);
		Display.font.draw(batch, "No." + getPokemon().getFormattedNumber(), 25 * Display.scale, Display.HEIGHT - 75 * Display.scale);
		batch.end();
	}

	@Override
	protected void tick() {
		
	}

	@Override
	protected void handleKey(Key key) {
		if (key == Key.deny || key == Key.accept)
			disposeMe = true;
	}
	
}
