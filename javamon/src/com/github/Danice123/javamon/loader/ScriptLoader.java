package com.github.Danice123.javamon.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.github.Danice123.javamon.script.Script;

public class ScriptLoader extends AsynchronousAssetLoader<Script, ScriptLoader.Parameters> {
	
	Script script;

	public ScriptLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	static public class Parameters extends AssetLoaderParameters<Script> {}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
		script = null;
		script = new Script(file);
	}

	@Override
	public Script loadSync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
		return script;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, Parameters parameter) {
		return null;
	}
}
