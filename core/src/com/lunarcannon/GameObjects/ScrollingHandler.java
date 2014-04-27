package com.lunarcannon.GameObjects;

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
	private float xDefPos, yDefPos, xSpeed, bgXSpeed, bgBackXSpeed, newX, platLeftEdgeX, newXBg, totalDist, totalDistTemp;
	private int platTotalWidth = 0;
	private int platCheckWidth = 0;
	private int gapWidth = 0;
	private int platType = 0;
	private int width = 0;
	private int height = 0;
	private Random gapRand;
	private Random platRand;
	private Rectangle bounds;
	private Vector2 velocity, bgVelocity, bgBackVelocity;
	private float bgWidth, bgHeight, aspectRatio;
	private Background bg1, bg2, bgBack1, bgBack2;
	
	private boolean stopped = false;

	public ScrollingHandler() {
		totalDist = 0;
		xDefPos = 0;
		yDefPos = 0;
		xSpeed = -570;
		bgVelocity = new Vector2(0,0);
		bgBackVelocity = new Vector2(0,0);
		velocity = new Vector2(0,0);
		bounds = new Rectangle(0, 0, 0, 0);
		gapRand = new Random();
		platRand = new Random();
		
		bgWidth = (Gdx.graphics.getWidth());
		bgHeight = (Gdx.graphics.getHeight());
		aspectRatio = bgHeight / bgWidth;
		
		bg1 = new Background(0, 0, 0, (int)(578 / aspectRatio) * 4, (int)(578), 0);
		bg2 = new Background(960, 0, 0, (int)(578 / aspectRatio) * 4, (int)(578), 0);
		bgBack1 = new Background(0, 0, 0, (int)(578 / aspectRatio) * 4, (int)(578), 0);
		bgBack2 = new Background(960, 0, 0, (int)(578 / aspectRatio) * 4, (int)(578), 0);
	}

	public void update(float delta) {		
	
		if(!stopped){
			bgUpdate(delta);
			platformUpdate(delta);
		}

	}
	
	public void platformUpdate(float delta){
		// STARTING PLATFORM
		
		if(platform.size() == 0){
			Platform p = new Platform(xDefPos, yDefPos, xSpeed, 800, 100, 0);
			platform.add(p);
			bounds.set(platform.get(0).getxPos(), platform.get(0).getyPos(), platform.get(0).getWidth(), platform.get(0).getHeight());
			platform.get(0).setBounds(bounds);
			
		}

		
			
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
			
			//Detect Total Distance
			totalDist += .007f;
			
			
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
	
	
	public void bgUpdate(float delta){
		//Background Scroller
		if((int)bg1.getxPos() + bg1.getWidth() <= bg1.getWidth()){
			newXBg = (float)bg1.getxPos() + bg1.getWidth();
			bg2.setxPos(newXBg);
		} 
		
		if ((int)bg2.getxPos() + bg2.getWidth() <= bg2.getWidth()){
			newXBg = (float)bg2.getxPos() + bg2.getWidth();
			bg1.setxPos(newXBg);
		}
		
		bgXSpeed = xSpeed + 300f;
		bgVelocity.set(bgXSpeed, 0);
				
		bg1.setVelocity(bgVelocity);
		bg2.setVelocity(bgVelocity);
		bg1.update(delta);
		bg2.update(delta);
		
		//Background Background
		if((int)bgBack1.getxPos() + bgBack1.getWidth() <= bgBack1.getWidth()){
			newXBg = (float)bgBack1.getxPos() + bgBack1.getWidth();
			bgBack2.setxPos(newXBg);
		} 
		
		if ((int)bgBack2.getxPos() + bgBack2.getWidth() <= bgBack2.getWidth()){
			newXBg = (float)bgBack2.getxPos() + bgBack2.getWidth();
			bgBack1.setxPos(newXBg);
		}
		
		bgBackXSpeed = xSpeed + 500f;
		bgBackVelocity.set(bgBackXSpeed, 0);
				
		bgBack1.setVelocity(bgBackVelocity);
		bgBack2.setVelocity(bgBackVelocity);
		bgBack1.update(delta);
		bgBack2.update(delta);		
	}

	public ArrayList<Platform> getPlatform() {
		return platform;
	}
	
	public Background getBg1(){
		return bg1;
	}
	public Background getBg2(){
		return bg2;
	}
	public Background getBgBack1(){
		return bgBack1;
	}
	public Background getBgBack2(){
		return bgBack2;
	}
	
	public float getTotalDist(){
		return totalDist;
	}

	public void remove() {

	}
	
	public void stop(){
		stopped = true;
		
	}
	
	public boolean getStop(){
		return stopped;
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
			this.height = 160;
			this.width = 120;
			break;
		case(5):
			this.height = 150;
			this.width = 150;
			break;
		case(6):
			this.height = 120;
			this.width = 400;
			break;
		default:
			this.height = 100;
			this.width = 250;
			break;
		}
		
	}
	
	public void reset(){
		bg1.setxPos(0);
		bgBack1.setxPos(0);
		totalDist = 0;
		platform.clear();
		stopped = false;
	}
}
