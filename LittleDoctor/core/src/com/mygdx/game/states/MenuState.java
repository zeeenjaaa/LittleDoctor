package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.DoctorGame;

public class MenuState extends State {

    private Texture background;
    private Texture playBtn;
    private Texture playBtnPush;
    private Vector3 touchPos;
    private int showButton,hideButton=-300;
    private static int playBtnHeight,playBtnPushHeight;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false,DoctorGame.WIDTH,DoctorGame.HEIGHT);
        playBtnPushHeight=hideButton;
        playBtnHeight=showButton;
        touchPos = new Vector3();
        background = new Texture("MenuScreen.png");
        playBtn = new Texture("PlayButton.png");
        playBtnPush = new Texture("PlayButtonPush.png");
        showButton= (DoctorGame.HEIGHT/2)-(playBtn.getHeight());
    }

    @Override
    protected void handleInput() {
        /*if(Gdx.input.isTouched()){
            gsm.set(new PlayState(gsm));
        }*/
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
            gsm.set(new PlayState(gsm));
        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchPos);
            if(touchPos.x>((DoctorGame.WIDTH/2)-(playBtn.getWidth()/2))&&touchPos.x<((DoctorGame.WIDTH/2)+(playBtn.getWidth()/2))){
                if(touchPos.y>((DoctorGame.HEIGHT/2)) &&touchPos.y< ((DoctorGame.HEIGHT/2)+playBtn.getHeight()) ){
                    playBtnPushHeight=showButton;
                    gsm.set(new PlayState(gsm));
                }
            }
            else {
                playBtnPushHeight=hideButton;
            }

        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background,0,0);
        sb.draw(playBtn,(DoctorGame.WIDTH/2)-(playBtn.getWidth()/2),(DoctorGame.HEIGHT/2)-playBtn.getHeight());
        sb.draw(playBtnPush,(DoctorGame.WIDTH/2)-(playBtn.getWidth()/2),playBtnPushHeight);
        sb.end();
    }

    @Override
    public void dispose() {

        background.dispose();
        playBtn.dispose();
        playBtnPush.dispose();
    }
}
