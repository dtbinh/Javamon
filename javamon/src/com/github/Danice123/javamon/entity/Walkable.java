package com.github.Danice123.javamon.entity;

import com.github.Danice123.javamon.entity.sprite.Spriteset;
import com.github.Danice123.javamon.script.Script;

public abstract class Walkable extends Entity {
	
	private Dir facing = Dir.North;
	protected boolean isWalking = false;
	private int ref = 0;
	
	private Walkable following;
	private Dir lastMove;

	public Walkable(String name, Spriteset sprites, Script script) {
		super(name, sprites, script);
	}
	
	public Dir getFacing() {
		return facing;
	}
	
	public void face(Dir dir) {
		facing = dir;
		setTextureRegion(sprites.getSprite(getFacing()));
	}
	
	public void setFollowing(Walkable e) {
		following = e;
	}
	
	public void removeFollower() {
		following = null;
		lastMove = null;
	}
	
	public void tick() {
		super.tick();
		if (isWalking) {
			ref++;
			moveDir(getFacing());
			if (ref == 16) {
				ref = 0;
				isWalking = false;
				setTextureRegion(sprites.getSprite(getFacing()));
				synchronized (this) {
					this.notifyAll();
				}
			}
		}
	}
	
	private void moveDir(Dir dir) {
		switch(dir) {
		case North:
			setY(getY() + 1);
			break;
		case South:
			setY(getY() - 1);
			break;
		case East:
			setX(getX() + 1);
			break;
		case West:
			setX(getX() - 1);
			break;
		default:
		}
	}
	

	
	public boolean walk(Dir dir) {
		if (isWalking)
			return false;
		face(dir);
		setTextureRegion(sprites.getSprite(getFacing()));
		boolean c = false;
		switch (dir) {
		case North:
			c = map.collide(trueX(), trueY() + 1, getLayer());
			break;
		case South:
			c = map.collide(trueX(), trueY() - 1, getLayer());
			break;
		case East:
			c = map.collide(trueX() + 1, trueY(), getLayer());
			break;
		case West:
			c = map.collide(trueX() - 1, trueY(), getLayer());
			break;
		default:
			break;
		}
		if (c) {
			return false;
		}
		isWalking = true;
		setTextureRegion(sprites.getSprite(Dir.toWalk(getFacing())));
		if (following != null) {
			if (lastMove == null) {
				following.face(dir);
				following.isWalking = true;
				following.setTextureRegion(following.sprites.getSprite(Dir.toWalk(dir)));
			} else {
				following.face(lastMove);
				following.isWalking = true;
				following.setTextureRegion(following.sprites.getSprite(Dir.toWalk(lastMove)));
			}
				
			lastMove = dir;
		}
		synchronized (this) {
			try {
				this.wait();
			} catch (InterruptedException e) {}
		}
		return true;
	}
	
	public int[] getHitBox() {
		if (isWalking) {
			switch (getFacing()) {
			case North:
				return new int[] {trueX(), trueY(), trueX(), trueY() - 1};
			case South:
				return new int[] {trueX(), trueY(), trueX(), trueY() + 1};
			case East:
				return new int[] {trueX(), trueY(), trueX() + 1, trueY()};
			case West:
				return new int[] {trueX(), trueY(), trueX() - 1, trueY()};
			default:
				break;
			}
		}
		return new int[]{trueX(), trueY()};
	}

}
