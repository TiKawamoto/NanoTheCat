package com.lunarcannon.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.lunarcannon.GameContainers.GameRenderer;
import com.lunarcannon.GameContainers.GameWorld;
import com.lunarcannon.GameContainers.InputHandler;
import com.lunarcannon.NanoCat.NanoCat;

public class GameScreen implements Screen{
	
	private GameWorld world;
	private GameRenderer renderer;
		
    public GameScreen(NanoCat game) {
        world = new GameWorld();
        renderer = new GameRenderer(world);
        
        Gdx.input.setInputProcessor(new InputHandler(world));
    }

    @Override
    public void render(float delta) {
    	world.update(delta);
    	renderer.render(delta);
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

}
