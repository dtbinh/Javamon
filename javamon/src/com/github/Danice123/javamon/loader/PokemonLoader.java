package com.github.Danice123.javamon.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.github.Danice123.javamon.pokemon.PokeDB;

public class PokemonLoader extends AsynchronousAssetLoader<PokeDB, PokemonLoader.Parameters>{
	
	PokeDB db;
	
	public PokemonLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	static public class Parameters extends AssetLoaderParameters<PokeDB> {}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
		db = new PokeDB(file);
	}

	@Override
	public PokeDB loadSync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
		return db;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			FileHandle file, Parameters parameter) {
		return null;
	}

}
