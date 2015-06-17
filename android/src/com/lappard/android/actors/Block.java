package com.lappard.android.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.lappard.android.graphic.AssetManager;

public class Block extends Obstacle {

    public static float SIZE = 100;

    public Block(World world, float x, float y) {
        super(world, new Sprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_BLOCK)), SIZE, SIZE, x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
