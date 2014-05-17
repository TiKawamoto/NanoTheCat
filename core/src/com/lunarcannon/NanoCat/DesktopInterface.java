package com.lunarcannon.NanoCat;

public class DesktopInterface implements ExternalInterface {

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
	public void submitScore(long highScore) {
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
	
	@Override
	public void showAds(boolean show){}
	
	@Override
	public void fbLogin(){}
	
	@Override
	public void fbLogOut(){}
	
	@Override
	public boolean fbGetSignedIn(){
	return false;
	}

	public void fbSubmitScore(float score){}
	
	
	public void buyPremium(){}
	
	public boolean getPremium(){
		return false;		
	}
}
