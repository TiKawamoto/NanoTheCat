package com.lunarcannon.GameContainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.lunarcannon.GameObjects.Cat;

public class InputHandler implements InputProcessor {
	private GameWorld world;
	private Cat cat;
	private boolean gameReset = false;

	public InputHandler(GameWorld world) {
		this.world = world;
		cat = world.getCat();
		gameReset = world.gameOver();

	}

	@Override
	public boolean keyDown(int keycode) {
		cat = world.getCat();
		switch (keycode) {
		case Keys.SPACE:
			cat.jump();
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
			// CAT JUMP
			cat.jump();			

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		cat.noJump();
		// RESET AFTER FALLING
		if (world.gameOver()) {
			world.gameReset();
		}

		return true;
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
