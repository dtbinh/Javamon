package com.github.Danice123.javamon.screen.menu.gen1;

import com.github.Danice123.javamon.Display;
import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.screen.Screen;

public class StartMenu extends com.github.Danice123.javamon.screen.menu.StartMenu {
	
	private final int width = 90 * Display.scale;
	private final int height = 140 * Display.scale;
	
	private int index;

	public StartMenu(Game game, Screen parent) {
		super(game, parent);
		renderBehind = true;
		index = 0;
	}
	
	@Override
	protected void init() {
		
	}

	@Override
	protected void render2(float delta) {
		int side = Display.WIDTH - width;
		int top = Display.HEIGHT;
		batch.begin();
		Display.border.drawBox(batch, side, top - height, width, height);
		Display.font.setColor(0f, 0f, 0f, 1f);
		Display.font.draw(batch, "PokeDex", side + 18 * Display.scale, 	top - (12 * Display.scale));
		Display.font.draw(batch, "Pokemon", side + 18 * Display.scale, 	top - (12 * Display.scale) - (18 * Display.scale * 1));
		Display.font.draw(batch, "Bag", 	side + 18 * Display.scale, 	top - (12 * Display.scale) - (18 * Display.scale * 2));
		Display.font.draw(batch, "Trainer", side + 18 * Display.scale, 	top - (12 * Display.scale) - (18 * Display.scale * 3));
		Display.font.draw(batch, "Save", 	side + 18 * Display.scale, 	top - (12 * Display.scale) - (18 * Display.scale * 4));
		Display.font.draw(batch, "Options", side + 18 * Display.scale, 	top - (12 * Display.scale) - (18 * Display.scale * 5));
		Display.font.draw(batch, "Exit", 	side + 18 * Display.scale, 	top - (12 * Display.scale) - (18 * Display.scale * 6));
		
		batch.draw(Display.arrow.rightArrow, side + 6 * Display.scale, top - (20 * Display.scale) - (18 * Display.scale * index),
				Display.arrow.rightArrow.getRegionWidth() * Display.scale, Display.arrow.rightArrow.getRegionHeight() * Display.scale);
		batch.end();
	}
	
	@Override
	protected void tick() {
		
	}

	@Override
	protected void handleKey(Key key) {
		switch (key) {
		case left:
		case right:
			break;
		case up:
			if (index > 0)
				index--;
			else
				index = 6;
			break;
		case down:
			if (index < 6)
				index++;
			else
				index = 0;
			break;
		case accept:
			switch (index) {
			case 0:
				openPokedex();
				break;
			case 1:
				openPokemon();
				break;
			case 2:
				openBag();
				break;
			case 3:
				openTrainer();
				break;
			case 4:
				openSave();
				break;
			case 5:
				openOptions();
				break;
			case 6:
				getGame().getPlayer().menuOpen = false;
				disposeMe = true;
				break;
			}
			break;
		case deny:
			getGame().getPlayer().menuOpen = false;
			disposeMe = true;
			break;
		}
	}
}
