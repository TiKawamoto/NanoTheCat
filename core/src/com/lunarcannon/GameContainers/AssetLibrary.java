package com.lunarcannon.GameContainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lunarcannon.NanoCat.NanoCat;

public class AssetLibrary {
	
	public static NanoCat nano;
	public static Texture bgTexture, bgBackTexture;
	public static Texture texture;
	public static TextureRegion bg, bgBack;
	public static Texture fontFilter;
	public static Texture panel;
	public static Sprite panelSprite;
	
	public static AtlasRegion catRun1, catRun2, catRun3, catRun4, catRun5, catRun6, catRun7, catRun8, catRun9, catRun10, catRun11, catRun12, catRun13, catRun14, catRun15;
	public static AtlasRegion catCollide1, catCollide2, catCollide3, catCollide4, catCollide5, catCollide6, catCollide7, catCollide8;
	public static AtlasRegion case0, case1, case2, case3, case4, case5, case6, caseDefault;
	public static TextureAtlas nanoRun;
	public static TextureAtlas platform;
	
	public static Animation catJumpAnim;
	public static Animation catRunAnim;
	public static Animation catCollideAnim;
	
	public static BitmapFont robotoLt;
	
	public static Preferences score;
	
	public static Sound collision;
	
	private static float distTemp = 0;
	private static int fallTemp = 0;
	
	public static void load(NanoCat nano){
			
		if(nano.getTime() < 15 && nano.getTime() >= 5){				
			bgTexture = new Texture(Gdx.files.internal("data/bg_1.png"));
			bgTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			
			bgBackTexture = new Texture(Gdx.files.internal("data/bg_dist_1.png"));
			bgBackTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		} else if (nano.getTime() < 20 && nano.getTime() >= 15){
			bgTexture = new Texture(Gdx.files.internal("data/bg_2.png"));
			bgTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);	
			
			bgBackTexture = new Texture(Gdx.files.internal("data/bg_dist_2.png"));
			bgBackTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);	
		} else if (nano.getTime() >= 20){
			bgTexture = new Texture(Gdx.files.internal("data/bg_3.png"));
			bgTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			
			bgBackTexture = new Texture(Gdx.files.internal("data/bg_dist_3.png"));
			bgBackTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		} else {
			bgTexture = new Texture(Gdx.files.internal("data/bg_3.png"));
			bgTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			
			bgBackTexture = new Texture(Gdx.files.internal("data/bg_dist_3.png"));
			bgBackTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		}			
		
		
		bgBack = new TextureRegion(bgBackTexture, 0, 0, 4096, 580);
		
		texture = new Texture(Gdx.files.internal("data/nano_region.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		nanoRun = new TextureAtlas(Gdx.files.internal("data/nanorun.atlas"));
		platform = new TextureAtlas(Gdx.files.internal("data/platform.atlas"));
		
		catRun1 = nanoRun.findRegion("nanorun0001");
		catRun2 = nanoRun.findRegion("nanorun0005");
		catRun3 = nanoRun.findRegion("nanorun0009");
		catRun4 = nanoRun.findRegion("nanorun0013");
		catRun5 = nanoRun.findRegion("nanorun0017");
		catRun6 = nanoRun.findRegion("nanorun0021");
		catRun7 = nanoRun.findRegion("nanorun0025");
		catRun8 = nanoRun.findRegion("nanorun0029");
		catRun9 = nanoRun.findRegion("nanorun0033");
		catRun10 = nanoRun.findRegion("nanorun0037");
		catRun11 = nanoRun.findRegion("nanorun0041");
		catRun12 = nanoRun.findRegion("nanorun0045");
		catRun13 = nanoRun.findRegion("nanorun0049");
		catRun14 = nanoRun.findRegion("nanorun0053");
		catRun15 = nanoRun.findRegion("nanorun0057");
		
		catCollide1 = nanoRun.findRegion("nanocollide0080");
		catCollide2 = nanoRun.findRegion("nanocollide0084");
		catCollide3 = nanoRun.findRegion("nanocollide0088");
		catCollide4 = nanoRun.findRegion("nanocollide0092");
		catCollide5 = nanoRun.findRegion("nanocollide0096");
		catCollide6 = nanoRun.findRegion("nanocollide0100");
		catCollide7 = nanoRun.findRegion("nanocollide0104");
		catCollide8 = nanoRun.findRegion("nanocollide0108");
		
		case0 = platform.findRegion("case0");
		case1 = platform.findRegion("case1");
		case2 = platform.findRegion("case2");
		case3 = platform.findRegion("case3");
		case4 = platform.findRegion("case4");
		case5 = platform.findRegion("case5");
		case6 = platform.findRegion("case6");
		caseDefault = platform.findRegion("default");

		
		
		TextureRegion[] catRun = {catRun1, catRun2, catRun3, catRun4, catRun5, catRun6, catRun7, catRun8, catRun9, catRun10, catRun11, catRun12, catRun13, catRun14, catRun15};
		catRunAnim = new Animation(.015f, catRun);
		catRunAnim.setPlayMode(Animation.PlayMode.LOOP);
		
		TextureRegion[] catJump = {catRun1, catRun2, catRun3, catRun4, catRun5, catRun6, catRun7, catRun8, catRun9, catRun9, catRun9, catRun9, catRun9, catRun9, catRun9, catRun9};
		catJumpAnim = new Animation(.04f, catJump);
		catJumpAnim.setPlayMode(Animation.PlayMode.LOOP);
		
		TextureRegion[] catCollide = {catCollide1, catCollide2, catCollide3, catCollide4, catCollide5, catCollide6, catCollide7, catCollide8};
		catCollideAnim = new Animation(.02f, catCollide);
		catCollideAnim.setPlayMode(Animation.PlayMode.NORMAL);
		
		fontFilter = new Texture(Gdx.files.internal("data/roboto_lt.png"));
		fontFilter.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		robotoLt = new BitmapFont(Gdx.files.internal("data/roboto_lt.fnt"), new TextureRegion(fontFilter), false);
		robotoLt.setScale(.5f, .5f);
		
		panel = new Texture(Gdx.files.internal("data/panelcolor.png"));
		panel.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		panelSprite = new Sprite(panel, 500, 300);
		
		
		collision = Gdx.audio.newSound(Gdx.files.internal("data/audio/collide.wav"));
		
		
		//Scoring
		score = Gdx.app.getPreferences("NanoCat");
		
		if(!score.contains("distance")){
			score.putFloat("distance", 0);
		}
		
		if(!score.contains("highScore")){
			score.putFloat("highScore", 0);
		}
		
		if(!score.contains("totalFall")){
			score.putInteger("totalFall", 0);			
		}
	
	}
	
	public static void setDistance(float dist){		
		distTemp = score.getFloat("distance");
		distTemp = distTemp + dist;
		
		score.putFloat("distance", distTemp);
		score.flush();
	}
	
	public static void setTotalFall(){
		fallTemp = score.getInteger("totalFall");
		fallTemp++;
		score.putInteger("totalFall", fallTemp);
		
	}
	
	public static int getTotalFall(){
		return score.getInteger("totalFall");
	}
	
	public static float getDistance(){
		return score.getFloat("distance");
	}
	
	public static void setHighScore(float val){
		score.putFloat("highScore", val);
		score.flush();
	}
	
	public static float getHighScore(){
		return score.getFloat("highScore");
	}
		
		
		
	
	
	public static void dispose(){
		texture.dispose();
		bgTexture.dispose();
		robotoLt.dispose();
		bgBackTexture.dispose();
		panel.dispose();
		fontFilter.dispose();
		collision.dispose();
		
	}

}
