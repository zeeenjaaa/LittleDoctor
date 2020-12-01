package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.DoctorGame;

public class Stairs {
    private Texture stairs;
    private Vector2 position;
    private Rectangle bounds;



    public Stairs() {
        stairs = new Texture("Stairs.png");
        position= new Vector2(DoctorGame.WIDTH/2-stairs.getWidth()-20,121);
        bounds = new Rectangle(position.x,position.y,stairs.getWidth(),stairs.getHeight());
    }
    public Vector2 getPosition() {
        return position;
    }

    public Texture getStairs() {
        return stairs;
    }
    public boolean collides (Rectangle player){
        return player.overlaps(bounds);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
