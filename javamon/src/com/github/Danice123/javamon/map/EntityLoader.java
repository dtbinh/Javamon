package com.github.Danice123.javamon.map;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.thoughtworks.xstream.XStream;

public class EntityLoader extends AsynchronousAssetLoader<EntityList, EntityLoader.Parameters> {
	
	EntityList list;

	public EntityLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	static public class Parameters extends AssetLoaderParameters<EntityList> {}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
		list = (EntityList) getXStream().fromXML(file.reader());
	}

	@Override
	public EntityList loadSync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, Parameters parameter) {
		return null;
	}
	
	public static XStream getXStream() {
		XStream s = new XStream();
		s.alias("List", EntityList.class);
		s.alias("Entity", EntityInfo.class);
		return s;
	}
}
