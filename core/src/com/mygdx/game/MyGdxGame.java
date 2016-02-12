package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	BitmapFont font;
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

	@Override
	public void create () {
		screen = new Texture(Gdx.files.internal("tetrisBckg.jpg"));
		myMusic = new MusicActivity();

		arrowLeft_ = new Texture(Gdx.files.internal("arrowleft.png"));
		arrowRight_ = new Texture(Gdx.files.internal("arrowright.png"));
		arrowUp_ = new Texture(Gdx.files.internal("arrowup.png"));
		arrowDown_ = new Texture(Gdx.files.internal("arrowbottom.png"));

		initGame();
	}

	@Override
	public void render() {
		if(play){
			gameT();
		}else{
			ScreenT();
		}
	}

	public void ScreenT(){
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(screen, 30, 0, 800, 1280);
		batch.end();
		if(Gdx.input.isTouched()){
			play = true;
		}
	}

	public void printScore(){

		BitmapFont font = new BitmapFont();

		String text = "Score : " + myGM.getScore();
		batch.begin();
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, text, 100, 100);
		batch.end();
	}

	public void initGame(){
		Stage myStage = new Stage();
		b = new BitmapFont();
		Label.LabelStyle ls = new Label.LabelStyle(b, Color.WHITE);
		myLab = new Label("",ls);
		sr = new ShapeRenderer();
		move = 40;
		x = 900;
		myDeltaManager = 0;
		eventManager = 0;

		//myStage = new Stage();
		//myStage.addActor(myLab);
		//Gdx.input.setInputProcessor(myStage);


		myGM = new GameManager();
		myGM.generateNextTetrimino();
		batch = new SpriteBatch();
		font = new BitmapFont();
		TimeSt = System.currentTimeMillis();


		myMusic.playTetris();
		myMusic.loopTetris();
	}


	public void gameT(){
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
//		myStage.draw();
		printGrid();
		printScore();
		//l.setText(myGM.getSc()+ "");

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
		int firstX;
		int firstY;

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
		Texture t1,t2;
		t1 = new Texture(Gdx.files.internal("linev.png"));
		t2 = new Texture(Gdx.files.internal("lineh.png"));

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