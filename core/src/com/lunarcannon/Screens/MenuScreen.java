package com.lunarcannon.Screens;

import java.util.Random;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lunarcannon.GameContainers.AssetLibrary;
import com.lunarcannon.GameContainers.ButtonAccessor;
import com.lunarcannon.GameContainers.GameStateHandler;
import com.lunarcannon.GameContainers.ImageAccessor;
import com.lunarcannon.GameContainers.LabelAccessor;
import com.lunarcannon.NanoCat.ExternalInterface;
import com.lunarcannon.NanoCat.NanoCat;

public class MenuScreen implements Screen {
	
	private ExternalInterface extInt;

	private NanoCat game;
	private Stage stage;
	private Label settingsLabel, premiumLabel, premiumCopyLabel;
	private LabelStyle settingsLabelStyle, premiumLabelStyle, premiumCopyLabelStyle;
	private TextButton playButton, settingsButton, premiumButton;
	private Button signButton, signButtonConnected, achieveButton, leaderButton, hdButton, hdButtonOff, cancelButton, fbButton, fbButtonConnected, muteButton, muteButtonOff, buyPremiumButton;
	private TextButtonStyle buttonStyle, buttonStyleHd;
	private ButtonStyle signButtonStyle, signButtonConnectedStyle, achieveButtonStyle, leaderButtonStyle, hdButtonStyle, hdButtonStyleOff, cancelButtonStyle, fbButtonStyle, fbButtonConnectedStyle, muteButtonStyle, muteButtonStyleOff, buyPremiumButtonStyle;
	private BitmapFont buttonFont;
	private TextureAtlas menuAtlas, gpgsAtlas;
	private Texture fontFilter, panel, line;
	private AtlasRegion buttonUp, buttonDown, bgDay, logo, logoPremium, gpgsSignGreen, gpgsSignGrey, gpgsSignWhite, gpgsAchieveGreen, gpgsAchieveGrey, gpgsLeaderGreen, gpgsLeaderGrey;
	private SpriteBatch batch;
	private Skin menuSkin, gpgsSkin;
	private Sprite bgDaySprite, logoSprite, logoPremiumSprite, panelSprite, lineSprite;
	private Image lineImage, panelImage;

	private float aspectRatio, xDif, yDif;
	private float muteState = 1;
	private int widthCorrect, heightCorrect;
	private String hdText;
	private boolean settingsOn = false;
	private boolean premiumOn = false;
	private TweenManager tweenManager;
	
	private int doOnce = 0;
	private int doOnce2 = 0;
	private int doOnce3 = 0;
	
	private float realX, realY;
	
	private Random randPremium = new Random();
	private int splashNum;

	public MenuScreen(NanoCat game) {
		this.game = game;
		
		splashNum = (randPremium.nextInt(5)+1);
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
	
		
		
		if(GameStateHandler.getMute()){
			muteState = 0;
		} else{
			muteState = 1;
		}
		
		if(Gdx.input.isKeyPressed(Keys.BACK)){
			Gdx.app.exit();
		}
		
		stage.act(delta);		
		batch.begin();
		bgDaySprite.draw(batch);
		if(!game.extInt.getPremium()){
			logoSprite.draw(batch);
		} else {
			logoPremiumSprite.draw(batch);
		}
		batch.end();
		
		batch.begin();
		batch.enableBlending();
		
		if(game.extInt.getSignedIn()){	
			stage.getRoot().removeActor(signButton);
			stage.addActor(signButtonConnected);
			stage.addActor(achieveButton);
			stage.addActor(leaderButton);
		} else if (!game.extInt.getSignedIn()) {
			stage.getRoot().removeActor(signButtonConnected);
			stage.getRoot().removeActor(achieveButton);
			stage.getRoot().removeActor(leaderButton);
			stage.addActor(signButton);
		}
		batch.end();
		
		batch.begin();
		batch.enableBlending();		
		if(game.extInt.fbGetSignedIn()){
			stage.getRoot().removeActor(fbButton);
			stage.addActor(fbButtonConnected);
			
		} else if(!game.extInt.fbGetSignedIn()) {
			stage.getRoot().removeActor(fbButtonConnected);
			stage.addActor(fbButton);			
		}
		
		
		stage.addActor(playButton);
		stage.addActor(settingsButton);
		
		if(!game.extInt.getPremium()){
			stage.addActor(premiumButton);	
		} else if(game.extInt.getPremium()) {
			premiumOn = false;
		}
		
		batch.end();
		batch.begin();
		
		if(settingsOn){
			if(doOnce < 2){
				Tween.registerAccessor(Image.class, new ImageAccessor());
				Tween.registerAccessor(Label.class, new LabelAccessor());
				Tween.registerAccessor(Button.class, new ButtonAccessor());
				tweenManager = new TweenManager();			
				panelImage.setColor(1,1,1,0);
				settingsLabel.setColor(1, 1, 1, 0);
				lineImage.setColor(1,1,1,0);
				hdButton.setColor(1,1,1,0);
				hdButtonOff.setColor(1,1,1,0);
				muteButton.setColor(1,1,1,0);
				muteButtonOff.setColor(1,1,1,0);
				Tween.to(panelImage, ImageAccessor.ALPHA, .2f).target(1f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(lineImage, ImageAccessor.ALPHA, .2f).target(1f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(settingsLabel, LabelAccessor.ALPHA, .2f).target(1f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(cancelButton, ButtonAccessor.ALPHA, .2f).target(1f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(hdButton, ButtonAccessor.ALPHA, .2f).target(.85f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(hdButtonOff, ButtonAccessor.ALPHA, .2f).target(.35f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(muteButton, ButtonAccessor.ALPHA, .2f).target(.85f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(muteButtonOff, ButtonAccessor.ALPHA, .2f).target(.35f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				
				
				doOnce++;
			}			
			tweenManager.update(delta);	
			batch.enableBlending();
//			panelSprite.draw(batch);
//			lineSprite.draw(batch);
			stage.addActor(panelImage);
			stage.addActor(lineImage);
			stage.addActor(settingsLabel);	
			stage.addActor(cancelButton);

			batch.end();	
			
			batch.begin();			
			if(GameStateHandler.getHD()){	
				stage.getRoot().removeActor(hdButtonOff);
				stage.addActor(hdButton);			
			} else {			
				stage.getRoot().removeActor(hdButton);
				stage.addActor(hdButtonOff);		
			}	
			
			if(GameStateHandler.getMute()){	
				stage.getRoot().removeActor(muteButtonOff);
				stage.addActor(muteButton);			
			} else {			
				stage.getRoot().removeActor(muteButton);
				stage.addActor(muteButtonOff);		
			}	
			
		} else if (!premiumOn) {
			if(doOnce < 3){
				
				Tween.registerAccessor(Image.class, new ImageAccessor());
				Tween.registerAccessor(Label.class, new LabelAccessor());
				Tween.registerAccessor(Button.class, new ButtonAccessor());
				tweenManager = new TweenManager();
				Tween.to(panelImage, ImageAccessor.ALPHA, .2f).target(0).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(lineImage, ImageAccessor.ALPHA, .2f).target(0).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(settingsLabel, LabelAccessor.ALPHA, .2f).target(0).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(cancelButton, ButtonAccessor.ALPHA, .2f).target(0).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(hdButton, ButtonAccessor.ALPHA, .2f).target(0).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(hdButtonOff, ButtonAccessor.ALPHA, .2f).target(0).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(muteButton, ButtonAccessor.ALPHA, .2f).target(0).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				Tween.to(muteButtonOff, ButtonAccessor.ALPHA, .2f).target(0).ease(TweenEquations.easeInOutQuad).start(tweenManager);
				
				doOnce++;
			}			
			tweenManager.update(delta);	
			if(panelImage.getColor().a <.001f){
				stage.getRoot().removeActor(panelImage);
				stage.getRoot().removeActor(lineImage);
				stage.getRoot().removeActor(settingsLabel);
				stage.getRoot().removeActor(cancelButton);
				stage.getRoot().removeActor(hdButton);
				stage.getRoot().removeActor(hdButtonOff);
				stage.getRoot().removeActor(muteButton);
				stage.getRoot().removeActor(muteButtonOff);
				doOnce = 0;
			}			
		}
		
		//PremiumOn
		if(!game.extInt.getPremium()){
		
			if(premiumOn){
			
				if(doOnce3 < 2){
					Tween.registerAccessor(Image.class, new ImageAccessor());
					Tween.registerAccessor(Label.class, new LabelAccessor());
					Tween.registerAccessor(Button.class, new ButtonAccessor());
					tweenManager = new TweenManager();			
					panelImage.setColor(1,1,1,0);
					settingsLabel.setColor(1, 1, 1, 0);
					lineImage.setColor(1,1,1,0);				
					Tween.to(panelImage, ImageAccessor.ALPHA, .2f).target(1f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
					Tween.to(lineImage, ImageAccessor.ALPHA, .2f).target(1f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
					Tween.to(premiumLabel, LabelAccessor.ALPHA, .2f).target(1f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
					Tween.to(premiumCopyLabel, LabelAccessor.ALPHA, .2f).target(1f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
					Tween.to(cancelButton, ButtonAccessor.ALPHA, .2f).target(1f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
					Tween.to(buyPremiumButton, ButtonAccessor.ALPHA, .2f).target(1f).ease(TweenEquations.easeInOutQuad).start(tweenManager);
					
					
					doOnce3++;
				}			
				tweenManager.update(delta);	
				batch.enableBlending();
				stage.addActor(panelImage);
				stage.addActor(lineImage);
				stage.addActor(premiumLabel);
				stage.addActor(premiumCopyLabel);	
				stage.addActor(cancelButton);
				stage.addActor(buyPremiumButton);
	
				batch.end();	
				
				batch.begin();		
				
			} else if (!settingsOn) {
				if(doOnce3 < 3){
					
					Tween.registerAccessor(Image.class, new ImageAccessor());
					Tween.registerAccessor(Label.class, new LabelAccessor());
					Tween.registerAccessor(Button.class, new ButtonAccessor());
					tweenManager = new TweenManager();
					Tween.to(panelImage, ImageAccessor.ALPHA, .2f).target(0).ease(TweenEquations.easeInOutQuad).start(tweenManager);
					Tween.to(lineImage, ImageAccessor.ALPHA, .2f).target(0).ease(TweenEquations.easeInOutQuad).start(tweenManager);
					Tween.to(premiumLabel, LabelAccessor.ALPHA, .2f).target(0).ease(TweenEquations.easeInOutQuad).start(tweenManager);
					Tween.to(premiumCopyLabel, LabelAccessor.ALPHA, .2f).target(0).ease(TweenEquations.easeInOutQuad).start(tweenManager);
					Tween.to(cancelButton, ButtonAccessor.ALPHA, .2f).target(0).ease(TweenEquations.easeInOutQuad).start(tweenManager);
					Tween.to(buyPremiumButton, ButtonAccessor.ALPHA, .2f).target(0).ease(TweenEquations.easeInOutQuad).start(tweenManager);
					
					doOnce3++;
				}			
				tweenManager.update(delta);	
				if(panelImage.getColor().a <.001f){
					stage.getRoot().removeActor(panelImage);
					stage.getRoot().removeActor(lineImage);
					stage.getRoot().removeActor(premiumLabel);
					stage.getRoot().removeActor(premiumCopyLabel);
					stage.getRoot().removeActor(cancelButton);
					stage.getRoot().removeActor(buyPremiumButton);
					doOnce3 = 0;
				}			
			}
		}
		
		stage.draw();				
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {		
		GameStateHandler.setAdState(true);
		
		aspectRatio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
		
		if (stage == null) {
			stage = new Stage(new ExtendViewport(960, 540));
		}		
		
		widthCorrect = (int)Math.round(Gdx.graphics.getHeight() / aspectRatio); 
				
		stage.clear();
		stage.getViewport().update(widthCorrect, Gdx.graphics.getHeight());
		
		realX = stage.stageToScreenCoordinates(new Vector2(200,200)).x;
		realY = stage.screenToStageCoordinates(new Vector2(200,200)).y;
		
		xDif = 960 / (float) width;
		yDif = 540 / (float) height;
		
//		System.out.println("GDX Width " + Gdx.graphics.getWidth() + " - GDX Height " + Gdx.graphics.getHeight());
//		System.out.println("Width " + widthCorrect);
		
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);
		
		
		
		

		
		batch = new SpriteBatch();
		
		//Font Stuff  -------------------------------------
		fontFilter = new Texture(Gdx.files.internal("data/roboto_lt.png"));
		fontFilter.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		buttonFont = new BitmapFont(Gdx.files.internal("data/roboto_lt.fnt"), new TextureRegion(fontFilter), false);
		
		//Texture Atlas  -------------------------------------
		menuAtlas = new TextureAtlas(Gdx.files.internal("data/menuitems.atlas"));
		buttonUp = menuAtlas.findRegion("button_up");
		buttonDown = menuAtlas.findRegion("button_down");
		
		//Splash Premium logic ----------------------------------------
				
		
		
		if(game.extInt.getPremium()){
			switch(splashNum){
			case 1:
				bgDay = menuAtlas.findRegion("menubg_day");
				break;
			case 2:
				bgDay = menuAtlas.findRegion("menubg_premium1");
				break;
			case 3:
				bgDay = menuAtlas.findRegion("menubg_premium2");
				break;
			case 4:
				bgDay = menuAtlas.findRegion("menubg_premium3");
				break;
			case 5:
				bgDay = menuAtlas.findRegion("menubg_premium4");
				break;
			default:
				bgDay = menuAtlas.findRegion("menubg_day");	
				break;
			}
			
		} else if (!game.extInt.getPremium()) {
			bgDay = menuAtlas.findRegion("menubg_day");	
		}
		
		
		
		logo = menuAtlas.findRegion("logo");
		logoPremium = menuAtlas.findRegion("logo_premium");
		
		gpgsAtlas = new TextureAtlas(Gdx.files.internal("data/gpgs.atlas"));
		gpgsSignGreen = gpgsAtlas.findRegion("games_controller");
		gpgsSignGrey = gpgsAtlas.findRegion("games_controller_grey");
		gpgsAchieveGreen = gpgsAtlas.findRegion("games_achievements_green");
		gpgsAchieveGrey = gpgsAtlas.findRegion("games_achievements_grey");
		gpgsLeaderGreen= gpgsAtlas.findRegion("games_leaderboards_green");
		gpgsLeaderGrey = gpgsAtlas.findRegion("games_leaderboards_grey");
		

		//SPRITES -------------------------------------
		bgDaySprite = new Sprite(bgDay);
		bgDaySprite.setBounds(0, 0, (float)width, (float) height);
		bgDaySprite.setX(0);
		bgDaySprite.setY(0);
		
		logoSprite = new Sprite(logo);
		logoSprite.setAlpha(.85f);
		logoSprite.setBounds(480 / xDif, 365 / yDif, 450 / xDif, 140 / xDif);
		
		logoPremiumSprite = new Sprite(logoPremium);
		logoPremiumSprite.setAlpha(.85f);
		logoPremiumSprite.setBounds(480 / xDif, 365 / yDif, 450 / xDif, 140 / xDif);
		
		panel = new Texture(Gdx.files.internal("data/panelcolor.png"));
		panel.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		panelImage = new Image(panel);
		panelImage.setBounds(250, 125, 500, 300);
		panelSprite = new Sprite(panel);
		panelSprite.setBounds((float)(widthCorrect / 2) - (230 / xDif), (float)(height / 2) - (145 / xDif), 500 / xDif, 300 / xDif);
		
		
		line = new Texture(Gdx.files.internal("data/whitecolor.png"));
		line.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		lineImage = new Image(line);
		lineImage.setBounds(250, 360, 500, 2);
		lineSprite = new Sprite(line);
		lineSprite.setBounds((float)(widthCorrect / 2) - (230 / xDif), (float)(height / 2) - (-90 / xDif), 500 / xDif, 2);
		
		
//		logoSprite.setX(430);
//		logoSprite.setY(330);
		
		menuSkin = new Skin();
		menuSkin.addRegions(menuAtlas);
		
		//ButtonStyle -------------------------------------
		
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = menuSkin.getDrawable("button_up");
		buttonStyle.down = menuSkin.getDrawable("button_down");
		buttonStyle.font = buttonFont;
		buttonStyle.font.scale(-.5f);
		buttonStyle.unpressedOffsetX = 35;
		buttonStyle.unpressedOffsetY = 2;
		buttonStyle.pressedOffsetX = 36;
		buttonStyle.pressedOffsetY = 1;
		
		
		

		
		//GPGS
		gpgsSkin = new Skin();
		gpgsSkin.addRegions(gpgsAtlas);		
		
		signButtonStyle = new ButtonStyle();
		signButtonStyle.up = gpgsSkin.getDrawable("games_controller");
		signButtonStyle.down = gpgsSkin.getDrawable("games_controller_white");
		
		
		signButtonConnectedStyle = new ButtonStyle();
		signButtonConnectedStyle.up = gpgsSkin.getDrawable("games_controller_grey");
		signButtonConnectedStyle.down = gpgsSkin.getDrawable("games_controller_white");
		
		achieveButtonStyle = new ButtonStyle();
		achieveButtonStyle.up = gpgsSkin.getDrawable("games_achievements_white");
		achieveButtonStyle.down = gpgsSkin.getDrawable("games_achievements");
		
		leaderButtonStyle = new ButtonStyle();
		leaderButtonStyle.up = gpgsSkin.getDrawable("games_leaderboards_white");
		leaderButtonStyle.down = gpgsSkin.getDrawable("games_leaderboards");
		
		muteButtonStyle = new ButtonStyle();
		muteButtonStyle.up = gpgsSkin.getDrawable("mute_on");
		muteButtonStyle.down = gpgsSkin.getDrawable("mute_on");
		
		muteButtonStyleOff = new ButtonStyle();
		muteButtonStyleOff.up = gpgsSkin.getDrawable("mute_off");
		muteButtonStyleOff.down = gpgsSkin.getDrawable("mute_off");
		
		hdButtonStyle = new ButtonStyle();
		hdButtonStyle.up = gpgsSkin.getDrawable("hd_on");
		hdButtonStyle.down = gpgsSkin.getDrawable("hd_on");
		
		hdButtonStyleOff = new ButtonStyle();
		hdButtonStyleOff.up = gpgsSkin.getDrawable("hd_off");
		hdButtonStyleOff.down = gpgsSkin.getDrawable("hd_off");
	
		cancelButtonStyle = new ButtonStyle();
		cancelButtonStyle.up = gpgsSkin.getDrawable("cancel");
		cancelButtonStyle.down = gpgsSkin.getDrawable("cancel");
		
		fbButtonStyle = new ButtonStyle();
		fbButtonStyle.up = gpgsSkin.getDrawable("fb_blue");
		fbButtonStyle.down = gpgsSkin.getDrawable("fb_white");
		
		fbButtonConnectedStyle = new ButtonStyle();
		fbButtonConnectedStyle.up = gpgsSkin.getDrawable("fb_grey");
		fbButtonConnectedStyle.down = gpgsSkin.getDrawable("fb_white");
		
		buyPremiumButtonStyle = new ButtonStyle();
		buyPremiumButtonStyle.up = gpgsSkin.getDrawable("buy_premium");
		buyPremiumButtonStyle.down = gpgsSkin.getDrawable("buy_premium");
		
		//GPGS Button Stuff ------------------------------------			
		signButton = new Button(signButtonStyle);				
		signButton.setWidth(80);
		signButton.setHeight(80);
		signButton.setX(50);
		signButton.setY(430);
		signButton.setColor(1,1,1,1);
		
		signButton.addListener(new InputListener() { 
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {	
				AssetLibrary.select.play(muteState * .5f);
		 		return true;
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		 		if(!game.extInt.getSignedIn()){
		 			game.extInt.login();
		 		} else if (game.extInt.getSignedIn()){
		 			game.extInt.logout();
		 		}
		 	}			
		});
		
		signButtonConnected = new Button(signButtonConnectedStyle);		
		signButtonConnected.setWidth(80);
		signButtonConnected.setHeight(80);
		signButtonConnected.setX(50);
		signButtonConnected.setY(430);
		signButtonConnected.setColor(1, 1, 1, .35f);
		
		signButtonConnected.addListener(new InputListener() { 
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {	
				AssetLibrary.select.play(muteState * .5f);
		 		return true;
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		 		if(!game.extInt.getSignedIn()){
		 			game.extInt.login();
		 		} else if (game.extInt.getSignedIn()){
		 			game.extInt.logout();
		 		}
		 	}			
		});
		
		
		
		
		achieveButton = new Button(achieveButtonStyle);				
		achieveButton.setWidth(80);
		achieveButton.setHeight(80);
		achieveButton.setX(50);
		achieveButton.setY(330);
		achieveButton.setColor(1, 1, 1, 1);
		
		achieveButton.addListener(new InputListener() { 
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {	
				AssetLibrary.select.play(muteState * .5f);
		 		return true;
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		 		if (game.extInt.getSignedIn()){
		 			game.extInt.getAchievement();
		 		}
		 	}			
		});
		
		leaderButton = new Button(leaderButtonStyle);				
		leaderButton.setWidth(80);
		leaderButton.setHeight(80);
		leaderButton.setX(50);
		leaderButton.setY(230);
		leaderButton.setColor(1, 1, 1, 1);
		
		leaderButton.addListener(new InputListener() { 
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {	
				AssetLibrary.select.play(muteState * .5f);
		 		return true;
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		 		if (game.extInt.getSignedIn()){
		 			game.extInt.getLeaderboard();
		 		}
		 	}			
		});
		
		//FB Button Stuff
		
		fbButton = new Button(fbButtonStyle);				
		fbButton.setWidth(80);
		fbButton.setHeight(80);
		fbButton.setX(150);
		fbButton.setY(430);
		
		fbButton.addListener(new InputListener() { 
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {	
				AssetLibrary.select.play(muteState * .5f);
		 		return true;
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		 		
		 		if(game.extInt.fbGetSignedIn()){
		 			game.extInt.fbLogOut();
		 		} else {
		 			game.extInt.fbLogin();
		 		}
		 		doOnce2 = 0;
		 	}					 	
		});
		
		fbButtonConnected = new Button(fbButtonConnectedStyle);		
		fbButtonConnected.setWidth(80);
		fbButtonConnected.setHeight(80);
		fbButtonConnected.setX(150);
		fbButtonConnected.setY(430);
		fbButtonConnected.setColor(1, 1, 1, .35f);
		
		fbButtonConnected.addListener(new InputListener() { 
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {	
				AssetLibrary.select.play(muteState * .5f);
		 		return true;
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		 		if(game.extInt.fbGetSignedIn()){
		 			game.extInt.fbLogOut();
		 		} else {
		 			game.extInt.fbLogin();
		 		}
		 		doOnce2 = 0;
		 	}			
		});
		
		
		//Play Button Stuff -------------------------------------
		playButton = new TextButton("        play", buttonStyle);
		playButton.setX(650);
		playButton.setY(270);
		playButton.setColor(1, 1, 1, .85f);
		playButton.setWidth(280);
		playButton.setHeight(70);

		
			playButton.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					AssetLibrary.select.play(muteState * .5f);
					return true;
				}
	
				public void touchUp(InputEvent event, float x, float y,	int pointer, int button) {
					if(!settingsOn && !premiumOn){
						game.setScreen(new GameScreen(game));
					}
				}
			});
		
		//Settings Button Stuff -------------------------------------
		
		settingsButton = new TextButton("  settings", buttonStyle);
		settingsButton.setX(650);
		settingsButton.setY(170);
		settingsButton.setColor(1, 1, 1, .85f);
		settingsButton.setWidth(280);
		settingsButton.setHeight(70);
		
		
			settingsButton.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					AssetLibrary.select.play(muteState * .5f);
					return true;
				}

				public void touchUp(InputEvent event, float x, float y,	int pointer, int button) {
					if(!settingsOn && !premiumOn){
						settingsOn = true;	
					}
				}
			});
		
		//Premium Button Stuff -------------------------------------
			
			premiumButton = new TextButton("premium", buttonStyle);
			premiumButton.setX(650);
			premiumButton.setY(70);
			premiumButton.setColor(1, 1, 1, .85f);
			premiumButton.setWidth(280);
			premiumButton.setHeight(70);
			
			
			premiumButton.addListener(new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						AssetLibrary.select.play(muteState * .5f);
						return true;
					}

					public void touchUp(InputEvent event, float x, float y,	int pointer, int button) {
						if(!settingsOn && !premiumOn){
							premiumOn = true;	
						}
					}
				});
					
		//HD Settings -------------------------------------
		
		
		hdButton = new Button(hdButtonStyle);		
		hdButton.setX(320);
		hdButton.setY(270);
		hdButton.setWidth(450 * .8f);
		hdButton.setHeight(64 * .8f);
		
		hdButton.setColor(1, 1, 1, 1f);
//		System.out.println("panel --- GETX - " + panelSprite.getX() + " GETY - " + panelSprite.getY());
//		System.out.println("hdButton --- GETX - " + hdButton.getX() + " GETY - " + hdButton.getY());
		

		hdButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				AssetLibrary.select.play(muteState * .5f);
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,	int pointer, int button) {
				if(GameStateHandler.getHD()){
					GameStateHandler.setHD(false);
					AssetLibrary.dispose();
					AssetLibrary.load(game);					
					
				} else{
					GameStateHandler.setHD(true);	
					AssetLibrary.dispose();
					AssetLibrary.load(game);				
				}
				
			}
		});
		
		hdButtonOff = new Button(hdButtonStyleOff);		
		hdButtonOff.setX(320);
		hdButtonOff.setY(270);
		hdButtonOff.setWidth(450 * .8f);
		hdButtonOff.setHeight(64 * .8f);
		
		hdButtonOff.setColor(1, 1, 1, 1f);
		

		hdButtonOff.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				AssetLibrary.select.play(muteState * .5f);
				return true;
				
			}

			public void touchUp(InputEvent event, float x, float y,	int pointer, int button) {
				if(GameStateHandler.getHD()){
					GameStateHandler.setHD(false);
					AssetLibrary.dispose();
					AssetLibrary.load(game);					
					
				} else{
					GameStateHandler.setHD(true);	
					AssetLibrary.dispose();
					AssetLibrary.load(game);				
				}
				
			}
		});
		
	//Mute Settings -------------------------------------
		
		
		muteButton = new Button(muteButtonStyle);		
		muteButton.setX(320);
		muteButton.setY(200);
		muteButton.setWidth(450 * .8f);
		muteButton.setHeight(64 * .8f);
		
		muteButton.setColor(1, 1, 1, 1f);
//		System.out.println("panel --- GETX - " + panelSprite.getX() + " GETY - " + panelSprite.getY());
//		System.out.println("hdButton --- GETX - " + hdButton.getX() + " GETY - " + hdButton.getY());
		

		muteButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				AssetLibrary.select.play(muteState * .5f);
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,	int pointer, int button) {
				if(GameStateHandler.getMute()){
					GameStateHandler.setMute(false);
					AssetLibrary.dispose();
					AssetLibrary.load(game);					
					
				} else{
					GameStateHandler.setMute(true);	
					AssetLibrary.dispose();
					AssetLibrary.load(game);				
				}
				
			}
		});
		
		muteButtonOff = new Button(muteButtonStyleOff);		
		muteButtonOff.setX(320);
		muteButtonOff.setY(200);
		muteButtonOff.setWidth(450 * .8f);
		muteButtonOff.setHeight(64 * .8f);
		
		muteButtonOff.setColor(1, 1, 1, 1f);
		

		muteButtonOff.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//				AssetLibrary.select.play(muteState * .5f);
				return true;
				
			}

			public void touchUp(InputEvent event, float x, float y,	int pointer, int button) {
				if(GameStateHandler.getMute()){
					GameStateHandler.setMute(false);
					AssetLibrary.dispose();
					AssetLibrary.load(game);					
					
				} else{
					GameStateHandler.setMute(true);	
					AssetLibrary.dispose();
					AssetLibrary.load(game);				
				}
				
			}
		});
		
		
		//BuyPremium --------------------------------------------
		buyPremiumButton = new Button(buyPremiumButtonStyle);		
		buyPremiumButton.setX((500) - ((450 * .6f) / 2));
		buyPremiumButton.setY(150);
		buyPremiumButton.setWidth(450 * .6f);
		buyPremiumButton.setHeight(80 * .6f);
		
		buyPremiumButton.setColor(1, 1, 1, 1f);
		

		buyPremiumButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				AssetLibrary.select.play(muteState * .5f);
				return true;
				
			}

			public void touchUp(InputEvent event, float x, float y,	int pointer, int button) {
				game.extInt.buyPremium();
			}
		});
		
		//Labels ----------------------------------
		
		settingsLabelStyle = new LabelStyle();
		settingsLabelStyle.font = buttonFont;		 
		settingsLabel = new Label("settings", settingsLabelStyle);
		settingsLabel.setX(280);
		settingsLabel.setY(370);
		settingsLabel.setScale(1f);
		
		premiumLabelStyle = new LabelStyle();
		premiumLabelStyle.font = buttonFont;		 
		premiumLabel = new Label("buy premium", premiumLabelStyle);
		premiumLabel.setX(280);
		premiumLabel.setY(370);
		premiumLabel.setScale(1f);
		
		premiumCopyLabelStyle = new LabelStyle();
		premiumCopyLabelStyle.font = buttonFont; 
		premiumCopyLabel = new Label("  - remove ads\n  - support developer\n  - buy nano cat food", premiumCopyLabelStyle);
		premiumCopyLabel.setX(280);
		premiumCopyLabel.setY(190);
		premiumCopyLabel.setFontScale(.3f);
		
		
		cancelButton = new Button(cancelButtonStyle);		
		cancelButton.setX(690);
		cancelButton.setY(370);
		cancelButton.setWidth(50 * .8f);
		cancelButton.setHeight(50 * .8f);
		
		cancelButton.setColor(1, 1, 1, 1f);
		

		cancelButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				AssetLibrary.select.play(muteState * .5f);
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,	int pointer, int button) {
				if(settingsOn){
					settingsOn = false;	
				}
				if(premiumOn){
					premiumOn = false;	
				}
			}
		});
		
	}

	@Override
	public void show() {

		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		menuAtlas.dispose();
		menuSkin.dispose();
		stage.dispose();
		buttonFont.dispose();
		batch.dispose();
		gpgsAtlas.dispose();
		gpgsSkin.dispose();
		fontFilter.dispose();
		line.dispose();
		panel.dispose();
		

	}

}
