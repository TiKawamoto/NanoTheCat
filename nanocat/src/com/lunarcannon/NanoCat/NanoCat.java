package com.lunarcannon.NanoCat;

import com.badlogic.gdx.Game;
import com.lunarcannon.Screens.GameScreen;

public class NanoCat extends Game {
	
	
	@Override
	public void create() {				
		setScreen(new GameScreen());
	}

}
