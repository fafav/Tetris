package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	BitmapFont font, fontTitle, fontStart;
	ShapeRenderer sr;
	Texture screen, t1, t2;
	GameManager myGM;
	MusicActivity myMusic;

	double timer = 0.7;
	float myDeltaManager, eventManager, TimeSt,TimeUpd;
	int x, move;
	boolean play = false;

	private static Color red_;
	private static Color orange_;
	private static Color yellow_;
	private static Color green_;
	private static Color blue_;
	private static Color purple_;

	private Texture arrowLeft_;
	private Texture arrowRight_;
	private Texture arrowUp_;
	private Texture arrowDown_;

	Stage stage;

	Button buttonDown;
	Button buttonLeft;
	Button buttonUp;
	Button buttonRight;

	/****************************************************************************************************/
	@Override
	public void create ()
	{
		screen = new Texture(Gdx.files.internal("background/tetrisBckg.png"));
		myMusic = new MusicActivity();
		batch = new SpriteBatch();
		fontTitle = new BitmapFont(Gdx.files.internal("fonts/nine_pin.fnt"), Gdx.files.internal("fonts/nine_pin.png"), false);
		fontStart = new BitmapFont(Gdx.files.internal("fonts/press.fnt"), Gdx.files.internal("fonts/press.png"), false);
		//Need to have/generate font white to add color later

		arrowLeft_ = new Texture(Gdx.files.internal("arrow/arrowleft.png"));
		arrowRight_ = new Texture(Gdx.files.internal("arrow/arrowright.png"));
		arrowUp_ = new Texture(Gdx.files.internal("arrow/arrowup.png"));
		arrowDown_ = new Texture(Gdx.files.internal("arrow/arrowbottom.png"));

		//To convert: color rgba/255.0f !! Not for rgba 1
		red_ = new Color(0.866f, 0.137f, 0.141f, 1.0f);
		//RGBA RED = (221, 35, 36, 1);
		orange_ = new Color(0.839f, 0.462f, 0.023f, 1.0f);
		//RGBA ORANGE = (214, 118, 6, 1);
		yellow_ = new Color(0.827f, 0.850f, 0.098f, 1.0f);
		//RGBA YELLOW = (211, 217, 25, 1);
		green_ = new Color(0.537f, 0.796f, 0.047f, 1.0f);
		//RGBA GREEN  = (137, 203, 12, 1);
		blue_ = new Color(0.031f , 0.564f, 0.898f, 1.0f);
		//RGBA BLUE = (8, 144, 229, 1);
		purple_ = new Color(0.843f, 0.094f, 0.949f, 1.0f);
		//RGBA PURPLE = (215, 24, 242, 1);

		buttonDown = new Button();
		buttonDown.setPosition(750, 300);
		buttonLeft = new Button();
		buttonLeft.setPosition(625, 425);
		buttonUp = new Button();
		buttonUp.setPosition(750, 550);
		buttonRight = new Button();
		buttonRight.setPosition(875, 425);

		stage = new Stage();

		createButtons(buttonDown, arrowDown_);
		initListenerDown();
		createButtons(buttonLeft, arrowLeft_);
		initListenerLeft();
		createButtons(buttonUp, arrowUp_);
		initListenerUp();
		createButtons(buttonRight, arrowRight_);
		initListenerRight();

		initGame();
	}

	/****************************************************************************************************/
	@Override
	public void render()
	{
		if(play)
		{
			gameP();
			stage.draw();
		}
		else
		{
			homeP();
		}
	}

	/****************************************************************************************************/
	public void createButtons(final Button button, Texture texture)
	{
		Skin skin = new Skin();
		skin.add("", texture);
		Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
		buttonStyle.up = skin.getDrawable("");
		buttonStyle.down = skin.getDrawable("");
		button.setStyle(buttonStyle);

		button.setSize(150, 150);
		button.center();

		stage.addActor(button);
		Gdx.input.setInputProcessor(stage);
	}

	/****************************************************************************************************/
	public void initListenerDown()
	{
		buttonDown.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				myGM.moveBlockDown();
			}
		});
	}

	/***************************************************************************************************/
	public void initListenerLeft()
	{
		buttonLeft.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				myGM.moveLeft(myGM.getMyGrid());
			}
		});
	}

	/****************************************************************************************************/
	public void initListenerUp()
	{
		buttonUp.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				myGM.rotate();
			}
		});
	}

	/****************************************************************************************************/
	public void initListenerRight()
	{
		buttonRight.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				myGM.moveRight(myGM.getMyGrid());
			}
		});
	}

	public static Color getRed() { return red_; }
	public static Color getOrange() { return orange_; }
	public static Color getYellow() { return yellow_; }
	public static Color getGreen() { return green_; }
	public static Color getBlue() { return blue_; }
	public static Color getPurple() { return purple_; }

	/****************************************************************************************************/
	public void homeP()
	{
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(screen, 0, 0, 1600, 1800);
		fontTitle.setColor(MyGdxGame.getRed());
		fontTitle.draw(batch, "T", 300, 1600);
		fontTitle.setColor(MyGdxGame.getOrange());
		fontTitle.draw(batch, "E", 375, 1600);
		fontTitle.setColor(MyGdxGame.getYellow());
		fontTitle.draw(batch, "T", 450, 1600);
		fontTitle.setColor(MyGdxGame.getGreen());
		fontTitle.draw(batch, "R", 525, 1600);
		fontTitle.setColor(MyGdxGame.getBlue());
		fontTitle.draw(batch, "I", 600, 1600);
		fontTitle.setColor(MyGdxGame.getPurple());
		fontTitle.draw(batch, "S", 675, 1600);
        fontStart.setColor(Color.BLACK);
		fontStart.draw(batch, "Press to start", 375, 1400);
		batch.end();

		if(Gdx.input.isTouched()){
			play = true;
		}
	}

	/*************************************************************************/
	public void printScore()
	{
		BitmapFont score = new BitmapFont();
		String text = "Score : " + myGM.getScore();
		batch.begin();
		score.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		score.draw(batch, text, 100, 100);
		batch.end();
	}

	/*************************************************************************/
	public void initGame()
	{
		sr = new ShapeRenderer();
		move = 40;
		x = 900;
		myDeltaManager = 0;
		eventManager = 0;

		myGM = new GameManager();
		myGM.generateNextTetrimino();
		batch = new SpriteBatch();
		font = new BitmapFont();
		TimeSt = System.currentTimeMillis();
		myMusic.playTetris05();
		myMusic.loopTetris05();
	}

	/*************************************************************************/
	public void gameP()
	{
		myMusic.stopTetris05();
		myMusic.playTetris();
		myMusic.loopTetris();

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(screen, 0,0,1600, 1800);
		batch.end();

		TimeUpd = System.currentTimeMillis();
		myDeltaManager += Gdx.graphics.getDeltaTime();
		eventManager += Gdx.graphics.getDeltaTime();

		printGrid();
		printScore();

		if(eventManager > 0.1){
			eventManager = 0;
		}

		myGM.printCurrentT(sr);
		myGM.printOtherT(sr);

		if(myDeltaManager > 0){
			if(myDeltaManager / timer >= 1){
				myGM.moveBlockDown();
				myDeltaManager = 0;
			}
		}

		if(myGM.checkGameOver()){
			myMusic.stopTetris();
			myMusic.playGameOver();
			play = false;
		}
	}

	/*************************************************************************/
	public void printGrid()
	{
		t1 = new Texture(Gdx.files.internal("grid/linev.png"));
		t2 = new Texture(Gdx.files.internal("grid/lineh.png"));

		batch.begin();

		batch.draw(t1,140,140);
		batch.draw(t1,180,140);
		batch.draw(t1,220,140);
		batch.draw(t1,260,140);
		batch.draw(t1,300,140);
		batch.draw(t1,340,140);
		batch.draw(t1,380,140);
		batch.draw(t1,420,140);
		batch.draw(t1,460,140);
		batch.draw(t1,500,140);
		batch.draw(t1,540,140);

		batch.draw(t2,140,140);
		batch.draw(t2,140,180);
		batch.draw(t2,140,220);
		batch.draw(t2,140,260);
		batch.draw(t2,140,300);
		batch.draw(t2,140,340);
		batch.draw(t2,140,380);
		batch.draw(t2,140,420);
		batch.draw(t2,140,460);
		batch.draw(t2,140,500);
		batch.draw(t2,140,540);
		batch.draw(t2,140,580);
		batch.draw(t2,140,620);
		batch.draw(t2,140,660);
		batch.draw(t2,140,700);
		batch.draw(t2,140,740);
		batch.draw(t2,140,780);
		batch.draw(t2,140,820);
		batch.draw(t2,140,860);
		batch.draw(t2,140,900);
		batch.draw(t2,140,940);
		batch.draw(t2,140,980);

		batch.end();
	}
}