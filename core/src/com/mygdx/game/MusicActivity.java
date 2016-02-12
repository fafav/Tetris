package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
/**
 * Created by Clotilde on 10/02/2016 alias paupiette d'amour.
 */

public class MusicActivity{

    private Music tetris_;
    private Music tetris01_;
    private Music tetris05_;
    private Music gameover_;

    public MusicActivity() {

        tetris_ = Gdx.audio.newMusic(Gdx.files.internal("sounds/tetris.mp3"));
        tetris05_ = Gdx.audio.newMusic(Gdx.files.internal("sounds/tetris05.mp3"));
        tetris01_ = Gdx.audio.newMusic(Gdx.files.internal("sounds/tetris01.mp3"));
        gameover_ = Gdx.audio.newMusic(Gdx.files.internal("sounds/game_over.mp3"));
    }

    /****************************************SOUND*TETRIS******************************************/
    public void playTetris (){
        tetris_.play();
    }

    public void stopTetris (){
        tetris_.stop();
    }

    public void loopTetris (){
        tetris_.setLooping(true);
    }

    /****************************************SOUND*TETRIS*1****************************************/
    public void playTetris01 (){
        tetris01_.play();
    }

    public void stopTetris01 (){
        tetris01_.stop();
    }

    public void loopTetris01 (){
        tetris01_.setLooping(true);
    }

    /****************************************SOUND*TETRIS*5****************************************/
    public void playTetris05 (){
        tetris05_.play();
    }

    public void stopTetris05 (){
        tetris05_.stop();
    }

    public void loopTetris05 (){
        tetris05_.setLooping(true);
    }

    /****************************************SOUND*GAME*OVER***************************************/
    public void playGameOver (){
        gameover_.play();
        gameover_.setLooping(false);
    }

    public void stopGameOver (){
        gameover_.stop();
    }

}
