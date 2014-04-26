package com.lunarcannon.GameContainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLibrary {

	public static Texture bgtexture;
	public static Texture texture;
	public static TextureRegion bg;
	public static Texture fontFilter;
	
	public static TextureRegion catRun1, catRun2, catRun3, catRun4, catRun5, catRun6, catRun7;
	
	public static Animation catJumpAnim;
	public static Animation catRunAnim;
	
	public static BitmapFont robotoLt;
	
	public static void load(){
		bgtexture = new Texture(Gdx.files.internal("data/bg_1.png"));
		bgtexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		bg = new TextureRegion(bgtexture, 0, 0, 1024, 289);
		
		texture = new Texture(Gdx.files.internal("data/nano_region.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		catRun1 = new TextureRegion(texture,0,0,200,80);
		catRun2 = new TextureRegion(texture,200,0,200,80);
		catRun3 = new TextureRegion(texture,400,0,200,80);
		catRun4 = new TextureRegion(texture,600,0,200,80);
		catRun5 = new TextureRegion(texture,800,0,200,80);
		catRun6 = new TextureRegion(texture,0,80,200,80);
		catRun7 = new TextureRegion(texture,200,80,200,80);
		
		TextureRegion[] catRun = {catRun1, catRun2, catRun3, catRun4, catRun5, catRun6, catRun7};
		catRunAnim = new Animation(.03f, catRun);
		catRunAnim.setPlayMode(Animation.PlayMode.LOOP);
		
		TextureRegion[] catJump = {catRun1, catRun2, catRun3, catRun4, catRun5, catRun5, catRun5, catRun5, catRun5};
		catJumpAnim = new Animation(.07f, catJump);
		catJumpAnim.setPlayMode(Animation.PlayMode.LOOP);
		
		fontFilter = new Texture(Gdx.files.internal("data/roboto_lt.png"));
		fontFilter.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		robotoLt = new BitmapFont(Gdx.files.internal("data/roboto_lt.fnt"), new TextureRegion(fontFilter), false);
		robotoLt.setScale(.5f, .5f);
		
		
		
		}
		
		
		
	
	
	public static void dispose(){
		texture.dispose();
		bgtexture.dispose();
		robotoLt.dispose();
	}

}
