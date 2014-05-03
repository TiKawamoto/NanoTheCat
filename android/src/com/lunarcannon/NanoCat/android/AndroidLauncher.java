package com.lunarcannon.NanoCat.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.lunarcannon.NanoCat.ExternalInterface;
import com.lunarcannon.NanoCat.NanoCat;

public class AndroidLauncher extends AndroidApplication implements ExternalInterface {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.hideStatusBar = true;
		config.useImmersiveMode = true;
		initialize(new NanoCat(this), config);
		
	}

	@Override
	public void login() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getSignedIn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void submitScore(int highScore) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getScore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getScoreData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAchievement() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockAchievement(String achievementID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getLeaderboard() {
		// TODO Auto-generated method stub
		
	}
	
}