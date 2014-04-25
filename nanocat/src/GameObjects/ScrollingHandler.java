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
	private int platType = 0;
	private int width = 0;
	private int height = 0;
	private Random gapRand;
	private Random platRand;
	private Rectangle bounds;
	private Vector2 velocity;
	private boolean stopped = false;

	public ScrollingHandler() {
		xDefPos = 0;
		yDefPos = 0;
		xSpeed = -570;
		velocity = new Vector2(0,0);
		bounds = new Rectangle(0, 0, 0, 0);
		gapRand = new Random();
		platRand = new Random();

	}

	public void update(float delta) {

		// STARTING PLATFORM
		
		if(platform.size() == 0){
			Platform p = new Platform(xDefPos, yDefPos, xSpeed, 800, 100, 0);
			platform.add(p);
			bounds.set(platform.get(0).getxPos(), platform.get(0).getyPos(), platform.get(0).getWidth(), platform.get(0).getHeight());
			platform.get(0).setBounds(bounds);
			
		}

		if(!stopped){	
			
			for (int i = 0; i < platform.size(); i++) {
				
				//Generate Random Gap Width
				gapWidth = (gapRand.nextInt(320 - 150) + 150);
				
				//Generate Random Platform Type
				platType = (platRand.nextInt(6-1) + 1);
					
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
				
					//pTemp.setPlat(platType);
					platLogic(platType);
					Platform p = new Platform(platLeftEdgeX + gapWidth, 0, xSpeed, width, height, platType);
					
					platform.add(p);									
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
		stopped = true;
		
	}

	public void platLogic(int platType){
		switch(platType){
		case(0):
			this.height = 100;
			this.width = 800;
			break;
		case(1):
			this.height = 220;
			this.width = 150;
			break;
		case(2):
			this.height = 120;
			this.width = 80;
			break;
		case(3):
			this.height = 120;
			this.width = 200;
			break;
		case(4):
			this.height = 130;
			this.width = 120;
			break;
		case(5):
			this.height = 150;
			this.width = 150;
			break;
		case(6):
			this.height = 120;
			this.width = 300;
			break;
		default:
			this.height = 100;
			this.width = 250;
			break;
		}
		
	}
}
