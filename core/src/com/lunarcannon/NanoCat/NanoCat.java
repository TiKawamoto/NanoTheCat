package com.lunarcannon.NanoCat;

import com.badlogic.gdx.Game;
import com.lunarcannon.GameContainers.AssetLibrary;
import com.lunarcannon.Screens.MenuScreen;

public class NanoCat extends Game {
	
	
	@Override
	public void create() {			
		AssetLibrary.load();
		setScreen(new MenuScreen(this));
//		setScreen(new GameScreen());
	}
	
	@Override
	public void dispose(){
		super.dispose();
		AssetLibrary.dispose();
	}

}
