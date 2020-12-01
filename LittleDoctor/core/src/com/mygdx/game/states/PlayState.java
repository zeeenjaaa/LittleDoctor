package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.DoctorGame;
import com.mygdx.game.sprites.Doctor;
import com.mygdx.game.sprites.Door;
import com.mygdx.game.sprites.Stairs;
import com.sun.org.apache.regexp.internal.RE;

import java.util.concurrent.TimeUnit;


public class PlayState extends State {


    private Vector3 touchPos;
    private Doctor doctor;
    private Array<Door> doors;
    private Stairs stairs;
    private Texture bg,leftButton,leftButtonPush,rightButton,rightButtonPush,upButton,upButtonPush;
    private static float showButton;
    private static final float hideButton=-100;
    private static float leftButtonHeight, rightButtonHeight, upButtonHeight;
    private BitmapFont font;
    private long lastSpawnTime,doorInside;
    private String gameOverMessage="";
    private Music gameOverMusic;


    private int doorTimer=10;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("GameOver.mp3"));
        gameOverMusic.setVolume(0.3f);
        gameOverMusic.setLooping(true);



        font=new BitmapFont();
        font.setColor(Color.RED);
        font.getData().setScale(5);


        touchPos = new Vector3();
        leftButtonHeight=hideButton;
        rightButtonHeight=hideButton;
        upButtonHeight=hideButton;
        doctor = new Doctor(100,117);
        camera.setToOrtho(false, DoctorGame.WIDTH, DoctorGame.HEIGHT);
        bg = new Texture("Background.png");

        leftButton = new Texture("LeftButton.png");
        leftButtonPush = new Texture("LeftButtonPush.png");
        rightButton = new Texture("RightButton.png");
        rightButtonPush = new Texture("RightButtonPush.png");
        upButton = new Texture("UpButton.png");
        upButtonPush = new Texture("UpButtonPush.png");


        stairs= new Stairs();

        doors = new Array<Door>();
        doors.add(new Door(45,Door.FIRSTFLOOR));
        doors.add(new Door(180,Door.FIRSTFLOOR));
        doors.add(new Door(470,Door.FIRSTFLOOR));
        doors.add(new Door(600,Door.FIRSTFLOOR));
        doors.add(new Door(45,Door.SECONDFLOOR));
        doors.add(new Door(180,Door.SECONDFLOOR));
        doors.add(new Door(470,Door.SECONDFLOOR));
        doors.add(new Door(600,Door.SECONDFLOOR));


        showButton= camera.viewportHeight/15;
    }


    @Override
    protected void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            doctor.left();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            doctor.right();
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){

                upButtonHeight=showButton;
                if(stairs.collides(doctor.getBounds())){
                    doctor.goStairs();
                }
                for(Door door:doors){
                    if(door.collides(doctor.getBounds())&!doctor.hided){
                        doctor.hide();
                        door.open.play();
                        door.doorTimer=10;
                        if(door.isRed)door.switchLamp();
                        if (door.isKit)doctor.hunger-=15;
                        if(door.isOrd)doctor.health+=15;
                    }

                }

            }
            else {
                upButtonHeight=hideButton;
            }

///////////////////

        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchPos);
            if(touchPos.x>DoctorGame.WIDTH/20 && touchPos.x< (DoctorGame.WIDTH/20+leftButton.getWidth())){
                leftButtonHeight=showButton;
                doctor.left();
            }
            else {
                leftButtonHeight=hideButton;
            }
        }
        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchPos);
            if(touchPos.x>DoctorGame.WIDTH/6 && touchPos.x< (DoctorGame.WIDTH/6+leftButton.getWidth())){
                rightButtonHeight=showButton;
                doctor.right();
            }
            else {
                rightButtonHeight=hideButton;
            }
        }

        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchPos);

            if(touchPos.x>(DoctorGame.WIDTH-DoctorGame.WIDTH/5) && touchPos.x<((DoctorGame.WIDTH-DoctorGame.WIDTH/5)+upButton.getWidth())){
                upButtonHeight=showButton;
                if(stairs.collides(doctor.getBounds())){
                    doctor.goStairs();
                }

                for(Door door:doors){
                    if(door.collides(doctor.getBounds())&!doctor.hided){
                        doctor.hide();
                        door.open.play();
                        door.doorTimer=10;
                        if(door.isRed)door.switchLamp();
                        if (door.isKit)doctor.hunger-=15;
                        if(door.isOrd)doctor.health+=15;
                    }

                }

            }
            else {
                upButtonHeight=hideButton;
            }
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        doctor.update(dt);
        if (doctor.health<=0||doctor.hunger>=100){
            gameOverMessage="Game Over";
            doctor.canGo=false;
            gameOverMusic.play();
        }
        if (doctor.day<=0){
            doctor.canGo=false;
            gameOverMessage="You WIN!";
        }

        if(TimeUtils.nanoTime()-doctor.docTimer>1000000000){
            doctor.docTimer=TimeUtils.nanoTime();
            doctor.day--;
            doctor.hunger++;
        }

        if(TimeUtils.nanoTime()-lastSpawnTime>2140000000){
            spawnDoors();
            lastSpawnTime = TimeUtils.nanoTime();

        }
        if(doctor.hided && TimeUtils.nanoTime()-doorInside>1000000000) {
            doctor.show();
            doorInside=TimeUtils.nanoTime();
        }

        for(Door door:doors){
            if (door.isRed&&(TimeUtils.nanoTime()-door.timerSecond>1000000000)){
                door.timerSecond=TimeUtils.nanoTime();
                door.doorTimer--;
                door.timer=Integer.toString(door.doorTimer);

                if(door.doorTimer==0){
                    doctor.health-=25;
                    door.doorTimer=10;
                    door.switchLamp();
                }
            }
        }






    }
    public void spawnDoors(){
        int i=MathUtils.random(0, 7);
        if (!doors.get(i).isRed)
        doors.get(i).switchLamp();
        lastSpawnTime= TimeUtils.nanoTime();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(bg,camera.position.x-(camera.viewportWidth/2),0);

        sb.draw(stairs.getStairs(),stairs.getPosition().x,stairs.getPosition().y);

        for(Door door:doors){
            sb.draw(door.getDoor(),door.getPosDoor().x,door.getPosDoor().y);
            sb.draw(door.getLampGreen(),door.getPosGreenLamp().x,door.getPosGreenLamp().y);
            sb.draw(door.getLampRed(),door.getPosRedLamp().x,door.getPosRedLamp().y);
            if(door.isRed)
                door.font.draw(sb,door.timer,door.getPosRedLamp().x,door.getPosRedLamp().y+70);
        }


        sb.draw(doctor.getDoctor(),doctor.getPosition().x,doctor.getPosition().y);

        doctor.dayFont.draw(sb,Integer.toString(doctor.day),760,385);
        doctor.healthFont.draw(sb,Integer.toString(doctor.health),760,440);
        doctor.hungerFont.draw(sb,Integer.toString(doctor.hunger),760,415);

        sb.draw(leftButton,DoctorGame.WIDTH/20,camera.viewportHeight/15);
        sb.draw(rightButton,camera.viewportWidth/6,camera.viewportHeight/15);
        sb.draw(upButton,camera.viewportWidth-camera.viewportWidth/5,camera.viewportHeight/15);

        sb.draw(leftButtonPush,DoctorGame.WIDTH/20,leftButtonHeight);
        sb.draw(rightButtonPush,camera.viewportWidth/6,rightButtonHeight);
        sb.draw(upButtonPush,camera.viewportWidth-camera.viewportWidth/5,upButtonHeight);

        font.draw(sb,gameOverMessage,camera.viewportWidth/4, camera.viewportHeight/2);
        sb.end();
    }

    @Override
    public void dispose() {
        doctor.dispose();


    }
}
