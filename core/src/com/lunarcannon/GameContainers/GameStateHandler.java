package com.lunarcannon.GameContainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector3;
import com.lunarcannon.NanoCat.NanoCat;

public class GameStateHandler {
	
	private static NanoCat game;
	private static boolean adState;
	private static Preferences pref;
	private static Vector3 touchPos;
	private static float thescore = 0;
	
	public static void load(NanoCat thegame){
		game = thegame;
		
		pref = Gdx.app.getPreferences("NanoGameState");
		
		if(!pref.contains("adState")){
			pref.putBoolean("adState", true);
			pref.flush();
		}
		
		if(!pref.contains("hdState")){
			pref.putBoolean("hdState", true);
			pref.flush();
		}
		
		if(!pref.contains("muteState")){
			pref.putBoolean("muteState", false);
			pref.flush();
		}
	}
	
	public static boolean getAdState(){
//		adState = pref.getBoolean("adState");
		return adState;
	}
	
	public static void setAdState(boolean state){
		
		if(game.extInt.getPremium()){
			pref.putBoolean("adState", false);
			game.adRefresh(false);
		} else {
			adState = state;
			pref.putBoolean("adState", adState);
			game.adRefresh(adState);	
		}
				
	}
	
	public static void setTouchPos(Vector3 touch){
		touchPos = touch;
	}
	
	public static Vector3 getTouchPos(){
		return touchPos;
	}
	
	public static void setHD(boolean val){
		pref.putBoolean("hdState", val);
		pref.flush();
	}
	
	public static boolean getHD(){		
		return pref.getBoolean("hdState");
	}
	
	public static void setMute(boolean val){
		pref.putBoolean("muteState", val);
		pref.flush();
	}
	
	public static boolean getMute(){
		return pref.getBoolean("muteState");
	}
	
	
}
