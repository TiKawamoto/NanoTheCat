package GameObjects;

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
		position.add(velocity.cpy().mul(delta));
		
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
		return xPos;
	}

	public float getyPos() {
		return yPos;
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
	
	public void setxPos(float xSet){
		this.xPos = xSet;
	}
	
	public void setyPos(float ySet){
		this.yPos = ySet;
	}
	
	public void setBounds(Rectangle bounds){
		this.bounds = bounds;
	}

}
