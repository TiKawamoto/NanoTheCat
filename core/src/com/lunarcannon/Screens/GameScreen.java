package com.lunarcannon.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.lunarcannon.GameContainers.AssetLibrary;
import com.lunarcannon.GameContainers.GameRenderer;
import com.lunarcannon.GameContainers.GameStateHandler;
import com.lunarcannon.GameContainers.GameWorld;
import com.lunarcannon.GameContainers.InputHandler;
import com.lunarcannon.NanoCat.NanoCat;

public class GameScreen implements Screen{
	private NanoCat game;
	private GameWorld world;
	private GameRenderer renderer;
	private int doOnce = 0;
	private float muteState = 1;
		
    public GameScreen(NanoCat game) {
    	this.game = game; 
        world = new GameWorld();
        renderer = new GameRenderer(world);
        
        Gdx.input.setInputProcessor(new InputHandler(world));
        
        if(GameStateHandler.getMute()){
        	muteState = 0;
        } else {
        	muteState = 1;
        }
    }

    @Override
    public void render(float delta) {
    	world.update(delta);
    	renderer.render(delta);
    	
    	   	
    	if(world.getCollide()){
    		
//    		if(doOnce < 1){
//    			game.extInt.fbAutoScore(world.getThisScore());
//    			doOnce++;
//    		}
    		
    		
    		if(world.getPostFb()){
    				game.extInt.fbSubmitScore(world.getThisScore());
    		}
    		world.resetPostFb();

    		if(game.extInt.getSignedIn()){
        		achievementChecker();	
        	}   	    	
    	} else {
//    		doOnce = 0;
    	}
    	
    	if(world.returnToMain()){
    		AssetLibrary.run.stop();
    		AssetLibrary.select.play(muteState * .5f);
    		game.setScreen(new MenuScreen(game));
    	}
    }

    @Override
    public void resize(int width, int height) {
       
    }

    @Override
    public void show() {
      
    }

    @Override
    public void hide() {
       
    }

    @Override
    public void pause() {
               
    }

    @Override
    public void resume() {
       
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
    
    public void achievementChecker(){
    	if(world.getThisScore() >= 25){
    		game.extInt.unlockAchievement("CgkI1Yuo44kEEAIQAQ");
    	}
    	if(world.getThisScore() >= 100){
    		game.extInt.unlockAchievement("CgkI1Yuo44kEEAIQAg");
    	}
    	if(world.getThisScore() >= 250){
    		game.extInt.unlockAchievement("CgkI1Yuo44kEEAIQAw");
    	}
    	if(world.getThisScore() >= 500){
    		game.extInt.unlockAchievement("CgkI1Yuo44kEEAIQBA");
    	}
    	if(world.getThisScore() >= 50){
    		game.extInt.unlockAchievement("CgkI1Yuo44kEEAIQBQ");
    	}
    	
    	if(world.getHigh()){
    		game.extInt.submitScore((long) (world.getThisScore() * 100));
    	}
    	
    	if(world.getMaxY() >= 520){
    		game.extInt.unlockAchievement("CgkI1Yuo44kEEAIQCA");
    	}
    	
    	if(world.getTotalDist() > 10000){
    		game.extInt.unlockAchievement("CgkI1Yuo44kEEAIQCQ");
    	}
    	
    	if(world.getTotalFall() == 100){
    		game.extInt.unlockAchievement("CgkI1Yuo44kEEAIQCg");
    	}
    	
    }
    

}
