package com.lunarcannon.GameObjects;

import java.util.Random;

public class Platform extends ScrollEntities{
	
	private float scrollSpeed = 10;
	private int type;
	private int width, height;
	
	public Platform(float xPos, float yPos, float xSpeed, int width, int height, int type) {
		super(xPos, yPos, xSpeed, width, height);
		
		xPos = 0;
				
	}
	
	//public void update(float delta){
		
	//}
	

	

}
