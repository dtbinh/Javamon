package com.github.Danice123.javamon.script;

import java.util.HashMap;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.script.Command.CommandType;

public class ScriptHandler implements Runnable {
	
	private Game game;
	private Script script;
	private Entity target;
	
	public ScriptHandler(Game game, Script script, Entity target) {
		this.game = game;
		this.script = script;
		this.target = target;
	}

	public void run() {
		target.busy = true;
		for (int i = 0; i < script.commands.length;) {
			if (script.commands[i].type == CommandType.Branch) {
				if (game.getPlayer().getFlag(parseString(script.commands[i].args[0], script.strings))) {
					i = script.branches.get(script.commands[i].args[1]);
				} else {
					i++;
				}
			} else if (script.commands[i].type == CommandType.Stop){
				break;
			} else {
				try {
					script.commands[i].execute(game, script.strings, target);
				} catch (ScriptException e) {
					System.out.println(e.getMessage() + ": Line " + Integer.toString(i + 1));
					return;
				}
				i++;
			}
		}
		target.busy = false;
	}
	
	private String parseString(String s, HashMap<String, String> strings) {
		do {
			int b = s.indexOf("<");
			if (b != -1) {
				int e = s.indexOf(">", b);
				s = s.substring(0, b) + strings.get(s.substring(b + 1, e)) + s.substring(e + 1, s.length());
			} else {
				break;
			}
		} while (true);
		return s;
	}
}
