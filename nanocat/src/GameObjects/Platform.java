package GameObjects;

import java.util.Random;

public class Platform extends ScrollEntities{
	
	private float scrollSpeed = 10;
	private int type;
	private Random rType;
	public Platform(float xPos, float yPos, float xSpeed, int width, int height, int type) {
		super(xPos, yPos, xSpeed, width, height);
		
		rType = new Random();
		xPos = 0;
				
	}
	
	public void update(float delta){
		
	}
	
	@Override
	public void generate(float newPosX){
		type = rType.nextInt(5);
		System.out.println(type);
	}
	

}
