package com.github.Danice123.javamon.entity;

import java.util.HashMap;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.battlesystem.Party;
import com.github.Danice123.javamon.battlesystem.Trainer;
import com.github.Danice123.javamon.control.MenuControl;
import com.github.Danice123.javamon.control.PlayerControl;
import com.github.Danice123.javamon.entity.sprite.Spriteset;
import com.github.Danice123.javamon.pokemon.PokeInstance;
import com.github.Danice123.javamon.pokemon.Pokemon;
import com.github.Danice123.javamon.pokemon.Status;
import com.github.Danice123.javamon.screen.menu.gen1.StartMenu;
import com.github.Danice123.javamon_bs.PokeData;

public class Player extends Walkable implements Trainer {
	
	public Game game;
	
	private HashMap<String, Boolean> flag;
	public PlayerControl control;
	private MenuControl menu;
	
	public boolean menuOpen;
	
	public PokeData pokeData;
	public String name = "Red";
	private Party party;

	public Player(Game game, Spriteset sprites) {
		super("Player", sprites, null);
		this.game = game;
		this.menu = game.getWorld();
		setTextureRegion(sprites.getSprite(getFacing()));
		control = new PlayerControl();
		
		flag = new HashMap<String, Boolean>();
		
		pokeData = new PokeData();
		pokeData.seen(1);
		pokeData.seen(2);
		party = new Party();
		pokeData.caught(4);
		pokeData.caught(17);
		party.add(new PokeInstance(Pokemon.getPokemon("Bulbasaur"), 100));
		party.add(new PokeInstance(Pokemon.getPokemon("Bulbasaur"), 30));
		party.add(new PokeInstance(Pokemon.getPokemon("Bulbasaur"), 30));
		party.add(new PokeInstance(Pokemon.getPokemon("Bulbasaur"), 30));
		party.add(new PokeInstance(Pokemon.getPokemon("Bulbasaur"), 30));
		party.add(new PokeInstance(Pokemon.getPokemon("Bulbasaur"), 30));
	}
	
	public boolean getFlag(String s) {
		try {
			boolean b = flag.get(s);
			return b;
		} catch (NullPointerException e) {
			return false;
		}
		
	}
	
	public void setFlag(String s, boolean state) {
		flag.put(s, state);
	}
	
	@Override
	protected void threaded() {
		if (menuOpen) {
			if (control.A) {
				menu.accept();
				control.A = false;
			} else if (control.B) {
				menu.deny();
				control.B = false;
			} else if (control.getControl() != null){
				switch (control.getControl()) {
				case East:
					menu.right();
					break;
				case North:
					menu.up();
					break;
				case South:
					menu.down();
					break;
				case West:
					menu.left();
					break;
				default:
					break;
				}
			}
		} else {
			if (control.A) {
				playerActivate();
				control.A = false;
			}
			if (control.start) {
				StartMenu.newStartMenu(game, game.getWorld());
				menuOpen = true;
			}
			Dir d = control.getControl();
			if (d != null) {
				if (d == Dir.North && trueY() == map.getY() - 1) {
					game.getWorld().loadMap(map.getMapBorder(d));
					this.setCoords(trueX() - map.getMapBorderTweak(d), -1, getLayer());
				}
				if (d == Dir.South && trueY() == 0) {
					game.getWorld().loadMap(map.getMapBorder(d));
					this.setCoords(trueX() - map.getMapBorderTweak(d), map.getMapBorderBottomHeight(), getLayer());
				}
				walk(d);
			}
		}
	}
	
	private void playerActivate() {
		switch(getFacing()) {
		case North:
			if (game.getWorld().getEntity(trueX(), trueY() + 1, getLayer()) != null) {
				game.getWorld().getEntity(trueX(), trueY() + 1, getLayer()).activate(this);
			}
			break;
		case South:
			if (game.getWorld().getEntity(trueX(), trueY() - 1, getLayer()) != null) {
				game.getWorld().getEntity(trueX(), trueY() - 1, getLayer()).activate(this);
			}
			break;
		case East:
			if (game.getWorld().getEntity(trueX() + 1, trueY(), getLayer()) != null) {
				game.getWorld().getEntity(trueX() + 1, trueY(), getLayer()).activate(this);
			}
			break;
		case West:
			if (game.getWorld().getEntity(trueX() - 1, trueY(), getLayer()) != null) {
				game.getWorld().getEntity(trueX() - 1, trueY(), getLayer()).activate(this);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected int threadDelay() {
		if (menuOpen)
			return 5;
		return 1;
	}

	@Override
	public Party getParty() {
		return party;
	}

	@Override
	public int firstPokemon() {
		return 0;
	}
	
	@Override
	public boolean hasPokemonLeft() {
		for (int i = 0; i < party.getSize(); i++)
			if (party.getPokemon(i).status != Status.Fainted) return true;
		return false;
	}
}
