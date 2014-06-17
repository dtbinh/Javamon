package com.github.Danice123.javamon.screen.menu.gen1;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.Danice123.javamon.Display;
import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.pokemon.PokeInstance;
import com.github.Danice123.javamon.screen.Screen;

public class PokemonMenu extends com.github.Danice123.javamon.screen.menu.PokemonMenu {
	
	private String text;
	private int index;
	private int index2;
	private boolean submenu;
	private int sindex;

	public PokemonMenu(Game game, Screen parent) {
		super(game, parent);
		text = "Choose a Pokemon";
		index = 0;
		index2 = 0;
		sindex = -1;
		submenu = false;
	}
	
	@Override
	protected void init() {
		super.init();
	}

	@Override
	protected void render2(float delta) {
		shape.begin(ShapeType.Filled);
		for (int i = 0; i < getParty().getSize(); i++) {
			drawHealthBar(shape, getParty().getPokemon(i), 55 * Display.scale, Display.HEIGHT - (19 + 20 * i) * Display.scale, 
					80 * Display.scale, 6 * Display.scale);
		}
		shape.end();
		batch.begin();
		for (int i = 0; i < getParty().getSize(); i++) {
			Display.font.draw(batch, getParty().getPokemon(i).getName(),
				30 * Display.scale, Display.HEIGHT - (2 + 20 * i) * Display.scale);
			Display.font.draw(batch, ":L" + getParty().getPokemon(i).getLevel(),
					120 * Display.scale, Display.HEIGHT - (2 + 20 * i) * Display.scale);
			Display.font.draw(batch, getParty().getPokemon(i).getCurrentHealth() + "/ " + health[i],
					170 * Display.scale, Display.HEIGHT - (12 + 20 * i) * Display.scale);
			Display.font.draw(batch, "HP:",
					30 * Display.scale, Display.HEIGHT - (12 + 20 * i) * Display.scale);
		}
		
		TextureRegion a = Display.arrow.rightArrow;
		if (submenu)
			a = Display.arrow.rightArrowAlt;
		batch.draw(a, 2 * Display.scale, Display.HEIGHT - (10 + 20 * index) * Display.scale,
				a.getRegionWidth() * Display.scale, a.getRegionHeight() * Display.scale);
		
		if (sindex != -1)
			batch.draw(Display.arrow.rightArrowAlt, 2 * Display.scale, Display.HEIGHT - (10 + 20 * sindex) * Display.scale,
					Display.arrow.rightArrowAlt.getRegionWidth() * Display.scale,
					Display.arrow.rightArrowAlt.getRegionHeight() * Display.scale);
		
		//chatbox
		Display.border.drawBox(batch, 0, 0, Display.WIDTH, 40 * Display.scale);
		Display.font.drawWrapped(batch, text, Display.border.WIDTH + 2, 40 * Display.scale - Display.border.HEIGHT, 
				Display.WIDTH - 2* (Display.border.WIDTH + 2));
		
		if (submenu) {
			Display.border.drawBox(batch, Display.WIDTH - 75 * Display.scale, 0, 75 * Display.scale, 50 * Display.scale);
			Display.font.draw(batch, "Status", Display.WIDTH - 60 * Display.scale, (15 + 14 * (2 - 0)) * Display.scale);
			Display.font.draw(batch, "Switch", Display.WIDTH - 60 * Display.scale, (15 + 14 * (2 - 1)) * Display.scale);
			Display.font.draw(batch, "Cancel", Display.WIDTH - 60 * Display.scale, (15 + 14 * (2 - 2)) * Display.scale);
			batch.draw(Display.arrow.rightArrow, Display.WIDTH - 70 * Display.scale, (7 + 14 * (2 - index2)) * Display.scale,
					Display.arrow.rightArrow.getRegionWidth() * Display.scale, Display.arrow.rightArrow.getRegionHeight() * Display.scale);
		}
		batch.end();
	}
	
	public static void drawHealthBar(ShapeRenderer shape, PokeInstance poke, int x, int y, int width, int height) {
		shape.setColor(0f, 0f, 0f, 0f);
		shape.rect(x, y + Display.scale, width, height - 2 * Display.scale);
		shape.rect(x + Display.scale, y, width - 2 * Display.scale, height);
		shape.setColor(1f, 1f, 1f, 0f);
		shape.rect(x + Display.scale, y + Display.scale, width - 2 * Display.scale, height - 2 * Display.scale);
		shape.setColor(0f, 1f, 0f, 0f);
		float hp = poke.getCurrentHealthPercent();
		if (hp < .5f)
			shape.setColor(1f, 1f, 0f, 0f);
		if (hp < .1f)
			shape.setColor(1f, 0f, 0f, 0f);
		shape.rect(x + Display.scale, y + Display.scale, hp * width - 2 * Display.scale, height - 2 * Display.scale);
	}

	@Override
	protected void tick() {
		
	}

	@Override
	protected void handleKey(Key key) {
		switch (key) {
		case up:
			if (submenu) {
				if(index2 > 0)
					index2--;
			}
			else if (index > 0)
				index--;
			else
				index = getParty().getSize() - 1;
			
			if (index == sindex)
				index--;
			if (index == -1)
				index = getParty().getSize() - 1;
			if (index == sindex)
				index--;
			break;
		case down:
			if (submenu) {
				if (index2 < 2)
					index2++;
			} else if (index < getParty().getSize() - 1)
				index++;
			else
				index = 0;
			
			if (index == sindex)
				index++;
			if (index == getParty().getSize())
				index = 0;
			if (index == sindex)
				index++;
			break;
		case accept:
			if (submenu)
				switch(index2) {
				case 0:
					openPokemonStatusMenu(getParty().getPokemon(index));
					break;
				case 1:
					sindex = index;
					index++;
					if (index == getParty().getSize())
						index = 0;
					submenu = false;
					break;
				case 2:
					submenu = false;
					break;
				}
			else if (sindex != -1) {
				getParty().switchPokemon(sindex, index);
				super.init();
				sindex = -1;
			} else
				submenu = true;
			break;
		case deny:
			if (submenu)
				submenu = false;
			else if(sindex != -1)
				sindex = -1;
			else
				disposeMe = true;
			break;
		}
	}
}
