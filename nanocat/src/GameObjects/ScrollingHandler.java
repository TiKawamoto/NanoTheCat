package GameObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ScrollingHandler {

	private Platform p, pTemp;
	private ArrayList<Platform> platform = new ArrayList<Platform>();
	private Iterator<Platform> pIter;
	private float xDefPos, yDefPos, xSpeed, newX, platLeftEdgeX;
	private int platTotalWidth = 0;
	private int platCheckWidth = 0;
	private int gapWidth = 0;
	private Random gapRand;
	private Rectangle bounds;
	private Vector2 velocity;
	private boolean stopped = false;

	public ScrollingHandler() {
		xDefPos = 0;
		yDefPos = 0;
		xSpeed = -350;
		velocity = new Vector2(0,0);
		bounds = new Rectangle(0, 0, 0, 0);

	}

	public void update(float delta) {

		// STARTING PLATFORM
		
		if(platform.size() == 0){
			Platform p = new Platform(xDefPos, yDefPos, xSpeed, 800, 100, 1);
			platform.add(p);
			bounds.set(platform.get(0).getxPos(), platform.get(0).getyPos(), platform.get(0).getWidth(), platform.get(0).getHeight());
			platform.get(0).setBounds(bounds);
			
		}

		if(!stopped){	
			
			for (int i = 0; i < platform.size(); i++) {
					
				//Find edge of platform
				platCheckWidth = (int) platform.get(i).getxPos() + platform.get(i).getWidth();
				platLeftEdgeX = (float) platCheckWidth;
				
				//remove platform from index if off screen
				if (((int) platform.get(i).getxPos() + platform.get(i).getWidth()) < -Gdx.graphics.getWidth()) {
					platform.remove(i);
				}			
				
				//Move Platform
				velocity.set(xSpeed, 0);
				platform.get(i).setVelocity(velocity);
				platform.get(i).update(delta);
				
				//Set Bounds
				bounds.set((float)platform.get(i).getxPos(),(float)platform.get(i).getyPos(),(float)platform.get(i).getWidth(), (float)platform.get(i).getHeight());
				platform.get(i).setBounds(bounds);
				
				//Get how much of last platform is on screen
				if (platform.size() == i + 1) {
					platTotalWidth = (int) platform.get(i).getxPos() + platform.get(i).getWidth();
				}
				
				//Checks if last platform is fully on screen and then adds new one
				if (platTotalWidth != 0 && platTotalWidth < (int) Gdx.graphics.getWidth()) {			
					Platform p = new Platform(platLeftEdgeX + 300, 0, xSpeed, 800, 100, 1);
					
					platform.add(p);				
					//platform.add(new Platform(platLeftEdgeX + 150, 0, xSpeed, 800, 100, 1));
					
					platTotalWidth = 0;
					
				}
				
			} // END PLATFORM FOR LOOP
		}//END STOPPED
	}

	public ArrayList<Platform> getPlatform() {
		return platform;

	}

	public void remove() {

	}
	
	public void stop(){
		System.out.println("stopped");
		stopped = true;
		
	}
}
