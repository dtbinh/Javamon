package com.github.Danice123.javamon.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BorderBox {
	
	private TextureRegion TL;
	private TextureRegion TR;
	private TextureRegion BL;
	private TextureRegion BR;
	private TextureRegion L;
	private TextureRegion R;
	private TextureRegion T;
	private TextureRegion B;
	private TextureRegion FILL;
	
	public final int WIDTH = 8;
	public final int HEIGHT = 8;
	
	public BorderBox(Texture tex) {
		TL = new TextureRegion(tex, 0, 0, 8, 8);
		TR = new TextureRegion(tex, 0, 8, 8, 8);
		BL = new TextureRegion(tex, 0, 16, 8, 8);
		BR = new TextureRegion(tex, 0, 24, 8, 8);
		T = new TextureRegion(tex, 0, 32, 8, 8);
		B = new TextureRegion(tex, 0, 40, 8, 8);
		L = new TextureRegion(tex, 0, 48, 8, 8);
		R = new TextureRegion(tex, 0, 56, 8, 8);
		FILL = new TextureRegion(tex, 0, 64, 8, 8);
	}
	
	public void drawBox(SpriteBatch batch, int x, int y, int width, int height) {
		batch.draw(TL, x, y + height - HEIGHT);
		batch.draw(TR, x + width - WIDTH, y + height - HEIGHT);
		batch.draw(BL, x, y);
		batch.draw(BR, x + width - WIDTH, y);
		
		batch.draw(L, x, y + HEIGHT, WIDTH, height - HEIGHT * 2);
		batch.draw(R, x + width - WIDTH, y + HEIGHT, WIDTH, height - HEIGHT * 2);
		batch.draw(T, x + WIDTH, y + height - HEIGHT, width - WIDTH * 2, HEIGHT);
		batch.draw(B, x + WIDTH, y, width - WIDTH * 2, HEIGHT);
		
		batch.draw(FILL, x + WIDTH, y + HEIGHT, width - WIDTH * 2, height - HEIGHT * 2);
	}

}
