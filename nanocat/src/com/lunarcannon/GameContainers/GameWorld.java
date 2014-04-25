package com.lunarcannon.GameContainers;

import java.util.ArrayList;
import java.util.Iterator;

import GameObjects.Cat;
import GameObjects.Platform;
import GameObjects.ScrollingHandler;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class GameWorld {


	private Cat cat;
	private ScrollingHandler scrollHandler;
	private Platform p;
	private ArrayList<Platform> platform = new ArrayList<Platform>();
	private Iterator<Platform> pIter;
	private Rectangle catBounds, platBounds;
	

	public GameWorld() {
		// this.game = game;

		cat = new Cat(200, 102);
		scrollHandler = new ScrollingHandler();
		this.platform = scrollHandler.getPlatform();

	}

	public void update(float delta) {
		cat.update(delta);
		scrollHandler.update(delta);
		
		//BEGIN PLATFORM COLLISION CHECK
		for (int i = 0; i < platform.size(); i++) {
			
			p = platform.get(i);
			catBounds = new Rectangle(cat.getPosition().x, cat.getPosition().y, cat.getWidth(), cat.getHeight());
			platBounds = new Rectangle(p.getxPos(), p.getyPos(), p.getWidth(), p.getHeight());			
					
			//CHECK IF ON TOP OF PLATFORM			
			if((catBounds.width + catBounds.x > platBounds.x) && (catBounds.x < platBounds.x + platBounds.width - 10f) && (catBounds.y <= platBounds.height)){
				
					cat.setY((float) (platBounds.height)); 
					cat.landed();
			}
			
			//CHECK FOR SIDE COLLISION			
			if((catBounds.width + catBounds.x <= platBounds.x) && (catBounds.width + catBounds.x > platBounds.x -12f) && (catBounds.y < platBounds.height)){	//SIDE COLLISIONS				
					
					scrollHandler.stop();
					cat.collide();
					cat.setX(platBounds.x - cat.getWidth());					
					
					if(catBounds.y < -300){
						cat.stop();						
					}

			}//END SIDE COLLISION LOGIC		
		}//END PLATFORM ITERATION FOR LOOP
		
	}//END UPDATE

	public Cat getCat() {
		return cat;
	}

	public ScrollingHandler getScroller() {
		return scrollHandler;
	}

}
