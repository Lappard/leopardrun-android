package com.lappard.android.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.lappard.android.util.AnimatedSprite;

public class Obstacle extends PhysicsActor {

    public Obstacle(World world, Sprite sprite, float x, float y) {
        this.sprite = sprite;
        this.sprite.setSize(128, 128);
        initPhysicsAsBox(world, x, y, false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
