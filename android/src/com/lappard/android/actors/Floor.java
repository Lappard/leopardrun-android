package com.lappard.android.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

public class Floor extends Obstacle {

    public Floor(World world, float x, float y) {
        super(world, new Sprite(new Texture("ground.png")), x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
