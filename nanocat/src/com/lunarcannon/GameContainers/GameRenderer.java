package com.lunarcannon.GameContainers;



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
import com.badlogic.gdx.utils.Array;

public class GameRenderer {
	private GameWorld world;
	private OrthographicCamera cam;
	private ShapeRenderer shape1;
	
	private Platform platform;
	private ScrollingHandler scrollHandler;
	private Array<Platform> platformScroller = new Array<Platform>();
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
		platformIterator = platformScroller.iterator();
		cat = world.getCat();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		shape1.setProjectionMatrix(cam.combined);
		shape1.begin(ShapeType.Rectangle);
		shape1.setColor(Color.RED);
		
		//Draw Platform
		while(platformIterator.hasNext()){
			platform = platformIterator.next();
			shape1.rect(platform.getxPos(), platform.getyPos(), platform.getWidth(), platform.getHeight());
			//System.out.println(platform.getxPos() + " + " + platform.getyPos() + " + " + platform.getWidth());
		}
		//Draw Cat
		shape1.rect(cat.getPosition().x, cat.getPosition().y, 200,100);
			
		shape1.end();
	}
	
	public void dispose(){
		shape1.dispose();
	}

}
