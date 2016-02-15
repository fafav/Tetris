package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;


public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	BitmapFont font, fontTitle, fontStart;
	ShapeRenderer sr;
	Texture screen, t1, t2;
	GameManager myGM;
	MusicActivity myMusic;

	double timer = 0.7;
	float myDeltaManager, eventManager, TimeSt,TimeUpd;
	int x, move, firstX, firstY;
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

	@Override
	public void create () {
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

		initGame();
	}

	public static Color getRed() { return red_; }
	public static Color getOrange() { return orange_; }
	public static Color getYellow() { return yellow_; }
	public static Color getGreen() { return green_; }
	public static Color getBlue() { return blue_; }
	public static Color getPurple() { return purple_; }

	@Override
	public void render() {
		if(play){
			gameP();
		}else{
			homeP();
		}
	}

	public void homeP(){
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(screen, 30, 0, 800, 1280);
		fontTitle.setColor(MyGdxGame.getRed());
		fontTitle.draw(batch, "T", 200, 1100);
		fontTitle.setColor(MyGdxGame.getOrange());
		fontTitle.draw(batch, "E", 275, 1100);
		fontTitle.setColor(MyGdxGame.getYellow());
		fontTitle.draw(batch, "T", 350, 1100);
		fontTitle.setColor(MyGdxGame.getGreen());
		fontTitle.draw(batch, "R", 425, 1100);
		fontTitle.setColor(MyGdxGame.getBlue());
		fontTitle.draw(batch, "I", 500, 1100);
		fontTitle.setColor(MyGdxGame.getPurple());
		fontTitle.draw(batch, "S", 575, 1100);
        fontStart.setColor(Color.BLACK);
		fontStart.draw(batch, "Press to start", 275, 900);
		batch.end();

		if(Gdx.input.isTouched()){
			play = true;
		}
	}

	public void printScore(){
		BitmapFont score = new BitmapFont();
		String text = "Score : " + myGM.getScore();
		batch.begin();
		score.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		score.draw(batch, text, 100, 100);
		batch.end();
	}

	public void initGame(){
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


	public void gameP(){
		myMusic.stopTetris05();
		myMusic.playTetris();
		myMusic.loopTetris();

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(arrowDown_, 780, 140, 100, 100);
		batch.draw(arrowLeft_, 680, 220, 100, 100);
		batch.draw(arrowUp_, 780, 300, 100, 100);
		batch.draw(arrowRight_, 880, 220, 100, 100);
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

	public void manageEvent(){

		if(Gdx.input.isTouched()){
			firstX = Gdx.input.getX();
			firstY = Gdx.input.getY();
			System.out.println(firstY);

			if( firstY <= 1636 && firstY >= 1536 && firstX <= 833 && firstX >= 746 )
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

	public void printGrid(){
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