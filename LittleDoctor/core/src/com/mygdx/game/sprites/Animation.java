package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private float lastChangeTime;
    private int frameCount;
    private int frame;
    private int lastSide=0;

    public Animation(TextureRegion region, int frameCount,float cycleTime ){
        frames= new Array<TextureRegion>();
        int frameWidth=region.getRegionWidth() /frameCount;
        for (int i = 0; i< frameCount;i++){
            frames.add(new TextureRegion(region,i*frameWidth,0,frameWidth,region.getRegionHeight()));
        }
        this.frameCount=frameCount;
        maxFrameTime=cycleTime/frameCount;
        frame = 0;
    }
    public void update(float dt, int side){
        //1-2f 3-4r 5-6l 7-8u 9-10d
        currentFrameTime += dt;
        if(lastSide!=side){
            lastSide=side;
            frame=side;
        }
        if(currentFrameTime>maxFrameTime){
            frame++;
            currentFrameTime=0;
        }
        if(frame>=side+2)
            frame= side;
    }
    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}
