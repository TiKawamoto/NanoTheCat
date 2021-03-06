package com.lunarcannon.GameContainers;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import com.badlogic.gdx.Preferences;
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
	private NumberFormat f = NumberFormat.getInstance(Locale.ENGLISH);
	private Preferences adPref;
	
	private boolean gameReset = false;
	private boolean midAirTrigger = false;
	private boolean collide = false;	
	private boolean highBool = false;
	private boolean mainTrigger = false;
	private boolean fbPost = false;
	
	private int runOnce = 0;
	
	private float totalDistance = 0;
	private float highScore = 0;
	private float actualWidth;
	private float muteState = 1;
	
	
	private enum GameState{
		RUNNING, GAMEOVER
	}
	
	private GameState gameState;
	

	public GameWorld() {
				
		cat = new Cat(200, 102);
		scrollHandler = new ScrollingHandler();
		this.platform = scrollHandler.getPlatform();
		gameState = GameState.RUNNING;
		mainTrigger = false;
		
		if(GameStateHandler.getMute()){
			muteState = 0;
		} else{
			muteState = 1;
		}
		
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);

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
			if((catBounds.width + catBounds.x <= platBounds.x) && (catBounds.width + catBounds.x > platBounds.x -14f) && (catBounds.y < platBounds.height)){	//SIDE COLLISIONS				
					
					//Things to run only once upon collision					
					
					scrollHandler.stop();						
					
					if(runOnce == 0){
						//collision sound
						AssetLibrary.collision.play(muteState);
						AssetLibrary.hit.play(muteState * .3f);
						
						//check high score
						totalDistance = Float.parseFloat(f.format(scrollHandler.getTotalDist()));
						highScore = AssetLibrary.getHighScore();
						
						AssetLibrary.setTotalFall();
						AssetLibrary.setDistance(totalDistance);
//						fbTrue();
						
						if(totalDistance > highScore){
							AssetLibrary.setHighScore(totalDistance);
							System.out.println("high: " + AssetLibrary.getHighScore() + " set: " + totalDistance);
							highBool = true;
						} else {
							System.out.println("get " + AssetLibrary.getHighScore());
							highBool = false;
						}
						runOnce++;
					}
				
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
	
//	public void fbTrue(){
//		setFbTrue = AssetLibrary.getFbTrue();
//	}
//	
	public boolean gameOver(){
		return gameReset;		
	}
	
	public float getThisScore(){
		return totalDistance;
	}
	
	public float getHighScore(){
		return highScore;
	}
	
	public boolean getHigh(){
		return highBool;
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
		fbPost = false;
		runOnce = 0;
		
	}
	
	public void setCollide(boolean collide){
		this.collide = collide;
	}
	
	public void setMainTrigger(boolean mainTrigger){
		this.mainTrigger = mainTrigger;
	}
	public boolean returnToMain(){		
		return mainTrigger;
	}
	
	public float getMaxY(){
		return cat.getMaxYPos();
	}
	
	public float getTotalDist(){
		return AssetLibrary.getDistance();
	}
	
	public int getTotalFall(){
		return AssetLibrary.getTotalFall();
	}
	
	public void setActualWidth(float actual){
		actualWidth = actual;
	}
	public float getActualWidth(){
		return actualWidth;
	}
	
	public void SetPostFb(){
		fbPost = true;
	}
	
	public void resetPostFb(){
		fbPost = false;
	}
	public boolean getPostFb(){
		return fbPost;
	}

}
