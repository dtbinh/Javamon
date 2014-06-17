package com.github.Danice123.javamon.map;

import java.util.ArrayList;
import com.badlogic.gdx.assets.AssetManager;
import com.github.Danice123.javamon.script.Script;

public class TriggerList {

	public ArrayList<Trigger> triggers;
	
	public Script[][][] load(AssetManager assets, MapHandler map, String mapName) {
		Script[][][] s = new Script[map.getLayer()][map.getX()][map.getY()];
		for (Trigger t : triggers) {
			if(t.script.startsWith("$Warp:")) {
				String[] arg = t.script.split(":");
				s[t.layer][t.x][t.y] = new Script((Script) assets.get("res/scripts/Warp.ps"));
				s[t.layer][t.x][t.y].strings.put("area", arg[1]);
				s[t.layer][t.x][t.y].strings.put("x", arg[2]);
				s[t.layer][t.x][t.y].strings.put("y", arg[3]);
				s[t.layer][t.x][t.y].strings.put("layer", arg[4]);
			} else
				s[t.layer][t.x][t.y] = (Script) assets.get("res/maps/" + mapName + "/" + t.script + ".ps");
		}
		return s;
	}
}
