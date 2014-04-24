package com.lunarcannon.GameContainers;

import java.util.ArrayList;
import java.util.Iterator;

import GameObjects.Cat;
import GameObjects.Platform;
import GameObjects.ScrollingHandler;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class GameWorld {

	// private NanoCat game;

	private Cat cat;
	private ScrollingHandler scrollHandler;
	private Platform p;
	private ArrayList<Platform> platform = new ArrayList<Platform>();
	private Iterator<Platform> pIter;
	private Rectangle catBounds, platBounds;
	

	public GameWorld() {
		// this.game = game;

		cat = new Cat(200, 100);
		scrollHandler = new ScrollingHandler();
		this.platform = scrollHandler.getPlatform();

	}

	public void update(float delta) {
		cat.update(delta);
		scrollHandler.update(delta);
		
		for (int i = 0; i < platform.size(); i++) {
			
			p = platform.get(i);
			catBounds = new Rectangle(cat.getPosition().x, cat.getPosition().y, cat.getWidth(), cat.getHeight());
			platBounds = new Rectangle(p.getxPos(), p.getyPos(), p.getWidth(), p.getHeight());
			
					
			//CHECK IF ON TOP OF PLATFORM
			
			if((catBounds.width + catBounds.x > platBounds.x) && (catBounds.x < platBounds.x + platBounds.width) && (catBounds.y <= platBounds.height)){
				
				if (Intersector.overlapRectangles(catBounds, platBounds)) {
					System.out.println("top collision");
					cat.setY((float) (platBounds.height)); 
					cat.landed();
				}
			}
			//System.out.println("CATBOUNDS Y: " + catBounds.y + " AND PLATHEIGHT: " + platBounds.height);
			System.out.println("CATBOUNDS X: " + (catBounds.width + catBounds.x) + " AND platX: " + platBounds.x);
			if((catBounds.width + catBounds.x <= platBounds.x) && (catBounds.width + catBounds.x > platBounds.x - 5) && (catBounds.y < platBounds.height)){	//SIDE COLLISIONS
					
					//if (Intersector.overlapRectangles(catBounds, platBounds)) {
						scrollHandler.stop();
						System.out.println("SIDE COLLISION");
						//cat.setX((float)(p.getxPos() - cat.getWidth())); 
						
					//}
				}
			
			
			
//			if (platBounds.x < catBounds.x + catBounds.width && platBounds.x + platBounds.width > catBounds.x && (catBounds.y + 1 >= platBounds.height)) {
//				if (Intersector.overlapRectangles(catBounds, platBounds)) {
//					//System.out.println("COLLIDE!!" + "  " + platBounds	+ "  " + catBounds);
//					cat.setY((float) (p.getyPos() + cat.getHeight())); //
//					cat.landed();
//				}
//			}
		}
		
	}

	public Cat getCat() {
		return cat;
	}

	public ScrollingHandler getScroller() {
		return scrollHandler;
	}

}
