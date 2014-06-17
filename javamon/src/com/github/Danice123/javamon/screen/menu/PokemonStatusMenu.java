package com.github.Danice123.javamon.screen.menu;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.move.Move;
import com.github.Danice123.javamon.pokemon.PokeInstance;
import com.github.Danice123.javamon.pokemon.Pokemon;
import com.github.Danice123.javamon.pokemon.Stat;
import com.github.Danice123.javamon.screen.Screen;

public abstract class PokemonStatusMenu extends Screen {
	
	protected EnumMap<Stat, Integer> stats;
	
	@SuppressWarnings("rawtypes")
	public static Class pokemonStatusMenuClass = com.github.Danice123.javamon.screen.menu.gen1.PokemonStatusMenu.class;
	
	protected PokeInstance pokemon;
	protected Pokemon basePokemon;
	protected Move[] moves;

	public PokemonStatusMenu(Game game, Screen parent, PokeInstance pokemon) {
		super(game, parent);
		this.pokemon = pokemon;
	}
	
	@Override
	public void init() {
		stats = new EnumMap<Stat, Integer>(Stat.class);
		stats.put(Stat.attack, pokemon.getAttack());
		stats.put(Stat.defense, pokemon.getDefense());
		stats.put(Stat.Sattack, pokemon.getSpecialAttack());
		stats.put(Stat.Sdefense, pokemon.getSpecialDefense());
		stats.put(Stat.speed, pokemon.getSpeed());
		stats.put(Stat.health, pokemon.getHealth());
		basePokemon = pokemon.getPokemon();
		moves = new Move[pokemon.getMoveAmount()];
		for (int i = 0; i < moves.length;i++)
			moves[i] = pokemon.getMove(i);
	}
	
	public static void newStartMenu(Game game, Screen parent, PokeInstance pokemon) {
		try {
			pokemonStatusMenuClass.getConstructors()[0].newInstance(game, parent, pokemon);
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
