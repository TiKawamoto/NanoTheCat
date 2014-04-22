package com.lunarcannon.GameContainers;

import GameObjects.Cat;
import GameObjects.ScrollingHandler;

public class GameWorld {
	
	//private NanoCat game;
	
	private Cat cat;
	private ScrollingHandler scrollHandler;
	
	public GameWorld() {
		//this.game = game;
		
		cat = new Cat(400, 400);
		scrollHandler = new ScrollingHandler();
	

	}
	
	public void update(float delta){
		cat.update(delta);
		scrollHandler.update(delta);
		
		
	}
	
	public Cat getCat(){
		return cat;
	}
	
	public ScrollingHandler getScroller(){
		return scrollHandler;
	}

}
