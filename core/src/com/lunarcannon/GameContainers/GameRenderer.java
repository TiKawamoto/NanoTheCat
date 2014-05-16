package com.lunarcannon.GameContainers;



import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.lunarcannon.GameObjects.Background;
import com.lunarcannon.GameObjects.Cat;
import com.lunarcannon.GameObjects.Platform;
import com.lunarcannon.GameObjects.ScrollingHandler;

public class GameRenderer {
	private GameWorld world;
	private OrthographicCamera cam;
	private ShapeRenderer shape1;
	private SpriteBatch spriteBatch;
	private float elapsedTime, elapsedTime2;
	private float frameNum;
	private String highScore, thisScore;
	private String highScoreText = "high score!";
	private String yourScoreText = "score";
	private TextBounds totalDistWidth;
	
	private Platform platform;
	private ScrollingHandler scrollHandler;
	private Background bgScroller1, bgScroller2, bgScrollerBack1, bgScrollerBack2;
	private ArrayList<Platform> platformScroller = new ArrayList<Platform>();
	private Iterator<Platform> platformIterator;
	private Cat cat;
	private int doOnce = 0;
	private int doOnce2 = 0;
	private int doOnce3 = 0;
	private int doOnce4 = 0;
	private int doOnce5 = 0;
	private int doOnce6 = 0;
	private long milestone = 0;
	private boolean timerBool = false;
	
	private Timer timer;
	
	private Value xVal = new Value();
	private Value yVal = new Value();
	private Value alphaVal = new Value();
	private Value scaleVal = new Value();
	
	private TweenManager tweenManager;
	
	private Vector3 touchPos;
	
	private NumberFormat f = NumberFormat.getInstance(Locale.ENGLISH);
	
	private float width, height, aspectRatio, actualWidth, catY, catX;

	public GameRenderer(GameWorld world) {		
		this.scrollHandler = world.getScroller();
		this.world = world;
		
		platformScroller = scrollHandler.getPlatform();
		bgScroller1 = scrollHandler.getBg1();
		bgScroller2 = scrollHandler.getBg2();
		bgScrollerBack1 = scrollHandler.getBgBack1();
		bgScrollerBack2 = scrollHandler.getBgBack2();
				
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		aspectRatio = height / width;
		
		actualWidth = 540 / aspectRatio;
		world.setActualWidth(actualWidth);
		
		//System.out.println(width + " " +  height + " " + aspectRatio + " " + actualWidth);
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 540 / aspectRatio, 540);
		cam.update();
		
		shape1 = new ShapeRenderer();
		shape1.setProjectionMatrix(cam.combined);
		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(cam.combined);
		
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);
		
		xVal.setValue(actualWidth + 800);
		
		
		
	}
	
	public void render(float delta){
		touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		GameStateHandler.setTouchPos(cam.unproject(touchPos));
		
		
		cat = world.getCat();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        //DRAW BACKGROUND  ----------------------------------------------------
        spriteBatch.begin();
        spriteBatch.enableBlending();
        spriteBatch.draw(AssetLibrary.bgBackTexture, bgScrollerBack1.getxPos(), bgScrollerBack1.getyPos(), bgScrollerBack1.getWidth(), bgScrollerBack1.getHeight());
        spriteBatch.draw(AssetLibrary.bgBackTexture, bgScrollerBack2.getxPos(), bgScrollerBack2.getyPos(), bgScrollerBack2.getWidth(), bgScrollerBack2.getHeight());
        spriteBatch.draw(AssetLibrary.bgTexture, bgScroller1.getxPos(), bgScroller1.getyPos(), bgScroller1.getWidth(), bgScroller1.getHeight());
        spriteBatch.draw(AssetLibrary.bgTexture, bgScroller2.getxPos(), bgScroller2.getyPos(), bgScroller2.getWidth(), bgScroller2.getHeight());
        spriteBatch.end();
		
        spriteBatch.begin();
		spriteBatch.enableBlending();
		
		for(int i = 0; i < platformScroller.size(); i++){
			
			if(platformScroller.get(i).getType() == 1){
				spriteBatch.draw(AssetLibrary.case1, platformScroller.get(i).getxPos(), platformScroller.get(i).getyPos(), 210, 350);
			} else if(platformScroller.get(i).getType() == 5){
				spriteBatch.draw(AssetLibrary.case5, platformScroller.get(i).getxPos(), platformScroller.get(i).getyPos(), 400, 300);
			} else if(platformScroller.get(i).getType() == 6){
				spriteBatch.draw(AssetLibrary.case6, platformScroller.get(i).getxPos(), platformScroller.get(i).getyPos(), 220, 220);
			}
			
		}
		
		spriteBatch.end();
				
		//DRAW CAT  ----------------------------------------------------
		spriteBatch.begin();
		spriteBatch.enableBlending();
		elapsedTime += Gdx.graphics.getDeltaTime();
		
		if(!world.getCollide()){			
			if(!world.getMidAirTrigger()){
				spriteBatch.draw(AssetLibrary.catRunAnim.getKeyFrame(elapsedTime, true), cat.getPosition().x - 95f, cat.getPosition().y - 13f, 250, 100);
				if(doOnce6 < 1){
					
					AssetLibrary.run.loop(.7f);
					doOnce6++;
				}
				
				doOnce2 = 0;
				doOnce3 = 0;
				doOnce5 = 0;
				
			
				
			} else if (world.getMidAirTrigger()){	
					doOnce6 = 0;
					AssetLibrary.run.stop();
					spriteBatch.draw(AssetLibrary.catJumpAnim.getKeyFrame(elapsedTime, false), cat.getPosition().x - 95f, cat.getPosition().y - 45f, 100f, 40f,250, 100,1,1,10f);
					

			} else if (cat.getDblJumpTrigger()){
				doOnce6 = 0;
				AssetLibrary.run.stop();
				spriteBatch.draw(AssetLibrary.catJumpAnim.getKeyFrame(.9f, false), cat.getPosition().x - 95f, cat.getPosition().y - 45f, 250, 100);
			}
		} else if(world.getCollide()){
			doOnce6 = 0;
			AssetLibrary.run.stop();
			elapsedTime2 += Gdx.graphics.getDeltaTime();
			spriteBatch.draw(AssetLibrary.catCollideAnim.getKeyFrame(elapsedTime2, false), cat.getPosition().x - 135f, cat.getPosition().y - 13f, 250, 125);
			
			
		}
		
		if(cat.getDblJump()){
			if(doOnce5 < 1){
				doOnce6 = 0;
				AssetLibrary.dblJump.play(.1f);	
				doOnce5++;
			}
			
		}
		
		//Jump smoke		
		if(cat.getJump()){
			if(doOnce2 < 1){
				doOnce6 = 0;
				AssetLibrary.run.stop();
				AssetLibrary.jump.play(.1f);
				AssetLibrary.jumpSound.play(1f);
				catY = cat.getPosition().y;
				catX = cat.getPosition().x + 55f;
			
				AssetLibrary.smokeParticle.start();	
				
				doOnce2++;
			}
								
			AssetLibrary.smokeParticle.findEmitter("smoke").getWind().setHigh(scrollHandler.getXSpeed());			
			AssetLibrary.smokeParticle.setPosition(catX, catY);
		}
		AssetLibrary.smokeParticle.draw(spriteBatch, delta);
		spriteBatch.end();

		
		
//		shape1.begin(ShapeType.Line);
//		shape1.setColor(Color.RED);
//		shape1.rect(cat.getPosition().x, cat.getPosition().y, cat.getWidth(), cat.getHeight());
//		shape1.end();			
		
		//Draw Platforms
//		shape1.begin(ShapeType.Line);		
//		for(int i = 0; i < platformScroller.size(); i++){
//					
//			platform = platformScroller.get(i);			
//			shape1.setColor(Color.ORANGE);
//			shape1.rect(platformScroller.get(i).getxPos(), platformScroller.get(i).getyPos(), platformScroller.get(i).getWidth(), platformScroller.get(i).getHeight());
//						
//		}
//		shape1.end();
		
		//DRAW PLAFORM ----------------------------------------------------
		spriteBatch.begin();
		spriteBatch.enableBlending();
		
		for(int i = 0; i < platformScroller.size(); i++){
			
			if(platformScroller.get(i).getType() == 0){
				spriteBatch.draw(AssetLibrary.case0, platformScroller.get(i).getxPos(), platformScroller.get(i).getyPos(), 800, 118);
			}	else if(platformScroller.get(i).getType() == 1){				
			} else if(platformScroller.get(i).getType() == 2){
				spriteBatch.draw(AssetLibrary.case2, platformScroller.get(i).getxPos(), platformScroller.get(i).getyPos(), 100, 120);
			} else if(platformScroller.get(i).getType() == 3){
				spriteBatch.draw(AssetLibrary.case3, platformScroller.get(i).getxPos(), platformScroller.get(i).getyPos(), 100, 180);
			} else if(platformScroller.get(i).getType() == 4){
				spriteBatch.draw(AssetLibrary.case4, platformScroller.get(i).getxPos(), platformScroller.get(i).getyPos(), 320, 180);
			} else if(platformScroller.get(i).getType() == 5){
				//spriteBatch.draw(AssetLibrary.case3, platformScroller.get(i).getxPos(), platformScroller.get(i).getyPos(), 100, 180);
			} else if(platformScroller.get(i).getType() == 6){
				//spriteBatch.draw(AssetLibrary.case3, platformScroller.get(i).getxPos(), platformScroller.get(i).getyPos(), 100, 180);
			}  else {
				spriteBatch.draw(AssetLibrary.caseDefault, platformScroller.get(i).getxPos(), platformScroller.get(i).getyPos(), 100, 250);
			}
			
		}
		
		
		//DRAW SCORE TEXT  ----------------------------------------------------
		
		if(!world.getCollide()){
			doOnce = 0;
			if(GameStateHandler.getAdState()){
				AssetLibrary.robotoLt.setScale(.5f);
				AssetLibrary.robotoLt.setColor(1,1,1,1);
				AssetLibrary.robotoLt.draw(spriteBatch, "total distance", actualWidth - 272, 420);
				totalDistWidth = AssetLibrary.robotoLt.getBounds(f.format(scrollHandler.getTotalDist()));
				AssetLibrary.robotoLt.draw(spriteBatch, f.format(scrollHandler.getTotalDist()) + "m", (actualWidth - 55) - totalDistWidth.width, 380);	
			} else {
				AssetLibrary.robotoLt.setScale(.5f);
				AssetLibrary.robotoLt.setColor(1,1,1,1);
				AssetLibrary.robotoLt.draw(spriteBatch, "total distance", actualWidth - 272, 520);
				totalDistWidth = AssetLibrary.robotoLt.getBounds(f.format(scrollHandler.getTotalDist()));
				AssetLibrary.robotoLt.draw(spriteBatch, f.format(scrollHandler.getTotalDist()) + "m", (actualWidth - 55) - totalDistWidth.width, 480);			
			}
			xVal.setValue(actualWidth + 800);
			GameStateHandler.setAdState(false);
			
			//DRAW MILESTONE POPUP TEXT
			
			if((Math.round(scrollHandler.getTotalDist()) % 10) == 0 && !(Math.round(scrollHandler.getTotalDist()) == 0)){
				milestone = (Math.round(scrollHandler.getTotalDist()));
				System.out.println(milestone + " -- milestone!");
				drawMilestone(delta);
			}			
		}
		
		if(timerBool){
			if(doOnce4 < 1){
				yVal.setValue(400);
				alphaVal.setValue(0);
				scaleVal.setValue(0);
				Tween.registerAccessor(Value.class, new ValueAccessor());
				
				tweenManager = new TweenManager();
				Tween.to(yVal, -1, .4f).target(470).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(alphaVal, -1, .3f).target(1).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 1).start(tweenManager);
				Tween.to(scaleVal, -1, .5f).target(1).ease(TweenEquations.easeInOutExpo).start(tweenManager);
				AssetLibrary.starParticle.start();
				AssetLibrary.starParticle.setPosition((actualWidth / 2) , 450);
				if(milestone%50 == 0){
					AssetLibrary.milestone50.play(.3f);
				} else{
					AssetLibrary.milestone.play(.1f);	
				}
				
				doOnce4++;
			}
			tweenManager.update(delta);
			
			if(milestone % 50 == 0){
				AssetLibrary.starParticle.findEmitter("star").getScale().setHigh(10f);
				AssetLibrary.starParticle.findEmitter("star2").getScale().setHigh(10f);
				AssetLibrary.starParticle.findEmitter("star50").getTransparency().setHigh(1, 1);
				AssetLibrary.starParticle.findEmitter("star502").getTransparency().setHigh(1, 1);
				AssetLibrary.starParticle.findEmitter("star50").getScale().setHigh(10f);
				AssetLibrary.starParticle.findEmitter("star502").getScale().setHigh(10f);
				AssetLibrary.robotoLt.setColor(1f,.8f,.2f,alphaVal.getValue());
				AssetLibrary.robotoLt.setScale(scaleVal.getValue() + .3f);
			}else{
				
				AssetLibrary.starParticle.findEmitter("star").getScale().setHigh(5f);
				AssetLibrary.starParticle.findEmitter("star2").getScale().setHigh(5f);
				AssetLibrary.starParticle.findEmitter("star50").getTransparency().setHigh(0, 0);
				AssetLibrary.starParticle.findEmitter("star502").getTransparency().setHigh(0, 0);
				AssetLibrary.robotoLt.setColor(1,1,1,alphaVal.getValue());
				AssetLibrary.robotoLt.setScale(scaleVal.getValue());
			}
			AssetLibrary.starParticle.draw(spriteBatch, delta);			
			
			
			AssetLibrary.robotoLt.draw(spriteBatch, milestone + "m", (actualWidth / 2) - ((AssetLibrary.robotoLt.getBounds((milestone + "m"), new TextBounds()).width) / 2), yVal.getValue());	
			
		}
		
		//--------
		
		if(world.getCollide()){		
			timerBool = false;
			GameStateHandler.setAdState(true);
			
			if(doOnce < 2){
				AssetLibrary.run.stop();
				AssetLibrary.jump.stop();
				AssetLibrary.dblJump.stop();
				Tween.registerAccessor(Sprite.class, new SpriteAccessor());
				Tween.registerAccessor(BitmapFont.class, new BitmapFontAccessor());
				Tween.registerAccessor(Value.class, new ValueAccessor());
				tweenManager = new TweenManager();
				AssetLibrary.panelSprite.setAlpha(0);
				AssetLibrary.fbShareSprite.setAlpha(0);
				AssetLibrary.robotoLt.setColor(1, 1, 1, 0);
				Tween.to(xVal, -1, .2f).target((actualWidth / 2) - 250).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(AssetLibrary.panelSprite, SpriteAccessor.ALPHA, .3f).target(.85f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(AssetLibrary.fbShareSprite, SpriteAccessor.ALPHA, .3f).target(.85f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(AssetLibrary.robotoLt, BitmapFontAccessor.ALPHA, .3f).target(.85f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				doOnce++;
			}
			
			if(world.getHigh()){	
				
				tweenManager.update(delta);
				thisScore = Float.toString(world.getThisScore());
				
				AssetLibrary.panelSprite.setX(xVal.getValue());
				AssetLibrary.panelSprite.setY(125);
				
				AssetLibrary.panelSprite.draw(spriteBatch);				
				
				AssetLibrary.robotoLt.setScale(1f);
				AssetLibrary.robotoLt.draw(spriteBatch, "yay.",  xVal.getValue() + 20, 405);
				
				AssetLibrary.robotoLt.setScale(.6f);
				AssetLibrary.robotoLt.draw(spriteBatch, thisScore + "m",  xVal.getValue() + 20, 270);
				
				AssetLibrary.robotoLt.setScale(.3f);
				AssetLibrary.robotoLt.draw(spriteBatch, highScoreText,  xVal.getValue() + 20, 220);
				
				AssetLibrary.robotoLt.setScale(.3f);
				AssetLibrary.robotoLt.draw(spriteBatch, "tap to continue",  xVal.getValue() + 320, 155);				
				
				AssetLibrary.robotoLt.setScale(.5f);
				
				AssetLibrary.fbShareSprite.setX( xVal.getValue() + 300);
				AssetLibrary.fbShareSprite.setY(350);		
				AssetLibrary.fbShareSprite.setScale(.8f);
				AssetLibrary.fbShareSprite.draw(spriteBatch);

				
			} else {
				tweenManager.update(delta);
				
				highScore = Float.toString(world.getHighScore());
				thisScore = Float.toString(world.getThisScore());
				
				AssetLibrary.panelSprite.setX( xVal.getValue());
				AssetLibrary.panelSprite.setY(125);
				AssetLibrary.panelSprite.setAlpha(.85f);
				AssetLibrary.panelSprite.draw(spriteBatch);							
				
				AssetLibrary.robotoLt.setScale(1f);
				AssetLibrary.robotoLt.draw(spriteBatch, "oof.", xVal.getValue() + 20, 395);
				
				AssetLibrary.robotoLt.setScale(.6f);
				AssetLibrary.robotoLt.draw(spriteBatch, "score: " + thisScore + "m", xVal.getValue() + 20, 270);
				
				AssetLibrary.robotoLt.setScale(.3f);
				AssetLibrary.robotoLt.draw(spriteBatch, "high score: " + highScore + "m", xVal.getValue() + 20, 220);
				
				AssetLibrary.robotoLt.setScale(.3f);
				AssetLibrary.robotoLt.draw(spriteBatch, "tap to continue", xVal.getValue() + 320, 155);
				
				AssetLibrary.robotoLt.setScale(.5f);	
				
				AssetLibrary.fbShareSprite.setX(xVal.getValue() + 310);
				AssetLibrary.fbShareSprite.setY(350);		
				AssetLibrary.fbShareSprite.setScale(.8f);
				AssetLibrary.fbShareSprite.draw(spriteBatch);

			}					
		}		
		spriteBatch.end();
		
		
		
		//Reset elapsedTime on game reset
		if(world.getReset()){
			elapsedTime2 = 0;			
		}
		
	}
	
	public void drawMilestone(float delta){
		
		if(doOnce3 <1){					
			
			if(!timerBool){
				timerBool = true;
				timer = new Timer();			
				timer.scheduleTask(new Task(){

					@Override
					public void run() {
						timerBool = false;
						doOnce4 = 0;						
						
					}
					
				}, 2);
				
				doOnce3++;
				}		
				
				
			}
						
	
	}
		
		
	
		
	public void dispose(){
		shape1.dispose();
		spriteBatch.dispose();
		
	}
}
