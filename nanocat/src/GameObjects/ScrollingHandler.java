package GameObjects;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;



public class ScrollingHandler {

	private Platform p, platformIter;
	private Array<Platform> platform = new Array<Platform>();
	private Iterator<Platform> pIter;
	private float xDefPos, yDefPos, xSpeed, newX, platLeftEdgeX;
	private int platTotalWidth = 0;
	private int platCheckWidth = 0;
	private int gapWidth = 0;
	private Random gapRand;
	private Rectangle bounds;
	
	public ScrollingHandler() {
		xDefPos = 0;
		yDefPos = 0;
		xSpeed = -5;
		bounds = new Rectangle(0,0,0,0);
		
		
	}
	
	public void update(float delta){
		
		//STARTING PLATFORM
		pIter = platform.iterator();
		
		if(!pIter.hasNext()){
			Platform p = new Platform(xDefPos, yDefPos, xSpeed, 400, 100, 1);
			platform.add(p);
			System.out.println("Platform Created");
		}
		
			
				
		while(pIter.hasNext()){
			platformIter = pIter.next();
			
			if(!pIter.hasNext()){
				platTotalWidth = (int)platformIter.getxPos() + platformIter.getWidth();
			}
			
			
			//System.out.println((int)platformIter.getxPos() + " + " + platformIter.getWidth());
			platCheckWidth = (int)platformIter.getxPos() + platformIter.getWidth();
			platLeftEdgeX = (float) platCheckWidth;
			
			
			if(((int)platformIter.getxPos() + platformIter.getWidth()) < 0){
				//System.out.println((int)platformIter.getxPos() + " + " + platformIter.getWidth());
				//System.out.println(((int)platformIter.getxPos() + platformIter.getWidth()));
				//pIter.remove();
				
			}
			
			if(platTotalWidth < (int)Gdx.graphics.getWidth()){
				//System.out.println(platTotalWidth + " + " + Gdx.graphics.getWidth() + "Platform");
				Platform p = new Platform(platLeftEdgeX + 30,0,xSpeed,800,100,1);
				platform.add(p);
				
			}
					
			newX = platformIter.getxPos() + xSpeed;
			platformIter.setxPos(newX);
			
			//platformIter.update(delta);
			//System.out.println(platformIter.getxSpeed());
			bounds.set(platformIter.getxPos(), platformIter.getyPos(), platformIter.getWidth(), platformIter.getHeight());
			platformIter.setBounds(bounds);			
			
		}
		
		
		
		
	}
	
	public Array<Platform> getPlatform(){
		return platform;
		
	}
	
public void remove(){
		
	}
}
