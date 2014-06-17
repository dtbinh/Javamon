package com.github.Danice123.javamon.entity;

import com.badlogic.gdx.graphics.Texture;
import com.github.Danice123.javamon.battlesystem.Party;
import com.github.Danice123.javamon.entity.sprite.Spriteset;
import com.github.Danice123.javamon.pokemon.PokeInstance;
import com.github.Danice123.javamon.pokemon.Pokemon;
import com.github.Danice123.javamon.pokemon.Status;
import com.github.Danice123.javamon.script.Script;

public class Trainer extends Walkable implements com.github.Danice123.javamon.battlesystem.Trainer {
	
	private Party party;

	public Trainer(String name, Spriteset sprites, Script script) {
		super(name, sprites, null);
		party = new Party();
		party.add(new PokeInstance(Pokemon.getPokemon("Bulbasaur"), 30));
	}
	
	@Override
	public void activate(Player player) {
		
	}

	@Override
	protected void threaded() {

	}

	@Override
	protected int threadDelay() {
		return 0;
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

	@Override
	public Texture getImage() {
		return new Texture("res/pokemon/1.png");
	}

	@Override
	public Texture getBackImage() {
		return new Texture("res/playerBack.png");
	}
}
