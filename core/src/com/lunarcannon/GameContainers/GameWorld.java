package com.lunarcannon.GameContainers;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.lunarcannon.GameObjects.Cat;
import com.lunarcannon.GameObjects.Platform;
import com.lunarcannon.GameObjects.ScrollingHandler;

public class GameWorld {

	private Cat cat;
	private ScrollingHandler scrollHandler;
	private Platform p;
	private ArrayList<Platform> platform = new ArrayList<Platform>();
	private Iterator<Platform> pIter;
	private Rectangle catBounds, platBounds;
	private boolean gameReset = false;
	private boolean midAirTrigger = false;
	private boolean collide = false;
	
	private enum GameState{
		RUNNING, GAMEOVER
	}
	
	private GameState gameState;
	

	public GameWorld() {
		// this.game = game;

		cat = new Cat(200, 102);
		scrollHandler = new ScrollingHandler();
		this.platform = scrollHandler.getPlatform();
		gameState = GameState.RUNNING;

	}

	public void update(float delta) {
		
		switch(gameState){
		case RUNNING:			
			cat.update(delta);
			scrollHandler.update(delta);
			collisionChecker(delta);
			break;
		
		case GAMEOVER:
			gameOver();
			break;
		}
		
		

	}//END UPDATE
	
	public void collisionChecker(float delta){
		//BEGIN PLATFORM COLLISION CHECK
		midAirTrigger = true;
		
		for (int i = 0; i < platform.size(); i++) {
			
			p = platform.get(i);
			catBounds = new Rectangle(cat.getPosition().x, cat.getPosition().y, cat.getWidth(), cat.getHeight());
			platBounds = new Rectangle(p.getxPos(), p.getyPos(), p.getWidth(), p.getHeight());			
					
			//CHECK IF ON TOP OF PLATFORM			
			if((catBounds.width + catBounds.x > platBounds.x) && (catBounds.x < platBounds.x + platBounds.width - 10f) && (catBounds.y <= platBounds.height)){
					midAirTrigger = false;
					cat.setY((float) (platBounds.height)); 
					cat.landed();
					
					if(catBounds.y >= platBounds.height + 0.4f){
						midAirTrigger = false;	
					}
					
			} 
			
			
			
			//CHECK FOR SIDE COLLISION			
			if((catBounds.width + catBounds.x <= platBounds.x) && (catBounds.width + catBounds.x > platBounds.x -12f) && (catBounds.y < platBounds.height)){	//SIDE COLLISIONS				
					
					scrollHandler.stop();
					cat.collide();
					collide = true;
					cat.setX(platBounds.x - cat.getWidth());	
					
					
					
					if(catBounds.y < -300){
						cat.stop();	
						System.out.println("GAMEOVER");
						gameState = GameState.GAMEOVER;
						gameReset = true;
					}

			}//END SIDE COLLISION LOGIC		
		}//END PLATFORM ITERATION FOR LOOP
		
	}
	
	public boolean gameOver(){
		return gameReset;		
	}

	public Cat getCat() {
		return cat;
	}
	public boolean getMidAirTrigger(){
		return midAirTrigger;
	}
	
	public boolean getCollide(){
		return collide;
	}
	public boolean getReset(){
		return gameReset;
	}

	public ScrollingHandler getScroller() {
		return scrollHandler;
	}
	
	public void gameReset(){
		gameState = GameState.RUNNING;
		scrollHandler.reset();
		cat.reset();
		gameReset = false;
		collide = false;	
		
		
	}
	
	public void setCollide(boolean collide){
		this.collide = collide;
	}

}
