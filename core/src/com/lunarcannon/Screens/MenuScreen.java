package com.lunarcannon.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lunarcannon.GameContainers.AssetLibrary;
import com.lunarcannon.NanoCat.ExternalInterface;
import com.lunarcannon.NanoCat.NanoCat;

public class MenuScreen implements Screen {
	
	private ExternalInterface extInt;

	private NanoCat game;
	private Stage stage;
	private TextButton playButton;
	private Button signButton, signButtonConnected, achieveButton, leaderButton;
	private TextButtonStyle buttonStyle;
	private ButtonStyle signButtonStyle, signButtonConnectedStyle, achieveButtonStyle, leaderButtonStyle;
	private BitmapFont buttonFont;
	private TextureAtlas menuAtlas, gpgsAtlas;
	private Texture fontFilter;
	private AtlasRegion buttonUp, buttonDown, bgDay, logo, gpgsSignGreen, gpgsSignGrey, gpgsSignWhite, gpgsAchieveGreen, gpgsAchieveGrey, gpgsLeaderGreen, gpgsLeaderGrey;
	private SpriteBatch batch;
	private Skin menuSkin, gpgsSkin;
	private Sprite bgDaySprite, logoSprite;

	private float aspectRatio, xDif, yDif;

	public MenuScreen(NanoCat game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
				
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		if(Gdx.input.isKeyPressed(Keys.BACK)){
			Gdx.app.exit();
		}
		
		stage.act(delta);		
		batch.begin();
		bgDaySprite.draw(batch);
		
		logoSprite.draw(batch);
		batch.end();
		
		batch.begin();
		batch.enableBlending();
		
		if(game.extInt.getSignedIn()){
			stage.getRoot().removeActor(signButton);
			stage.addActor(signButtonConnected);
			stage.addActor(achieveButton);
			stage.addActor(leaderButton);
		} else {
			stage.getRoot().removeActor(signButtonConnected);
			stage.getRoot().removeActor(achieveButton);
			stage.getRoot().removeActor(leaderButton);
			stage.addActor(signButton);
		}
		
		
		
		stage.addActor(playButton);		
		stage.draw();				
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {		
		
		
		aspectRatio = height / width;
		
		if (stage == null) {
			stage = new Stage(new ExtendViewport(960, 540));
		}

		stage.clear();
		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		xDif = 960 / (float) width;
		yDif = 540 / (float) height;
		
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
		bgDay = menuAtlas.findRegion("menubg_day");
		logo = menuAtlas.findRegion("logo"); 
		
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
		buttonStyle.unpressedOffsetX = 75;
		buttonStyle.unpressedOffsetY = 2;
		buttonStyle.pressedOffsetX = 76;
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
		
		//GPGS Button Stuff ------------------------------------
		signButton = new Button(signButtonStyle);				
		signButton.setWidth(80);
		signButton.setHeight(80);
		signButton.setX(50);
		signButton.setY(430);
		
		signButton.addListener(new InputListener() { 
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {		 		
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
		signButtonConnected.setColor(1, 1, 1, .65f);
		
		signButtonConnected.addListener(new InputListener() { 
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {		 		
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
		achieveButton.setColor(1, 1, 1, 1f);
		
		achieveButton.addListener(new InputListener() { 
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {		 		
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
		leaderButton.setColor(1, 1, 1, 1f);
		
		leaderButton.addListener(new InputListener() { 
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {		 		
		 		return true;
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		 		if (game.extInt.getSignedIn()){
		 			game.extInt.getLeaderboard();
		 		}
		 	}			
		});
		
		
		//Play Button Stuff -------------------------------------
		playButton = new TextButton("play", buttonStyle);
		playButton.setColor(1, 1, 1, .85f);
		playButton.setWidth(280);
		playButton.setHeight(70);
		playButton.setX(650);
		playButton.setY(270);

		playButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,	int pointer, int button) {
				game.setScreen(new GameScreen(game));
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

	}

}
