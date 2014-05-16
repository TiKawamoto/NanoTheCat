package com.lunarcannon.GameContainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.lunarcannon.GameObjects.Cat;
import com.lunarcannon.NanoCat.NanoCat;

public class InputHandler implements InputProcessor {
	private NanoCat game;
	private GameWorld world;
	private Cat cat;
	private boolean gameReset = false;
	private Vector3 touchPos;
	
	private float muteState = 1;

	public InputHandler(GameWorld world) {
		this.world = world;
		cat = world.getCat();
		gameReset = world.gameOver();
		Gdx.input.setCatchBackKey(true);
		
		if(GameStateHandler.getMute()){
			muteState = 0;
		} else{
			muteState = 1;
		}

	}

	@Override
	public boolean keyDown(int keycode) {
		cat = world.getCat();
		switch (keycode) {
		case Keys.SPACE:
			cat.jump();
			break;
		case Keys.BACK:
			AssetLibrary.run.stop();
			world.setMainTrigger(true);
			break;	
		case Keys.BACKSPACE:
			AssetLibrary.run.stop();
			world.setMainTrigger(true);
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
		if(world.gameOver()){
			
		}else{
			// CAT JUMP
			cat.jump();		
		}
				

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		cat.noJump();
		// RESET AFTER FALLING
		if (world.gameOver()) {	
	
			touchPos = GameStateHandler.getTouchPos();
					
			if(touchPos.x > (world.getActualWidth() - 420)  && touchPos.x < (world.getActualWidth() - 230) 
					&&  touchPos.y > 350 && touchPos.y < 425){
				world.SetPostFb();
				AssetLibrary.select.play(muteState * .5f);
			} else if (touchPos.x > (0)  && touchPos.x < (350) 
					&&  touchPos.y > 480 && touchPos.y < 540){
				AssetLibrary.run.stop();
				world.setMainTrigger(true);
			} else if (touchPos.x > (0)  && touchPos.x < (world.getActualWidth()) 
					&&  touchPos.y > 0 && touchPos.y < 540){
				world.gameReset();
				AssetLibrary.select.play(muteState * .5f);
			}
					
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
