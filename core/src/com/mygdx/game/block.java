package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by val on 05/02/2016.
 */
public class block {

    private int x;
    private int y;
    private Color c;
    private blockType bt;

    private static Texture textureL_;
    private static Texture textureJ_;
    private static Texture textureI_;
    private static Texture textureT_;
    private static Texture textureO_;
    private static Texture textureS_;
    private static Texture textureZ_;

    public block(int x,int y, blockType bt){

        this.x = x;
        this.y = y;
        this.bt=bt;
        switch (bt){
            case j:
                textureJ_ = new Texture(Gdx.files.internal("tetrominos/J.jpg"));
                c = new Color(0,26,255,0);
                break;
            case l:
                textureL_ = new Texture(Gdx.files.internal("tetrominos/L.jpg"));
                c = new Color(135,228,255,0);
                break;
            case o:
                textureO_ = new Texture(Gdx.files.internal("tetrominos/O.jpg"));
                c = new Color(255,255,0,0);
                break;
            case s:
                textureS_ = new Texture(Gdx.files.internal("tetrominos/S.jpg"));
                c = new Color(0,255,0,0);
                break;
            case t:
                textureT_ = new Texture(Gdx.files.internal("tetrominos/T.jpg"));
                c = new Color(203,26,186,0);
                break;
            case z:
                textureZ_ = new Texture(Gdx.files.internal("tetrominos/Z.jpg"));
                c = new Color(255,0,0,0);
                break;
            case i:
                textureI_ = new Texture(Gdx.files.internal("tetrominos/I.jpg"));
                c = new Color(255,255,0,0);
                break;
        }
    }

    public static Texture getL()
    {
        return textureL_;
    }

    public static Texture getI()
    {
        return textureI_;
    }

    public static Texture getT()
    {
        return textureT_;
    }

    public static Texture getJ()
    {
        return textureJ_;
    }

    public static Texture getO()
    {
        return textureO_;
    }

    public static Texture getS()
    {
        return textureS_;
    }

    public static Texture getZ()
    {
        return textureZ_;
    }



    public blockType getBt(){
        return this.bt;
    }


    public Color getC(){
        return this.c;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void PrintBlockXY(){
        System.out.println("Block :  x " + this.getX() + " - y " + this.getY());
    }

    public boolean checkCollideDown(boolean[][] grid){
        if(grid[this.getX()][this.getY() + 1] == true){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkCollideRight(boolean[][] grid){
        if(grid[this.getX()+1][this.getY()] == true){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkCollideLeft(boolean[][] grid){
        if(grid[this.getY()-1][this.getX()] == true){
            return true;
        }else{
            return false;
        }
    }
}
