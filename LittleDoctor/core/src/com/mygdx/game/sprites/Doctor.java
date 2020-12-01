package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

import java.util.concurrent.TimeUnit;

import javax.print.Doc;

public class Doctor {
    public  int GRAVITY = 0;
    private Vector3 position;
    private Vector3 velosity;

    private Rectangle bounds;
    private Texture doctor;
    private int floor=1;
    public boolean canGo=true;
    private int side=1;
    private Music steps,stairsSteps;
    private float previousPositionY;
    public boolean hided=false;
    public BitmapFont hungerFont,healthFont,scoreFont,dayFont;
    public int health,hunger,score,day;
    public long docTimer;



    private Animation docAnimation;

    public Doctor(int x, int y){
        steps = Gdx.audio.newMusic(Gdx.files.internal("steps.mp3"));
        stairsSteps = Gdx.audio.newMusic(Gdx.files.internal("stepsStairs.mp3"));
        steps.setVolume(1.2f);
        stairsSteps.setVolume(0.3f);

        position= new Vector3(x,y,0);
        velosity= new Vector3(0,0,0);
        Texture texture = new Texture("DocAnimation.png");
        docAnimation= new Animation(new TextureRegion(texture),10,3f);
        dayFont = new BitmapFont();
        scoreFont= new BitmapFont();
        hungerFont= new BitmapFont();
        healthFont= new BitmapFont();

        health=100;
        hunger=0;
        score=0;
        day=100;
        bounds= new Rectangle(x,y,texture.getWidth()/10,texture.getHeight());

    }


    public void hide(){
        hided=true;
        previousPositionY=position.y;
        position.y=-300;
    }

    public void show(){
        position.y = previousPositionY;
        hided=false;
    }

    public void update(float dt){

        docAnimation.update(dt,side);


        if(position.y>0)
        velosity.add(0,GRAVITY, 0);
        velosity.scl(dt);
        position.add(0,velosity.y,0);
        velosity.scl(1/dt);

        if(floor==2)
            if (position.y < 117) {
                position.y = 117;
                velosity.y=0;
                floor=1;
            }

        if(floor==1)
        if(position.y>265){
            position.y=270;
            velosity.y=0;
            floor=2;
        }



        if(position.x<6)
            position.x=6;

        if(position.x>642)
            position.x=642;

        if(position.y>117&&position.y<265)
            canGo=false;
        else {
            canGo=true;
            side=0;
            stairsSteps.setLooping(false);
        }

        bounds.setPosition(position.x,position.y);
    }
    public void goStairs()  {
        if(floor==1){

            stairsSteps.play();
            stairsSteps.setLooping(true);
            side=6;
            velosity.y+=25;
            bounds.setPosition(position.x,position.y);
        }
        if(floor==2){
            stairsSteps.play();
            stairsSteps.setLooping(true);
            side=8;
            velosity.y-=25;
            bounds.setPosition(position.x,position.y);
        }
    }

    public void left() {
        if(canGo&&!hided) {
            side = 4;
            position.x -= 5;
            steps.play();
        }

    }
    public void right() {
        if(canGo&&!hided){
            side=2;
            position.x+=5;
            steps.play();
        }
    }
    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getDoctor() {
        return docAnimation.getFrame();
    }

    public Rectangle getBounds() {
        return bounds;
    }
    public void dispose(){
        steps.dispose();
        stairsSteps.dispose();
    }
}
