package com.github.Danice123.javamon.screen.menu.gen1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.Danice123.javamon.Display;
import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.screen.Screen;

public class Pokedex extends com.github.Danice123.javamon.screen.menu.Pokedex {
	
	private int top;
	private int index;
	private int index2;
	private boolean level;
	
	public static String pokeball = "res/gui/pokedex/pokeball.png";

	public Pokedex(Game game, Screen parent) {
		super(game, parent);
		top = 1;
		index = 0;
		index2 = 0;
		level = false;
	}
	
	@Override
	protected void init() {
		super.init();
	}

	@Override
	protected void render2(float delta) {
		shape.begin(ShapeType.Filled);
		shape.setColor(.2f, .2f, .2f, 0f);
		shape.rect(0, 0, Display.WIDTH,	Display.HEIGHT);
		shape.setColor(1f, 1f, 1f, 0f);
		shape.rect(1 * Display.scale, 1 * Display.scale, Display.WIDTH - 2 * Display.scale, Display.HEIGHT - 2 * Display.scale);
		shape.setColor(0f, 0f, 0f, 0f);
		shape.rect(Display.WIDTH - 45 * Display.scale, 0, 2 * Display.scale, Display.WIDTH);
		
		int xoff = Display.WIDTH - 45 * Display.scale;
		int yoff = 11;
		for (int i = 0; i < 8; i++) {
			shape.setColor(0f, 0f, 0f, 0f);
			shape.rect(	xoff - 2 * Display.scale,	(yoff + i * 17) * Display.scale,		6 * Display.scale, 6 * Display.scale);
			shape.setColor(.5f, .5f, .5f, 0f);
			shape.rect(	xoff - 1 * Display.scale,	(yoff + i * 17 + 1) * Display.scale,	4 * Display.scale, 4 * Display.scale);
			shape.setColor(1f, 1f, 1f, 0f);
			shape.rect(	xoff,						(yoff + i * 17 + 1) * Display.scale,	2 * Display.scale, 4 * Display.scale);
		}
		
		shape.setColor(0f, 0f, 0f, 0f);
		shape.rect(xoff + 4 * Display.scale, 83 * Display.scale, Display.WIDTH, 1 * Display.scale);
		shape.rect(xoff + 4 * Display.scale, 80 * Display.scale, Display.WIDTH, 2 * Display.scale);
		shape.end();
		batch.begin();
		Display.font.draw(batch, "Contents", 9 * Display.scale, Display.HEIGHT - 10 * Display.scale);
		Display.font.draw(batch, "Seen", Display.WIDTH - 33 * Display.scale, 137 * Display.scale);
		Display.font.draw(batch, Integer.toString(amountSeen()), Display.WIDTH - 16 * Display.scale, 128 * Display.scale);
		Display.font.draw(batch, "Own", Display.WIDTH - 33 * Display.scale, 112 * Display.scale);
		Display.font.draw(batch, Integer.toString(amountCaught()), Display.WIDTH - 16 * Display.scale, 103 * Display.scale);
		
		for (int i = 0; i < 7; i++) {
			Display.font.draw(batch, getPokemonName(top + 6 - i), 35 * Display.scale, (i * 17 + 27) * Display.scale);
			Display.font.draw(batch, getPokemonNumber(top + 6 - i), 11 * Display.scale, (i * 17 + 35) * Display.scale);
			if (isCaught(top + 6 - i)) {
				Texture t = getGame().getAssets().get(pokeball);
				batch.draw(t, 25 * Display.scale, (i * 17 + 20) * Display.scale,
						t.getWidth() * Display.scale, t.getHeight() * Display.scale);
			}
		}
		
		Display.font.draw(batch, "Quit", Display.WIDTH - 33 * Display.scale, 	(1 + 1 * 17) * Display.scale);
		Display.font.draw(batch, "Area", Display.WIDTH - 33 * Display.scale, 	(1 + 2 * 17) * Display.scale);
		Display.font.draw(batch, "Cry", Display.WIDTH - 33 * Display.scale, 	(1 + 3 * 17) * Display.scale);
		Display.font.draw(batch, "Data", Display.WIDTH - 33 * Display.scale, 	(1 + 4 * 17) * Display.scale);
		
		if (level)
			batch.draw(Display.arrow.rightArrow, Display.WIDTH - 43 * Display.scale, ((3 - index2) * 17 + 10) * Display.scale,
					Display.arrow.rightArrow.getRegionWidth() * Display.scale, Display.arrow.rightArrow.getRegionHeight() * Display.scale);
		else 
			batch.draw(Display.arrow.rightArrow, 1 * Display.scale, ((6 - index) * 17 + 17) * Display.scale,
					Display.arrow.rightArrow.getRegionWidth() * Display.scale, Display.arrow.rightArrow.getRegionHeight() * Display.scale);
		batch.end();
	}

	@Override
	protected void tick() {
		
	}

	@Override
	protected void handleKey(Key key) {
		switch (key) {
		case up:
			if (level && index2 > 0)
				index2--;
			else if (index > 1)
				index--;
			else if (top > 1)
				top--;
			else if (index > 0)
				index--;
			break;
		case down:
			if (level && index2 < 3)
				index2++;
			else if (index < 5)
				index++;
			else if (top < getGame().db.getNumberPokemon() - 7)
				top++;
			else if (index < 6)
				index++;
			break;
		case accept:
			if (!level && isSeen(top + index))
				level = true;
			else if (level)
				switch (index2) {
				case 0:
					PokedexPage.newStartMenu(getGame(), this, cache[top + index - 1]);
					level = false;
					break;
				case 1:
					//Cry
					break;
				case 2:
					//Area
					break;
				case 3:
					disposeMe = true;
					break;
				}
			break;
		case deny:
			if (level)
				level = false;
			else
				disposeMe = true;
			break;
		default:
			break;
		}
	}
}
