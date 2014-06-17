package com.github.Danice123.javamon.script;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.files.FileHandle;
import com.github.Danice123.javamon.script.commands.Command;

public class Script {
	
	public Command[] commands;
	public HashMap<String,String> strings;
	public HashMap<String,Integer> branches;
	
	public Script(FileHandle file) {
		try {
			BufferedReader in = new BufferedReader(file.reader());
			ArrayList<Command> c = new ArrayList<Command>();
			strings = new HashMap<String,String>();
			branches = new HashMap<String, Integer>();
			for (String line = in.readLine();line != null; line = in.readLine()) {
				if (line.startsWith("!")) {
					String[] s = line.substring(1).split(":");
					if (s.length < 2) {
						c.add(newCommand(s[0], new String[1]));
					} else {
						c.add(newCommand(s[0], s[1].split(" ")));
					}
				}
				if (line.startsWith("$")) {
					String[] s = line.substring(1).split(":");
					strings.put(s[0], s[1]);
				}
				if (line.startsWith("@")) {
					branches.put(line.substring(1), c.size());
				}
			}
			in.close();
			commands = c.toArray(new Command[c.size()]);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Command Probably not found");
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Script(Script copy) {
		this.commands = copy.commands;
		this.strings = (HashMap<String, String>) copy.strings.clone();
		this.branches = copy.branches;
	}
	
	@SuppressWarnings("unchecked")
	private Command newCommand(String name, String[] args) throws Exception {
		Class<Command> c = (Class<Command>) Class.forName("com.github.Danice123.javamon.script.commands." + name);
		Command command = c.newInstance();
		command.args = args;
		return command;
	}
}
