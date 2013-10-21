package com.github.Danice123.javamon.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.Danice123.javamon.Display;
import com.github.Danice123.javamon.Game;

public class StartMenu extends Screen {
	
	private SpriteBatch batch;
	private BorderBox border;
	private Arrow arrow;
	
	private final int width = 90 * Display.scale;
	private final int height = 140 * Display.scale;
	
	private int index;
	private boolean t = false;

	public StartMenu(Game game, Screen parent) {
		super(game, parent);
		renderBehind = true;
		index = 1;
	}
	
	@Override
	protected void init() {
		batch = new SpriteBatch();
		border = new BorderBox((Texture) getGame().getAssets().get("res/gui/border.png"));
		arrow = new Arrow((Texture) getGame().getAssets().get("res/gui/arrow.png"));
	}

	@Override
	protected void render2(float delta) {
		int side = Display.WIDTH - width;
		int top = Display.HEIGHT;
		batch.begin();
		border.drawBox(batch, side, top - height, width, height);
		Display.font.setColor(0f, 0f, 0f, 1f);
		Display.font.draw(batch, "PokeDex", side + 18 * Display.scale, 	top - (12 * Display.scale));
		Display.font.draw(batch, "Pokemon", side + 18 * Display.scale, 	top - (12 * Display.scale) - (18 * Display.scale * 1));
		Display.font.draw(batch, "Bag", 	side + 18 * Display.scale, 	top - (12 * Display.scale) - (18 * Display.scale * 2));
		Display.font.draw(batch, "Trainer", side + 18 * Display.scale, 	top - (12 * Display.scale) - (18 * Display.scale * 3));
		Display.font.draw(batch, "Save", 	side + 18 * Display.scale, 	top - (12 * Display.scale) - (18 * Display.scale * 4));
		Display.font.draw(batch, "Options", side + 18 * Display.scale, 	top - (12 * Display.scale) - (18 * Display.scale * 5));
		Display.font.draw(batch, "Exit", 	side + 18 * Display.scale, 	top - (12 * Display.scale) - (18 * Display.scale * 6));
		
		if (t)
			batch.draw(arrow.rightArrow, side + 6 * Display.scale, top - (20 * Display.scale) - (18 * Display.scale * index),
					arrow.rightArrow.getRegionWidth() * Display.scale, arrow.rightArrow.getRegionHeight() * Display.scale);
		else
			batch.draw(arrow.rightArrowAlt, side + 7 * Display.scale, top - (20 * Display.scale) - (18 * Display.scale * index),
					arrow.rightArrow.getRegionWidth() * Display.scale, arrow.rightArrow.getRegionHeight() * Display.scale);
		batch.end();
	}

	private int counter = 0;
	
	@Override
	protected void tick() {
		if (counter % 30 == 0)
			t = !t;
		counter++;
	}
	
	@Override
	public void up() {
		if (index > 0)
			index--;
		else
			index = 6;
	}
	
	@Override
	public void down() {
		if (index < 6)
			index++;
		else
			index = 0;
	}
	
	@Override
	public void accept() {
		
	}

	@Override
	public void deny() {
		getGame().getPlayer().menuOpen = false;
		disposeMe = true;
	}
}
