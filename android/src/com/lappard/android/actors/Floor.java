package com.lappard.android.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.lappard.android.graphic.AssetManager;

public class Floor extends Obstacle {

    public static float SIZE = 200;

    public Floor(World world, float x, float y) {
        super(world, new Sprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_FLOOR)), SIZE, SIZE, x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
