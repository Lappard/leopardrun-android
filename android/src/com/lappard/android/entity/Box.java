package com.lappard.android.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lappard.android.screens.GameScreen;

/**
 * Created by Jonas on 04.06.2015.
 */
public class Box extends Entity {

    private Texture texture;

    public Box(float x, float y, World world){
        Texture texture = new Texture("Block.png");


        rect = new Rectangle(x, y, 1, 2);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth() / 2f, rect.getHeight() / 2f);

        initPhysics(shape, x, y, world, true);
        body.setUserData(this);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        float width = this.texture.getWidth() / GameScreen.PIXEL_PER_METER,
            height = this.texture.getHeight() / GameScreen.PIXEL_PER_METER;
        spriteBatch.draw(texture, body.getPosition().x, body.getPosition().y, width, height);
    }
}
