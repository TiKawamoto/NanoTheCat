package com.lunarcannon.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ScrollEntities {
	
	private float xSpeed;
	private float xPos;
	private float yPos;
	private Vector2 position;
	private Vector2 velocity;
	private Rectangle bounds;
	private int width;
	private int height;
	private boolean outOfBounds = false;
		
	public ScrollEntities(float xPos, float yPos, float xSpeed, int width, int height) {
		this.velocity = new Vector2(xSpeed, 0);
		this.position = new Vector2(xPos, yPos);		
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		bounds = new Rectangle(position.x, position.y, width, height);
		
	}
	
	public void update(float delta){
		position.add(velocity.cpy().scl(delta));
		if(position.x + width < 0){
			outOfBounds = true;
		}
	}
	
	public void generate(float newPosX){
		outOfBounds = false;
		position.x = newPosX;
	}	
	
	public Vector2 getPosition(){
		return position;
	}
	
	public Rectangle getBounds(){
		return bounds;
	}
	
	
	public boolean isOutOfBounds(){
		return outOfBounds;
	}

	public float getxSpeed() {
		return xSpeed;
	}

	public float getxPos() {
		return position.x;
	}

	public float getyPos() {
		return position.y;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}	
	
	public void setVelocity(Vector2 velocity){
		this.velocity = velocity;
	}
	
	public void setxPos(float xSet){
		position.x = xSet;
	}
	
	public void setyPos(float ySet){
		position.y = ySet;
	}
	
	public void setBounds(Rectangle bounds){
		this.bounds = bounds;
	}

}
