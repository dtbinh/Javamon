package com.github.Danice123.javamon.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.github.Danice123.javamon.map.Trigger;
import com.github.Danice123.javamon.map.TriggerList;
import com.thoughtworks.xstream.XStream;

public class TriggerLoader extends AsynchronousAssetLoader<TriggerList, TriggerLoader.Parameters> {
	
	TriggerList list;

	public TriggerLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	static public class Parameters extends AssetLoaderParameters<TriggerList> {}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
		list = (TriggerList) getXStream().fromXML(file.reader());
	}

	@Override
	public TriggerList loadSync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, Parameters parameter) {
		return null;
	}
	
	public static XStream getXStream() {
		XStream s = new XStream();
		s.alias("List", TriggerList.class);
		s.alias("Trigger", Trigger.class);
		return s;
	}
}
