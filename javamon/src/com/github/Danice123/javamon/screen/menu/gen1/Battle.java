package com.github.Danice123.javamon.screen.menu.gen1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.Danice123.javamon.Display;
import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.battlesystem.Battlesystem;
import com.github.Danice123.javamon.battlesystem.Party;
import com.github.Danice123.javamon.screen.Screen;
import com.github.Danice123.javamon.screen.menu.animation.*;

public class Battle extends com.github.Danice123.javamon.screen.menu.Battle {
	
	private int index;
	private int mindex;
	
	private Texture player;
	private Texture enemy;
	private Texture[] playerPokemon;
	private Texture[] enemyPokemon;
	
	private Animation ani = null;
	
	private String text;
	private boolean waitForText;
	
	private int menu;
	private Object menuDone = new Object();
	
	private boolean showEnemyHealth;
	private boolean showPlayerHealth;
	private int playerHealth;

	public Battle(Game game, Screen parent, Battlesystem battle) {
		super(game, parent, battle);
		text = "";
		waitForText = false;
		menu = 0;
		index = 0;
		mindex = 0;
		showPlayerHealth = false;
		showEnemyHealth = false;
	}
	
	@Override
	protected void init() {
		player = battle.getPlayer().getBackImage();
		enemy = battle.getEnemy().getImage();
		
		playerPokemon = new Texture[battle.getPlayer().getParty().getSize()];
		for (int i = 0; i < playerPokemon.length; i++)
			playerPokemon[i] = new Texture("res/pokemon/" + battle.getPlayer().getParty().getPokemon(i).getPokemon().number + ".png");
		
		enemyPokemon = new Texture[battle.getEnemy().getParty().getSize()];
		for (int i = 0; i < enemyPokemon.length; i++)
			enemyPokemon[i] = new Texture("res/pokemon/" + battle.getEnemy().getParty().getPokemon(i).getPokemon().number + ".png");
		synchronized (this) {
			notifyAll();
		}
	}
	
	@Override
	protected void render2(float delta) {
		shape.begin(ShapeType.Filled);
		if(showPlayerHealth) {
			shape.setColor(0f, 0f, 0f, 0f);
			shape.rect(Display.WIDTH - 15 * Display.scale, 55 * Display.scale, 2 * Display.scale, 30 * Display.scale);
			int blength = 115;
			shape.rect(Display.WIDTH - (blength + 5) * Display.scale, 55 * Display.scale, (blength - 10) * Display.scale, 2 * Display.scale);
			shape.rect(Display.WIDTH - (blength + 7) * Display.scale, 55 * Display.scale, 2 * Display.scale, 1 * Display.scale);
			shape.rect(Display.WIDTH - (blength + 3) * Display.scale, 55 * Display.scale, 4 * Display.scale, 3 * Display.scale);
			shape.rect(Display.WIDTH - (blength + 1) * Display.scale, 55 * Display.scale, 2 * Display.scale, 4 * Display.scale);
			
			PokemonMenu.drawHealthBar(shape, battle.getPlayerPokemon(), Display.WIDTH - 100 * Display.scale, 70 * Display.scale,
					80 * Display.scale, 6 * Display.scale);
		}
		
		if (showEnemyHealth) {
			shape.setColor(0f, 0f, 0f, 0f);
			shape.rect(12 * Display.scale, Display.HEIGHT - 30 * Display.scale, 2 * Display.scale, 12 * Display.scale);
			int blength = 115;
			shape.rect(12 * Display.scale, Display.HEIGHT - 30 * Display.scale, blength * Display.scale, 2 * Display.scale);
			//shape.rect(Display.WIDTH - (blength + 7) * Display.scale, Display.HEIGHT - 30 * Display.scale, 2 * Display.scale, 1 * Display.scale);
			//shape.rect(Display.WIDTH - (blength + 3) * Display.scale, Display.HEIGHT - 30 * Display.scale, 4 * Display.scale, 3 * Display.scale);
			//shape.rect(Display.WIDTH - (blength + 1) * Display.scale, Display.HEIGHT - 30 * Display.scale, 2 * Display.scale, 4 * Display.scale);
			PokemonMenu.drawHealthBar(shape, battle.getEnemyPokemon(), 41 * Display.scale, Display.HEIGHT - 25 * Display.scale,
					80 * Display.scale, 6 * Display.scale);
		}
		shape.end();
		batch.begin();
		if (ani != null)
			ani.render(batch);
		Display.border.drawBox(batch, 0, 0, Display.WIDTH, 50 * Display.scale);
		
		if (showPlayerHealth) {
			Display.font.draw(batch, "HP:", 
					Display.WIDTH - 125 * Display.scale, 77 * Display.scale);
			Display.font.draw(batch, battle.getPlayerPokemon().getCurrentHealth() + "/ " + playerHealth, 
					Display.WIDTH - 95 * Display.scale, 68 * Display.scale);
			Display.font.draw(batch, ":L" + battle.getPlayerPokemon().getLevel(), 
					Display.WIDTH - 90 * Display.scale, 86 * Display.scale);
			Display.font.draw(batch, battle.getPlayerPokemon().getName(),
					Display.WIDTH - 114 * Display.scale, 96 * Display.scale);
		}
		
		if (showEnemyHealth) {
			Display.font.draw(batch, "HP:", 
					16 * Display.scale, Display.HEIGHT - 18 * Display.scale);
			Display.font.draw(batch, ":L" + battle.getEnemyPokemon().getLevel(), 
					46 * Display.scale, Display.HEIGHT - 9 * Display.scale);
			Display.font.draw(batch, battle.getEnemyPokemon().getName(), 10 * Display.scale, Display.HEIGHT - Display.scale);
		}
		
		switch (menu) {
		case 1:
			Display.border.drawBox(batch, 110 * Display.scale, 0, Display.WIDTH - 110 * Display.scale, 50 * Display.scale);
			Display.font.draw(batch, "Fight", 	(110 + 20) * Display.scale, 40 * Display.scale);
			Display.font.draw(batch, "PKMN", 	(110 + 80) * Display.scale, 40 * Display.scale);
			Display.font.draw(batch, "Item", 	(110 + 20) * Display.scale, 20 * Display.scale);
			Display.font.draw(batch, "Run", 	(110 + 80) * Display.scale, 20 * Display.scale);
			batch.draw(Display.arrow.rightArrow, 
					(110 + 9 + 60 * (index % 2)) * Display.scale, (31 - 20 * (index / 2)) * Display.scale,
					Display.arrow.rightArrow.getRegionWidth() * Display.scale, 
					Display.arrow.rightArrow.getRegionHeight() * Display.scale);
			break;
		case 2:
			Display.border.drawBox(batch, 50 * Display.scale, 0, Display.WIDTH - 50 * Display.scale, 50 * Display.scale);
			for (int i = 0; i < 4; i++)
				Display.font.draw(batch, battle.getPlayerPokemon().getMove(i).getName(),
						(50 + 20) * Display.scale, (15 + 9 * (3 - i)) * Display.scale);
			batch.draw(Display.arrow.rightArrow, 
					(50 + 9) * Display.scale, (6 + 9 * (3 - mindex)) * Display.scale,
					Display.arrow.rightArrow.getRegionWidth() * Display.scale, 
					Display.arrow.rightArrow.getRegionHeight() * Display.scale);
			break;
		default:
			Display.font.drawWrapped(batch, text, Display.border.WIDTH + 2, 50 * Display.scale - Display.border.HEIGHT, 
					Display.WIDTH - 2* (Display.border.WIDTH + 2));
		}
		batch.end();
	}

	@Override
	protected void tick() {
		
	}

	@Override
	protected void handleKey(Key key) {
		switch(key) {
		case up:
			if (menu == 1 && index > 1)
				index -= 2;
			if (menu == 2 && mindex > 0)
				mindex--;
			break;
		case down:
			if (menu == 1 && index < 2)
				index += 2;
			if (menu == 2 && mindex < 3)
				mindex++;
			break;
		case right:
			if (menu == 1 && index % 2 == 0)
				index++;
			break;
		case left:
			if (menu == 1 && index % 2 == 1)
				index--;
			break;
		case accept:
			if (menu == 1) {
				switch (index) {
				case 0:
					menu = 2;
					break;
				case 1:
					mindex = -2;
					synchronized (menuDone) {
						menuDone.notifyAll();
					}
					menu = 0;
					break;
				case 2:
					break;
				case 3:
					mindex = -1;
					synchronized (menuDone) {
						menuDone.notifyAll();
					}
					menu = 0;
					break;
				}
				break;
			}
			if (menu == 2) {
				synchronized (menuDone) {
					menuDone.notifyAll();
				}
				menu = 0;
				break;
			}
			if (waitForText) {
				synchronized (text) {
					text.notifyAll();
				}
				waitForText = false;
			}
			break;
		case deny:
			if (menu == 2)
				menu = 1;
			break;
		}
	}
	
	@Override
	public void print(String print) {
		text = print;
		waitForText = true;
		w(text);
	}
	
	@Override
	public void printnw(String print) {
		text = print;
	}

	@Override
	public int openMenu() {
		menu = 1;
		index = 0;
		mindex = 0;
		w(menuDone);
		return mindex;
	}

	@Override
	public int openSwitchMenu(Party party, boolean force) {
		return 0;
	}
	
	@Override
	public void battleWildStart() {
		ani = new MultiAnimation(
				new MoveTexture(player,
						-player.getWidth() * Display.scale, 50 * Display.scale,
						10 * Display.scale, 50 * Display.scale, .05f),
				new MoveTexture(enemyPokemon[0],
						-enemy.getWidth() * Display.scale, Display.HEIGHT - (enemy.getHeight() + 10) * Display.scale,
						Display.WIDTH - (enemy.getWidth() + 30) * Display.scale, Display.HEIGHT - (enemy.getHeight() + 10) * Display.scale, .05f)
				);
		synchronized (ani) {
			try {
				ani.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void playerThrowPokemon() {
		playerHealth = battle.getPlayerPokemon().getHealth();
		showPlayerHealth = true;
		showEnemyHealth = true;
	}

	@Override
	public void quit() {
		getGame().getPlayer().menuOpen = false;
		disposeMe = true;
	}
	
	private void w(Object lock) {
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
