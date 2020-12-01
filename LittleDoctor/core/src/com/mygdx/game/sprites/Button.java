package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Button {
    private Vector2 position;
    private Texture button,pushButton,thirdTxt;

    public Button(Vector2 position, Texture button, Texture pushButton) {
        this.position = position;
        this.button = button;
        this.pushButton = pushButton;

    }

    public Vector2 getPosBtn(){
        return position;
    }
    public void switchBtn(){
        thirdTxt=button;
        button=pushButton;
        pushButton=thirdTxt;
    }
}
