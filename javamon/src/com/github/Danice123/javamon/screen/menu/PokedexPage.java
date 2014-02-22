package com.github.Danice123.javamon.screen.menu;

import java.lang.reflect.InvocationTargetException;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.pokemon.Pokemon;
import com.github.Danice123.javamon.screen.Screen;

public abstract class PokedexPage extends Screen {
	
	@SuppressWarnings("rawtypes")
	public static Class pokedexPageMenuClass = com.github.Danice123.javamon.screen.menu.gen1.PokedexPage.class;
	
	private Pokemon pokemon;

	public PokedexPage(Game game, Screen parent, Pokemon pokemon) {
		super(game, parent);
		this.pokemon = pokemon;
	}
	
	protected Pokemon getPokemon() {
		return pokemon;
	}
	
	public static void newStartMenu(Game game, Screen parent, Pokemon pokemon) {
		try {
			pokedexPageMenuClass.getConstructors()[0].newInstance(game, parent, pokemon);
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
