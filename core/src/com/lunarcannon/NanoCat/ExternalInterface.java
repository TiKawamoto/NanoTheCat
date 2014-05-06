package com.lunarcannon.NanoCat;

public interface ExternalInterface {


public void login();

public void logout();

public boolean getSignedIn();

public void submitScore(long highScore);

public void getScore();

public void getScoreData();

public void getAchievement();

public void unlockAchievement(String achievementID);

public void getLeaderboard();

public void showAds(boolean show);
}



