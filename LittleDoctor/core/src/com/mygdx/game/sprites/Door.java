package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Door {
    private Texture door,lampRed,lampGreen;
    private Random rand;
    private Vector2 posDoor,posRedLamp,posGreenLamp;
    private Rectangle bounds;
    public Music lamp,open;
    public boolean isService=false;
    public boolean isRed=false;
    public boolean isOrd=false;
    public boolean isKit=false;
    public BitmapFont font;
    public String timer="";
    public int doorTimer=10;
    public long timerSecond=0;


    public static final int FIRSTFLOOR = 119;
    public static final int SECONDFLOOR= 275;

    public Door(float x, float y){
        font = new BitmapFont();
        door = new Texture("Door.png");
        lampGreen= new Texture("LampGreen.png");
        lampRed= new Texture("LampRed.png");

        posDoor = new Vector2(x,y);
        posRedLamp = new Vector2((posDoor.x+door.getWidth()/2-8),-20);
        posGreenLamp = new Vector2((posDoor.x+door.getWidth()/2-8),(posDoor.y+door.getHeight()+5));
        bounds = new Rectangle(posDoor.x,posDoor.y,door.getWidth(),door.getHeight());
        open = Gdx.audio.newMusic(Gdx.files.internal("door.mp3"));
        lamp = Gdx.audio.newMusic(Gdx.files.internal("lamp.mp3"));
        lamp.setVolume(0.3f);
        open.setVolume(0.15f);

        if(posDoor.x == 600 && posDoor.y==FIRSTFLOOR){
            posRedLamp.y=-40;
            posGreenLamp.y=-40;
            isService=true;
            isKit=true;
        }
        if(posDoor.x == 45 && posDoor.y==SECONDFLOOR){
            posRedLamp.y=-40;
            posGreenLamp.y=-40;
            isService=true;
            isOrd=true;
        }
    }

    public void switchLamp() {
        if(!isService) {
            if (posRedLamp.y == -20) {
                posRedLamp.y = posDoor.y + door.getHeight() + 5;
                posGreenLamp.y = -20;
                isRed=true;
                lamp.play();
            } else if (posGreenLamp.y == -20) {
                posGreenLamp.y = posDoor.y + door.getHeight() + 5;
                posRedLamp.y = -20;
                isRed=false;
                lamp.play();
            }
        }
    }



    public Rectangle getBounds() {
        return bounds;
    }

    public Texture getDoor(){
        return door;
    }
    public Texture getLampRed() {
        return lampRed;
    }

    public Texture getLampGreen() {
        return lampGreen;
    }

    public Vector2 getPosDoor() {
        return posDoor;
    }

    public Vector2 getPosRedLamp() {
        return posRedLamp;
    }

    public Vector2 getPosGreenLamp() {
        return posGreenLamp;
    }
    public boolean collides (Rectangle player){
        return player.overlaps(bounds);
    }

}
