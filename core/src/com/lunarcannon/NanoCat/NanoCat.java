package com.lunarcannon.NanoCat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.badlogic.gdx.Game;
import com.lunarcannon.GameContainers.AssetLibrary;
import com.lunarcannon.Screens.MenuScreen;

public class NanoCat extends Game {
	
	private int timeOfDay;
	
	@Override
	public void create() {
		//GET TIME
		DateFormat dateFormatter = new SimpleDateFormat("HH");
		Calendar cal = Calendar.getInstance();
		timeOfDay = Integer.parseInt(dateFormatter.format(cal.getTime()));
		System.out.println(timeOfDay);
		
		AssetLibrary.load(this);
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

}
