package com.lappard.android.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

public class Block extends Obstacle {

    public static float SIZE = 100;

    public Block(World world, float x, float y) {
        super(world, new Sprite(new Texture("block.png")), SIZE, SIZE, x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
