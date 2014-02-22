package com.github.Danice123.javamon.screen.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.pokemon.Pokemon;
import com.github.Danice123.javamon.screen.Screen;

public abstract class Pokedex extends Screen {
	
	@SuppressWarnings("rawtypes")
	public static Class pokedexMenuClass = com.github.Danice123.javamon.screen.menu.gen1.Pokedex.class;
	
	protected Pokemon[] cache;

	public Pokedex(Game game, Screen parent) {
		super(game, parent);
	}
	
	@Override
	protected void init() {
		cache = getGame().db.getPokemonList();
	}
	
	protected int amountSeen() {
		return getGame().getPlayer().pokeData.amountSeen();
	}
	
	protected int amountCaught() {
		return getGame().getPlayer().pokeData.amountCaught();
	}
	
	protected boolean isCaught(int i) {
		return getGame().getPlayer().pokeData.isCaught(i);
	}
	
	protected boolean isSeen(int i) {
		return getGame().getPlayer().pokeData.isSeen(i);
	}
	
	protected String getPokemonName(int i) {
		if (getGame().getPlayer().pokeData.isCaught(i) || getGame().getPlayer().pokeData.isSeen(i)) {
			return cache[i - 1].name;
		}
		return "-------------------";
	}
	
	protected String getPokemonNumber(int i) {
		if (cache[i - 1] == null)
			return "???";
		return cache[i - 1].getFormattedNumber();
	}
	
	public static void newStartMenu(Game game, Screen parent) {
		try {
			pokedexMenuClass.getConstructors()[0].newInstance(game, parent);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
}
