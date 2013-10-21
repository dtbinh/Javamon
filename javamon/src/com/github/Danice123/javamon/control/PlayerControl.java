package com.github.Danice123.javamon.control;

import java.util.ArrayList;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

import com.github.Danice123.javamon.entity.Dir;

public class PlayerControl implements InputProcessor {
	
	private ArrayList<Dir> moving;
	public boolean A;
	public boolean B;
	public boolean start;
	
	public PlayerControl() {
		moving = new ArrayList<Dir>();
	}
	
	public Dir getControl() {
		if (moving.size() > 0) {
			return moving.get(0);
		}
		return null;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.UP:
			moving.add(Dir.North);
			return true;
		case Keys.DOWN:
			moving.add(Dir.South);
			return true;
		case Keys.RIGHT:
			moving.add(Dir.East);
			return true;
		case Keys.LEFT:
			moving.add(Dir.West);
			return true;
		case Keys.Z:
			A = true;
			return true;
		case Keys.X:
			B = true;
			return true;
		case Keys.ENTER:
			start = true;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.UP:
			moving.remove(Dir.North);
			return true;
		case Keys.DOWN:
			moving.remove(Dir.South);
			return true;
		case Keys.RIGHT:
			moving.remove(Dir.East);
			return true;
		case Keys.LEFT:
			moving.remove(Dir.West);
			return true;
		case Keys.Z:
			A = false;
			return true;
		case Keys.X:
			B = false;
			return true;
		case Keys.ENTER:
			start = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
