package com.lunarcannon.GameContainers;

import java.util.Iterator;

import GameObjects.Cat;
import GameObjects.Platform;
import GameObjects.ScrollingHandler;

import com.badlogic.gdx.utils.Array;

public class GameWorld {

	// private NanoCat game;

	private Cat cat;
	private ScrollingHandler scrollHandler;
	private Platform p;
	private Array<Platform> platform = new Array<Platform>();;
	private Iterator<Platform> pIter;

	public GameWorld() {
		// this.game = game;

		cat = new Cat(400, 400);
		scrollHandler = new ScrollingHandler();
		this.platform = scrollHandler.getPlatform();

	}

	public void update(float delta) {
		cat.update(delta);
		scrollHandler.update(delta);

		pIter = platform.iterator();

		while (pIter.hasNext()) {
			p = pIter.next();
					
			
			if (cat.getBounds().overlaps(p.getBounds())){
				System.out.println("COLLIDE!!" + "  " + cat.getPosition());
				cat.setY((float)(p.getyPos() + cat.getHeight()));
//				
				
			}
		}

	}

	public Cat getCat() {
		return cat;
	}

	public ScrollingHandler getScroller() {
		return scrollHandler;
	}

}
