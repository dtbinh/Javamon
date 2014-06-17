package com.github.Danice123.javamon.screen.menu.gen1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.Danice123.javamon.Display;
import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.pokemon.PokeInstance;
import com.github.Danice123.javamon.pokemon.Stat;
import com.github.Danice123.javamon.screen.Screen;

public class PokemonStatusMenu extends com.github.Danice123.javamon.screen.menu.PokemonStatusMenu {
	
	private Texture image;
	private boolean page;
	private int health;

	public PokemonStatusMenu(Game game, Screen parent, PokeInstance pokemon) {
		super(game, parent, pokemon);
		page = false;
		health = pokemon.getHealth();
	}
	
	@Override
	public void init() {
		super.init();
		image = new Texture("res/pokemon/" + basePokemon.number + ".png");
	}

	@Override
	protected void render2(float delta) {
		shape.begin(ShapeType.Filled);
		shape.setColor(0f, 0f, 0f, 0f);
		
		shape.rect(Display.WIDTH - 5 * Display.scale, 2 * Display.scale, 2 * Display.scale, 70 * Display.scale);
		int blength = 110;
		shape.rect(Display.WIDTH - (blength + 5) * Display.scale, 2 * Display.scale, blength * Display.scale, 2 * Display.scale);
		shape.rect(Display.WIDTH - (blength + 7) * Display.scale, 2 * Display.scale, 2 * Display.scale, 1 * Display.scale);
		shape.rect(Display.WIDTH - (blength + 3) * Display.scale, 2 * Display.scale, 4 * Display.scale, 3 * Display.scale);
		shape.rect(Display.WIDTH - (blength + 1) * Display.scale, 2 * Display.scale, 2 * Display.scale, 4 * Display.scale);
		
		shape.rect(Display.WIDTH - 5 * Display.scale, 85 * Display.scale, 2 * Display.scale, 70 * Display.scale);
		blength = 140;
		shape.rect(Display.WIDTH - (blength + 5) * Display.scale, 85 * Display.scale, blength * Display.scale, 2 * Display.scale);
		shape.rect(Display.WIDTH - (blength + 7) * Display.scale, 85 * Display.scale, 2 * Display.scale, 1 * Display.scale);
		shape.rect(Display.WIDTH - (blength + 3) * Display.scale, 85 * Display.scale, 4 * Display.scale, 3 * Display.scale);
		shape.rect(Display.WIDTH - (blength + 1) * Display.scale, 85 * Display.scale, 2 * Display.scale, 4 * Display.scale);
		
		if (!page)
			PokemonMenu.drawHealthBar(shape, pokemon, Display.WIDTH - 90 * Display.scale, 125 * Display.scale,
					80 * Display.scale, 6 * Display.scale);
		shape.end();
		batch.begin();
		Display.font.draw(batch, pokemon.getName(), Display.WIDTH - 135 * Display.scale, 155 * Display.scale);
		Display.font.draw(batch, "No." + basePokemon.getFormattedNumber(), 10 * Display.scale, 90 * Display.scale);
		
		batch.draw(image, 30 * Display.scale, Display.HEIGHT - 60 * Display.scale, 
				image.getWidth() * Display.scale, image.getHeight() * Display.scale, 
				0, 0, image.getWidth(), image.getHeight(), true, false);
		
		if (!page) {
		Display.font.draw(batch, "HP:", Display.WIDTH - 115 * Display.scale, 132 * Display.scale);
		Display.font.draw(batch, pokemon.getCurrentHealth() + "/ " + health, Display.WIDTH - 85 * Display.scale, 123 * Display.scale);
		Display.font.draw(batch, ":L" + pokemon.getLevel(), Display.WIDTH - 80 * Display.scale, 141 * Display.scale);
		
		Display.font.draw(batch, "Status/" + pokemon.status.name, Display.WIDTH - 135 * Display.scale, 97 * Display.scale);
			
		Display.border.drawBox(batch, 0, 0, 110 * Display.scale, 80 * Display.scale);
		Display.font.draw(batch, "Attack", 10 * Display.scale, 		(72 - 0 * 14) * Display.scale);
		Display.font.draw(batch, "Defense", 10 * Display.scale, 	(72 - 1 * 14) * Display.scale);
		Display.font.draw(batch, "Speed", 10 * Display.scale, 		(72 - 2 * 14) * Display.scale);
		Display.font.draw(batch, "SAttack", 10 * Display.scale, 	(72 - 3 * 14) * Display.scale);
		Display.font.draw(batch, "SDefense", 10 * Display.scale,	(72 - 4 * 14) * Display.scale);
		
		Display.font.draw(batch, Integer.toString(stats.get(Stat.attack)), 80 * Display.scale, (72 - 0 * 14) * Display.scale);
		Display.font.draw(batch, Integer.toString(stats.get(Stat.defense)), 80 * Display.scale, (72 - 1 * 14) * Display.scale);
		Display.font.draw(batch, Integer.toString(stats.get(Stat.speed)), 80 * Display.scale, (72 - 2 * 14) * Display.scale);
		Display.font.draw(batch, Integer.toString(stats.get(Stat.Sattack)), 80 * Display.scale, (72 - 3 * 14) * Display.scale);
		Display.font.draw(batch, Integer.toString(stats.get(Stat.Sdefense)), 80 * Display.scale, (72 - 4 * 14) * Display.scale);
		
		Display.font.draw(batch, "Type1/ " + basePokemon.type1.name, 112 * Display.scale, (73 - 0 * 14) * Display.scale);
		if (basePokemon.dualType)
		Display.font.draw(batch, "Type2/ " + basePokemon.type2.name, 112 * Display.scale, (73 - 1 * 14) * Display.scale);
		Display.font.draw(batch, "ID/ " + pokemon.idNumber, 112 * Display.scale, (73 - 2 * 14) * Display.scale);
		Display.font.draw(batch, "OT/ " + pokemon.originalTrainer, 112 * Display.scale, (73 - 3 * 14) * Display.scale);
		} else {
		Display.border.drawBox(batch, 0, 0, Display.WIDTH, 80 * Display.scale);
		
		for (int i = 0; i < moves.length; i++) {
			Display.font.draw(batch, moves[i].getName(), 10 * Display.scale, (72 - i * 17) * Display.scale);
			Display.font.draw(batch, "pp " + pokemon.CPP[i] + "/" + moves[i].getPP(),
					Display.WIDTH - 80 * Display.scale, (72 - i * 17) * Display.scale);
		}
		}
		batch.end();
	}

	@Override
	protected void tick() {
		
	}

	@Override
	protected void handleKey(Key key) {
		switch(key) {
		case accept:
			if (!page)
				page = true;
			else
				disposeMe = true;
			break;
		case deny:
			if (page)
				page = false;
			else
				disposeMe = true;
			break;
		}
	}


}
