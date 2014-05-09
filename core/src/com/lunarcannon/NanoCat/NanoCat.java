package com.lunarcannon.NanoCat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Preferences;
import com.lunarcannon.GameContainers.AssetLibrary;
import com.lunarcannon.GameContainers.GameStateHandler;
import com.lunarcannon.Screens.MenuScreen;

public class NanoCat extends Game {
			
	public ExternalInterface extInt;
	private int timeOfDay;
	
	public NanoCat(ExternalInterface extInt){
		this.extInt = extInt;
	}
	
	@Override
	public void create() {		
		
		
		//GET TIME
		DateFormat dateFormatter = new SimpleDateFormat("HH");
		Calendar cal = Calendar.getInstance();
		timeOfDay = Integer.parseInt(dateFormatter.format(cal.getTime()));
		System.out.println(timeOfDay);
		
		AssetLibrary.load(this);
		GameStateHandler.load(this);
		
		setScreen(new MenuScreen(this));
		
//		setScreen(new GameScreen());
	}
	
	@Override
	public void dispose(){
		super.dispose();
		AssetLibrary.dispose();
	}
	
	public int getTime(){
		return timeOfDay;
	}
	
	public void adRefresh(boolean ad){
		extInt.showAds(ad);
	}

}
