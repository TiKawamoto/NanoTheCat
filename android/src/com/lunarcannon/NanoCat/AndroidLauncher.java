package com.lunarcannon.NanoCat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.Window;
import android.view.WindowManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;



public class AndroidLauncher extends AndroidApplication implements ExternalInterface{
	
	GameHelper gHelper;
	Preferences pref;
	private AdView adView;
	private static final String AD_ID = "ca-app-pub-9782017076126208/2849197083";
	private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;
    
    protected Handler adHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW_ADS:
                {
                    adView.setVisibility(View.VISIBLE);
                    break;
                }
                case HIDE_ADS:
                {
                    adView.setVisibility(View.GONE);
                    System.out.println("GONE");
                    break;
                    
                }
            }
        }
    };
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout layout = new RelativeLayout(this);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.hideStatusBar = true;
		config.useImmersiveMode = true;
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        
		
		View gameView = initializeForView(new NanoCat(this), config);
		
		 // Admob Setup
		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(AD_ID);		
        
//		AdRequest adRequest = new AdRequest.Builder()
//			//.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//			.addTestDevice("9E455A49BC75589448AD9D9EA73FF4EB")
//			.build();
        adView.loadAd(new AdRequest.Builder().build());

        //LibGDX
        layout.addView(gameView);

   	 //AdMob
        RelativeLayout.LayoutParams adParams = 
            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layout.addView(adView, adParams);
        
        
        
        setContentView(layout);
		//initialize(new NanoCat(this), config);
		
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
            if(msg.arg1 == 2)
            	 	Toast.makeText(getApplicationContext(),getString(R.string.login), Toast.LENGTH_LONG).show();
        }	
        	
        
    };
	
	@Override
	protected void onStart() {
	    super.onStart();
	    
	    if(!pref.contains("firstTime")){
	    	pref.putBoolean("firstTime", true);
	    	pref.putBoolean("gpgs", true);
	    	pref.flush();
	    }	    
	 	    
	    if(pref.getBoolean("gpgs")){
	    	gHelper.onStart(this);
	    	Message msg = handler.obtainMessage();
			msg.arg1 = 2;
			handler.sendMessage(msg);
//	    	Toast.makeText(this, getString(R.string.login), Toast.LENGTH_LONG).show();
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
	
	@Override
	public void showAds(boolean show) {
	       adHandler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	    }

}