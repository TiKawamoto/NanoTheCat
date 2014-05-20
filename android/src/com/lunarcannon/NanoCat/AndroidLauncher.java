package com.lunarcannon.NanoCat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionState;
import com.facebook.widget.WebDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.lunarcannon.NanoCat.util.IabHelper;
import com.lunarcannon.NanoCat.util.IabResult;
import com.lunarcannon.NanoCat.util.Inventory;
import com.lunarcannon.NanoCat.util.Purchase;

public class AndroidLauncher extends AndroidApplication implements
		ExternalInterface {

	GameHelper gHelper;
	Preferences pref;
	
	private IabHelper mHelper;
	private AdView adView;
	private static final String AD_ID = "ca-app-pub-9782017076126208/2849197083";
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;
	private float theScore;
	
	private WebDialog dialog = null;
    private String dialogAction = null;
    private Bundle dialogParams = null;
    
    private boolean isPremium = false;
    
    
    
    private static final String TAG = "com.lunarcannon.NanoCat";
    static final String PREMIUM_SKU = "android.test.purchased";
    


	private Session.StatusCallback statusCallback = new SessionStatusCallback();

	// FB STUFF
	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			updateView();
		}
	}

	private void updateView() {
		Session session = Session.getActiveSession();
		if (session.isOpened()) {

		} else {

		}
	}

	// END FB STUFF

	// AD VISIBILITY STUFF
	protected Handler adHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_ADS: {
				adView.setVisibility(View.VISIBLE);
				break;
			}
			case HIDE_ADS: {
				adView.setVisibility(View.GONE);
//				System.out.println("GONE");
				break;

			}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout layout = new RelativeLayout(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.hideStatusBar = true;
		config.useImmersiveMode = true;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		View gameView = initializeForView(new NanoCat(this), config);
		//IAP Setup
			String base64EncodedPublicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkdMpTypThPFh0JQb09a/5xeEZl9d+AYefWPokMc5HURv5A6LUlFhybZspYcqCplY4N7zOQF/+WL8baE9+GA5laKimt3i4LzJ4ryXjNFKWe+iLCb/XIUxZpo8O+mPo+UW6kz+DrdWxoVu9/FS75/ujFWeAhZ8wCYSbn+3p0zk3FHBdNg3Bb3Hns1RQdCV1eLvkmtTOg1X/JQa0sOb0SaVW0uGyOwCLgyUFk66vUie9LGlDxSrHVPAG7c61urCVqT1Fk+iVE317mFdJ0/cLdolKSnhjvMHy/6a0LRssgW+oa/nRaEu3p3Cm4+DdDQOsNDqQd+0LGpEpcltJ63w2c4CAwIDAQAB";
			mHelper = new IabHelper(this, base64EncodedPublicKey);
			
			mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
		        	   	 public void onIabSetupFinished(IabResult result) 
					 {
		        	           if (!result.isSuccess()) {
		        	             Log.d(TAG, "In-app Billing setup failed: " + result);
		        	           } else {             
		        	      	     Log.d(TAG, "In-app Billing is set up OK");
		        	      	     mHelper.queryInventoryAsync(mGotInventoryListener);
				           }
		        	         }
		        	});
			
			
			 
		
		// Admob Setup
		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(AD_ID);

		// AdRequest adRequest = new AdRequest.Builder()
		// //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		// .addTestDevice("9E455A49BC75589448AD9D9EA73FF4EB")
		// .build();
		adView.loadAd(new AdRequest.Builder().build());

		// LibGDX
		layout.addView(gameView);

		// AdMob
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		layout.addView(adView, adParams);

		setContentView(layout);
		// initialize(new NanoCat(this), config);

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

		// FB Session
		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(this, null, statusCallback,
						savedInstanceState);
			}
			if (session == null) {
				session = new Session(this);
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this)
						.setCallback(statusCallback));
			}
		}

		//GET SIGNATURE
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					this.getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {

				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("====Hash Key===",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));

			}

		} catch (NameNotFoundException e) {

			e.printStackTrace();

		} catch (NoSuchAlgorithmException ex) {

			ex.printStackTrace();

		}

	}

	private final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.arg1 == 1)
				Toast.makeText(getApplicationContext(),
						getString(R.string.logout), Toast.LENGTH_LONG).show();
			if (msg.arg1 == 2)
				Toast.makeText(getApplicationContext(),
						getString(R.string.login), Toast.LENGTH_LONG).show();
			if (msg.arg1 == 3)
				Toast.makeText(getApplicationContext(),
						getString(R.string.fblogout), Toast.LENGTH_LONG).show();
			if (msg.arg1 == 4)
				Toast.makeText(getApplicationContext(),
						getString(R.string.fbposted), Toast.LENGTH_LONG).show();
		}

	};

	@Override
	protected void onStart() {
		super.onStart();

		if (!pref.contains("firstTime")) {
			pref.putBoolean("firstTime", true);
			pref.putBoolean("gpgs", true);
			pref.flush();
		}

		// GPGS Signin
		if (pref.getBoolean("gpgs")) {
			gHelper.onStart(this);
			// Message msg = handler.obtainMessage();
			// msg.arg1 = 2;
			// handler.sendMessage(msg);
			// Toast.makeText(this, getString(R.string.login),
			// Toast.LENGTH_LONG).show();
		}

		Session.getActiveSession().addCallback(statusCallback);
	}

	@Override
	protected void onStop() {
		super.onStop();
		gHelper.onStop();

		Session.getActiveSession().removeCallback(statusCallback);
	}

	@Override
	protected void onActivityResult(int request, int response, Intent data) {
		
		if (!mHelper.handleActivityResult(request, response, data)) {
			super.onActivityResult(request, response, data);
			System.out.println("REQUEST CODE: " + request);
	
			if (request == 64206) {
				Session.getActiveSession().onActivityResult(this, request, response, data);
			}else if (request == 103 || request == 102){
				Session.getActiveSession().onActivityResult(this, request, response, data);
			} else {
			gHelper.onActivityResult(request, response, data);
			} 
		}

	}
	
	// BEGIN IAP STUFF ----------------------------------------
	@Override
	public void buyPremium(){
		
		mHelper.launchPurchaseFlow(this, PREMIUM_SKU, 10001, mPurchaseFinishedListener, "mypurchasetoken");
		
			
		System.out.println("BUY INITIATED");
	}
	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
	   public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

	      if (result.isFailure()) {
	        // handle error here
	      }
	      else {
	        // does the user have the premium upgrade?
	    	  Purchase premiumPurchase = inventory.getPurchase(PREMIUM_SKU);
	    	  isPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
	            Log.d(TAG, "User is " + (isPremium ? "PREMIUM" : "NOT PREMIUM"));
	    	  
	    	  if(inventory.hasDetails(PREMIUM_SKU)){
	    		  mHelper.consumeAsync(inventory.getPurchase(PREMIUM_SKU), mConsumeFinishedListener);
	    	  }
	    	 
	        // update UI accordingly
	      }
	   }
	};
	
	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
			   new IabHelper.OnConsumeFinishedListener() {
			   public void onConsumeFinished(Purchase purchase, IabResult result) {
			      if (result.isSuccess()) {
			         // provision the in-app purchase to the user
			         // (for example, credit 50 gold coins to player's character)
			      }
			      else {
			         // handle error
			      }
			   }
			};
	
	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener 	= new IabHelper.OnIabPurchaseFinishedListener() {
	@Override
	public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			 if (result.isFailure()) {
				 Log.d(TAG, "PURCHASE FAILURE.");				
			     return;
			 }      
			 else if (purchase.getSku().equals(PREMIUM_SKU)) {
				 
				 isPremium = (purchase != null && verifyDeveloperPayload(purchase));
		         Log.d(TAG, "User is " + (isPremium ? "PREMIUM" : "NOT PREMIUM"));
				 Log.d(TAG, "Purchase is premium upgrade. Congratulating user.");	
				 
				 
			 }
	}
};

	public boolean getPremium(){
//		System.out.println(isPremium);
		return isPremium;
	}

	boolean verifyDeveloperPayload(Purchase p) {
	    String payload = p.getDeveloperPayload();
	
	    return true;
    }

	// BEGIN FACEBOOK STUFF ----------------------------------

	@Override
	public void fbLogin() {
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			Session.OpenRequest openRequest = new Session.OpenRequest(this);
			session.openForRead(openRequest.setPermissions(
					Arrays.asList("friends_games_activity")).setCallback(
					statusCallback));

		} else {
			Session.openActiveSession(this, true, statusCallback);
		}
	}

	@Override
	public void fbLogOut() {
		Session session = Session.getActiveSession();
		if (!session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
		Message msg = handler.obtainMessage();
		msg.arg1 = 3;
		handler.sendMessage(msg);
		// Toast.makeText(this, getString(R.string.logout),
		// Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean fbGetSignedIn() {
		boolean signedState = false;
		Session session = Session.getActiveSession();
		if (session.isOpened() && !session.isClosed()) {
			signedState = true;
		}
		return signedState;
	}
	
	@Override
	public void fbAutoScore(float score){
		Session session = Session.getActiveSession();
		System.out.println("AUTO POSTO CALLED");
		if(session.isOpened() && !session.isClosed() && session.getPermissions().contains("publish_actions")){
			System.out.println("AUTO POSTO");
			final int theScore = (int) (score * 100);		
			
			if (theScore > 0) {
				System.out.println("Auto Score submitted! " + theScore);
				AndroidLauncher.this.runOnUiThread(new Runnable(){
					public void run(){
						Bundle fbParams = new Bundle();
						fbParams.putString("score", "" + theScore);	
						Request postScoreRequest = new Request(Session.getActiveSession(), "me/scores", fbParams,HttpMethod.POST, new Request.Callback() {

							@Override
							public void onCompleted(Response response) {
								System.out.println("Posted yo");
								FacebookRequestError error = response.getError();
								if (error != null) {
									
								} else {

								}
							}
						});					
						Request.executeBatchAsync(postScoreRequest);	
						
					}	
				});					
			}			
		}
	}
	
	@Override
	public void fbSubmitScore(float score) {
		Session session = Session.getActiveSession();
		
		
		
		if (!session.isOpened() && !session.isClosed()) {
			System.out.println("FOO");
			Session.OpenRequest openRequest = new Session.OpenRequest(this);
			session.openForPublish(openRequest.setPermissions(
					Arrays.asList("publish_actions",
							"friends_games_activity")).setCallback(
					statusCallback));
//			session.openForPublish(openRequest.setPermissions(
//					Arrays.asList("publish_actions")).setCallback(
//					statusCallback));
		} else if (!session.isOpened() && session.isClosed()) {
			System.out.println("POO");
						
			session = new Session(this);
			
			Session.setActiveSession(session);
//			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this)
						.setCallback(statusCallback));
//			}
			
			
			
		} else {
//			System.out.println("DOO");
		}
		
//		System.out.println(session.getState());
		
		if (session.isOpened() && !session.isClosed()) {
			fbPostLogic(score);
			if (session.getPermissions().contains("publish_actions")) {
				System.out.println("MOO");
				fbPostLogic(score);
			

			} else if (session.getPermissions().contains("friends_games_activity")) {
				System.out.println("BOO");
				 Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, "publish_actions")             
	             .setDefaultAudience(SessionDefaultAudience.FRIENDS)
	             .setRequestCode(103);
				 session.requestNewPublishPermissions(newPermissionsRequest);
				fbPostLogic(score);			
			}
		}
		

	}
	
	public void fbPostLogic(final float score){
		final int theScore = (int) (score * 100);		
		
		if (theScore > 0) {
			System.out.println("Pub SUBMIT SCORE! " + theScore);
			AndroidLauncher.this.runOnUiThread(new Runnable(){
				public void run(){
					Bundle fbParams = new Bundle();
					fbParams.putString("score", "" + theScore);	
					Request postScoreRequest = new Request(Session.getActiveSession(), "me/scores", fbParams,HttpMethod.POST, new Request.Callback() {

						@Override
						public void onCompleted(Response response) {
							System.out.println("Posted yo");
							FacebookRequestError error = response.getError();
							if (error != null) {
								
							} else {

							}
						}
					});					
					Request.executeBatchAsync(postScoreRequest);	
					
					
					Bundle postParams = new Bundle();
					postParams.putString("name", "Nano the Cat!");
					postParams.putString("caption", "MEOW.");
					postParams.putString("description", "I ran " + Float.toString(score) + " meters in Nano the Cat!");
					postParams.putString("link", "http://play.google.com");
					postParams.putString("picture", "http://lunarcannon.com/img/nano-web-icon.png");
					
					showDialogWithoutNotificationBar("feed", postParams);
					
//					Message msg = handler.obtainMessage();
//					msg.arg1 = 4;
//					handler.sendMessage(msg);
					
				}	
			});
				
		}
	}
	
	private void showDialogWithoutNotificationBar(String action, Bundle params) {
		// Create the dialog
		dialog = new WebDialog.Builder(this, Session.getActiveSession(), action, params).setOnCompleteListener(
				new WebDialog.OnCompleteListener() {

					@Override
					public void onComplete(Bundle values,FacebookException error) {
						if (error != null && !(error instanceof FacebookOperationCanceledException)) {
							System.out.println("error");
						}
						dialog = null;
						dialogAction = null;
						dialogParams = null;
						
					}

				}).build();

		// Hide the notification bar and resize to full screen
		Window dialog_window = dialog.getWindow();
    	dialog_window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
    	// Store the dialog information in attributes
    	dialogAction = action;
    	dialogParams = params;
    	
    	// Show the dialog
    	dialog.show();
	}


	// END FACEBOOK STUFF ----------------------------------

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
		Games.Leaderboards.submitScore(gHelper.getApiClient(),
				"CgkI1Yuo44kEEAIQBg", highScore);

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
		startActivityForResult(Games.Achievements.getAchievementsIntent(gHelper
				.getApiClient()), 1);

	}

	@Override
	public void unlockAchievement(String achievementID) {
		Games.Achievements.unlock(gHelper.getApiClient(), achievementID);

	}

	@Override
	public void getLeaderboard() {
		startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
				gHelper.getApiClient(), "CgkI1Yuo44kEEAIQBg"), 1);

	}

	@Override
	public void showAds(boolean show) {

		adHandler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    if (mHelper != null) {
	    	mHelper.dispose();
	    	mHelper = null;
	    }   
	}
}