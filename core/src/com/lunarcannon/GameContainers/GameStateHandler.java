package com.lunarcannon.GameContainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector3;
import com.lunarcannon.NanoCat.NanoCat;

public class GameStateHandler {
	private static boolean adState;
	private static Preferences pref;
	private static Vector3 touchPos;
	
	public static void load(NanoCat game){
		pref = Gdx.app.getPreferences("NanoGameState");
		
		if(!pref.contains("adState")){
			pref.putBoolean("adState", false);
			pref.flush();
		}
		
		if(!pref.contains("hdState")){
			pref.putBoolean("hdState", true);
			pref.flush();
		}
	}
	
	public static boolean getAdState(){
		adState = pref.getBoolean("adState");
		return adState;
	}
	
	public static void setAdState(boolean state){
		adState = state;
		pref.putBoolean("adState", adState);
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
	
}
