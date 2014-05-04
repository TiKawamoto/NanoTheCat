package com.lunarcannon.NanoCat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.leaderboard.Leaderboards.LoadPlayerScoreResult;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

public class AndroidLauncher extends AndroidApplication implements ExternalInterface{
	
	GameHelper gHelper;
	Preferences pref;

	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.hideStatusBar = true;
		config.useImmersiveMode = true;
		initialize(new NanoCat(this), config);
		
		gHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		
		GameHelperListener listener = new GameHelper.GameHelperListener() {
	        @Override
	        public void onSignInSucceeded() {
	        
	        }
	        @Override
	        public void onSignInFailed() {
	  
	        }

	    };
	    
	    gHelper.setup(listener);
		
	    pref = Gdx.app.getPreferences("androidprefs");
				
	}
	
	private final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
              if(msg.arg1 == 1)
                    Toast.makeText(getApplicationContext(),getString(R.string.logout), Toast.LENGTH_LONG).show();
        }
    };
	
	@Override
	protected void onStart() {
	    super.onStart();
	    
	    if(pref.getBoolean("gpgs")){
	    	gHelper.onStart(this);
	    	Toast.makeText(this, getString(R.string.login), Toast.LENGTH_LONG).show();
	    }	    		    
	}
		

	@Override
	protected void onStop() {
	    super.onStop();
	    gHelper.onStop();
	}
	
	@Override
	protected void onActivityResult(int request, int response, Intent data) {
	    super.onActivityResult(request, response, data);
	    gHelper.onActivityResult(request, response, data);
	    
	}
	
	
	
	@Override
	public void login() {
		gHelper.beginUserInitiatedSignIn();
		pref.putBoolean("gpgs", true);
		pref.flush();		
	}
	

	
	@Override
	public void logout() {
		
		gHelper.signOut();
		gHelper.disconnect();		
		pref.putBoolean("gpgs", false);
		pref.flush();
		Message msg = handler.obtainMessage();
		msg.arg1 = 1;
		handler.sendMessage(msg);
		
	}

	@Override
	public boolean getSignedIn() {
		return gHelper.isSignedIn();
	}

	@Override
	public void submitScore(long highScore) {
		Games.Leaderboards.submitScore(gHelper.getApiClient(), "CgkI1Yuo44kEEAIQBg", highScore);
		
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
		startActivityForResult(Games.Achievements.getAchievementsIntent(gHelper.getApiClient()), 1);
		
		
	}

	@Override
	public void unlockAchievement(String achievementID) {
		Games.Achievements.unlock(gHelper.getApiClient(), achievementID);
		
	}

	@Override
	public void getLeaderboard() {
		startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gHelper.getApiClient(), "CgkI1Yuo44kEEAIQBg"), 1);
		
		
	}


}