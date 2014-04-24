package com.lunarcannon.GameContainers;



import java.util.ArrayList;
import java.util.Iterator;

import GameObjects.Cat;
import GameObjects.Platform;
import GameObjects.ScrollingHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameRenderer {
	private GameWorld world;
	private OrthographicCamera cam;
	private ShapeRenderer shape1;
	
	private Platform platform;
	private ScrollingHandler scrollHandler;
	private ArrayList<Platform> platformScroller = new ArrayList<Platform>();
	private Iterator<Platform> platformIterator;
	private Cat cat;
	
	private float width, height, aspectRatio;

	public GameRenderer(GameWorld world) {
		this.scrollHandler = world.getScroller();
		this.world = world;
		
		platformScroller = scrollHandler.getPlatform();
				
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		aspectRatio = height / width;
		
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, width * aspectRatio);
		cam.update();
		
		shape1 = new ShapeRenderer();
		
	}
	
	public void render(float delta){
		
		cat = world.getCat();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		shape1.setProjectionMatrix(cam.combined);
		shape1.begin(ShapeType.Rectangle);
		shape1.setColor(Color.RED);
		
		
		for(int i = 0; i < platformScroller.size(); i++){
			
					
			platform = platformScroller.get(i);
			shape1.setColor(Color.GREEN);
			shape1.rect(platformScroller.get(i).getxPos(), platformScroller.get(i).getyPos(), platformScroller.get(i).getWidth(), platformScroller.get(i).getHeight());
			
		}
		//Draw Cat
		shape1.setColor(Color.RED);
		shape1.rect(cat.getPosition().x, cat.getPosition().y, cat.getWidth(),cat.getHeight());
		
		//shape1.setColor(Color.BLUE);
		//shape1.rect(cat.getBounds().x, cat.getBounds().y, cat.getBounds().width, cat.getBounds().height);
		
			
		shape1.end();
	}
	
	public void dispose(){
		shape1.dispose();
	}

}
