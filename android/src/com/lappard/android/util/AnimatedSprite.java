package com.lappard.android.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class AnimatedSprite {

    private int width, height;
    private float time;
    private Animation animation;
    private boolean looping = true;

    public AnimatedSprite(Texture texture, int colls, int rows, float frameDuration) {
        width = texture.getWidth() / colls;
        height = texture.getHeight() / rows;

        //split texture and put all frames in one dimensional array for Animation object
        TextureRegion[][] frames2d = TextureRegion.split(texture, width, height);
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

    public void draw(SpriteBatch batch, float x, float y) {
        time += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(time, looping), x, y);
    }

}
