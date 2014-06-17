package com.github.Danice123.javamon.script.commands;

import java.util.HashMap;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.entity.Walkable;
import com.github.Danice123.javamon.script.ScriptException;

public class Follow extends Command {

	@Override
	public void execute(Game game, HashMap<String, String> strings, Entity target) throws ScriptException {
		Walkable follower;
		if (args[0].toLowerCase().equals("p")) {
			follower = game.getPlayer();
		} else if (args[0].toLowerCase().equals("t")) {
			try {
				follower = (Walkable) target;
			} catch (ClassCastException e) {
				throw new ScriptException(ScriptException.BAD_TARGET);
			}
		} else {
			Entity e = game.getWorld().getEntity(args[0]);
			if (e != null) {
				try {
					follower = (Walkable) e;
				} catch (ClassCastException error) {
					throw new ScriptException(ScriptException.BAD_TARGET);
				}
			} else {
				throw new ScriptException(ScriptException.BAD_ARGS);
			}
		}
		if (args[1].toLowerCase().equals("p")) {
			game.getPlayer().setFollowing(follower);
		} else if (args[1].toLowerCase().equals("t")) {
			try {
				((Walkable) target).setFollowing(follower);
			} catch (ClassCastException e) {
				throw new ScriptException(ScriptException.BAD_TARGET);
			}
		} else {
			Entity e = game.getWorld().getEntity(args[1]);
			if (e != null) {
				try {
					((Walkable) e).setFollowing(follower);
				} catch (ClassCastException error) {
					throw new ScriptException(ScriptException.BAD_TARGET);
				}
			} else {
				throw new ScriptException(ScriptException.BAD_ARGS);
			}
		}
	}

}
