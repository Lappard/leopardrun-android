package com.lappard.android.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ScrollingBackground extends TextureRegionDrawable{

    int srcX = 0;

   /* public ScrollingBackground(Texture texture) {
        super(texture);
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge.Repeat);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw((Texture)this.getDrawable(), 0, 0, srcX, 0, (int)getWidth(), (int)getHeight());
        srcX++;
    }*/
}
