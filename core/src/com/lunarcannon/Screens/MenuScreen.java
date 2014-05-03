package com.lunarcannon.Screens;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lunarcannon.NanoCat.NanoCat;

public class MenuScreen implements Screen {

	private NanoCat game;
	private Stage stage;
	private TextButton playButton;
	private TextButtonStyle buttonStyle;
	private BitmapFont buttonFont;
	private TextureAtlas menuAtlas;
	private Texture fontFilter;
	private AtlasRegion buttonUp, buttonDown, bgDay, logo;
	private SpriteBatch batch;
	private Skin menuSkin;
	private Sprite bgDaySprite, logoSprite;

	private float aspectRatio, xDif, yDif;

	public MenuScreen(NanoCat game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		stage.act(delta);

		batch.begin();
		bgDaySprite.draw(batch);
		
		logoSprite.draw(batch);
		batch.end();
		
		batch.begin();
		batch.enableBlending();
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

		//SPRITES -------------------------------------
		bgDaySprite = new Sprite(bgDay);
		bgDaySprite.setBounds(0, 0, width, height);
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
		
		
		//Button Stuff -------------------------------------
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

	}

}
