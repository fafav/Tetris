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

	int x,move;
	ShapeRenderer sr;
	float TimeSt, TimeUpd;
	double timer = 0.7;
	GameManager myGM;
	float myDeltaManager;
	Label myLab;
	float eventManager;
	Texture screen;
	BitmapFont b;

	MusicActivity myMusic;
	boolean play = false;

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

		arrowLeft_ = new Texture(Gdx.files.internal("arrow/arrowleft.png"));
		arrowRight_ = new Texture(Gdx.files.internal("arrow/arrowright.png"));
		arrowUp_ = new Texture(Gdx.files.internal("arrow/arrowup.png"));
		arrowDown_ = new Texture(Gdx.files.internal("arrow/arrowbottom.png"));

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
		if(play){
			gameT();
			stage.draw();
		}else {
			ScreenT();
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
			public void clicked(InputEvent event, float x, float y) {
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
			public void clicked(InputEvent event, float x, float y) {
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
			public void clicked(InputEvent event, float x, float y) {
				myGM.moveRight(myGM.getMyGrid());
			}
		});
	}

	/****************************************************************************************************/
	public void ScreenT()
	{
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(screen, 0, 0, 1100, 2000);
		batch.end();
		if(Gdx.input.isTouched()){
			play = true;
		}
	}

	/****************************************************************************************************/
	public void printScore()
	{
		BitmapFont font = new BitmapFont();

		String text = "Score : " + myGM.getScore();
		batch.begin();
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, text, 100, 100);
		batch.end();
	}

	/****************************************************************************************************/
	public void initGame()
	{
		b = new BitmapFont();
		Label.LabelStyle ls = new Label.LabelStyle(b, Color.WHITE);
		myLab = new Label("",ls);
		sr = new ShapeRenderer();
		move = 40;
		x = 900;
		myDeltaManager = 0;
		eventManager = 0;

		myGM = new GameManager();
		myGM.generateNextTetrimino();
		batch = new SpriteBatch();
		TimeSt = System.currentTimeMillis();

		myMusic.playTetris();
		myMusic.loopTetris();
	}

	/****************************************************************************************************/
	public void gameT()
	{
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(screen, 0, 0, 1100, 2000);
		batch.end();

		TimeUpd = System.currentTimeMillis();
		myDeltaManager += Gdx.graphics.getDeltaTime();
		eventManager += Gdx.graphics.getDeltaTime();
		printGrid();
		printScore();

		if(eventManager > 0.1){
			manageEvent();
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

	/****************************************************************************************************/
	public void manageEvent()
	{
		int firstX;
		int firstY;

		if(Gdx.input.isTouched()){
			firstX = Gdx.input.getX();
			firstY = Gdx.input.getY();
			System.out.println(firstY);


			if( firstY <= 1836 && firstY >= 1736 && firstX <= 833 && firstX >= 646 )
			{
				myGM.moveBlockDown();
			}
			else if( firstY <= 1561 && firstY >= 1461 && firstX <= 791 && firstX >= 691 )
			{
				myGM.moveLeft(myGM.getMyGrid());
			}
			else if( firstY <= 1478 && firstY >= 1378 && firstX <= 886 && firstX >= 786 )
			{
				myGM.rotate();
			}
			else if( firstY <= 1561 && firstY >= 1461 && firstX <= 986 && firstX >= 886 )
			{
				myGM.moveRight(myGM.getMyGrid());
			}
		}

	}

	/****************************************************************************************************/
	public void printGrid()
	{
		Texture t1,t2;
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