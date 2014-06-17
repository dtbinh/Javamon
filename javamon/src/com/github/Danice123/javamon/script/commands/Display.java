package com.github.Danice123.javamon.script.commands;

import java.util.HashMap;

import com.github.Danice123.javamon.Game;
import com.github.Danice123.javamon.entity.Entity;
import com.github.Danice123.javamon.screen.menu.ChatBox;
import com.github.Danice123.javamon.script.ScriptException;

public class Display extends Command {

	@Override
	public void execute(Game game, HashMap<String, String> strings, Entity target) throws ScriptException {
		try {
			Object lock = new Object();
			if (game.getPlayer().menuOpen) {
				new ChatBox(game, game.getWorld(), parseString(strings.get(args[0]), strings), lock); //Should never occur
				System.out.println("What?");
			} else {
				new ChatBox(game, game.getWorld(), parseString(strings.get(args[0]), strings), lock);
				game.getPlayer().menuOpen = true;
			}
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {}
			}
		} catch (NullPointerException e) {
			throw new ScriptException(ScriptException.BAD_STRING);
		}
	}

}
