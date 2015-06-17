package com.lappard.android.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class AnimatedSprite extends Sprite {

    private float time;
    private Animation animation;
    private boolean looping = true;

    public AnimatedSprite(Texture texture, int colls, int rows, float frameDuration) {
        super(texture, texture.getWidth() / colls, texture.getHeight() / rows);


        //split texture and put all frames in one dimensional array for Animation object
        TextureRegion[][] frames2d = TextureRegion.split(texture, (int) getWidth(), (int) getHeight());
        TextureRegion[] frames1d = new TextureRegion[colls * rows];
        int index = 0;

        for (TextureRegion[] row : frames2d) {
            for (TextureRegion frame : row) {
                frames1d[index++] = frame;
            }
        }

        animation = new Animation(frameDuration, frames1d);


    }

    public void restart() {
        time = 0;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }


    private void prepareDraw() {
        time += Gdx.graphics.getDeltaTime();
        super.setRegion(animation.getKeyFrame(time, looping));
    }


    @Override
    public void draw(Batch batch) {
        prepareDraw();
        super.draw(batch);
    }

    @Override
    public void draw(Batch batch, float alphaModulation) {
        prepareDraw();
        super.draw(batch, alphaModulation);
    }
}
