package com.github.Danice123.javamon.screen.menu.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.Danice123.javamon.Display;

public class MoveTexture extends Animation {
	
	private Texture texture;
	private int x;
	private int y;
	private int ex;
	private int ey;
	private float mx;
	private float my;

	public MoveTexture(Texture texture, int sx, int sy, int ex, int ey, float speed) {
		this.texture = texture;
		this.x = sx;
		this.y = sy;
		this.ex = ex;
		this.ey = ey;
		mx = ((float) ex - (float) sx) / 2f * speed;
		my = ((float) ey - (float) sy) / 2f * speed;
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texture, x, y, texture.getWidth() * Display.scale, texture.getHeight() * Display.scale);
		x += mx;
		y += my;
		
		if (mx > 0 && x > ex)
			x = ex;
		if (my > 0 && y > ey)
			y = ey;
		if (mx < 0 && x < ex)
			x = ex;
		if (my < 0 && y < ey)
			y = ey;
		
		if (x == ex && y == ey)
			done();
	}

}
