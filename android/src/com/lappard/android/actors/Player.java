package com.lappard.android.actors;

import android.util.Log;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lappard.android.util.AnimatedSprite;

public class Player extends PhysicsActor {

    public Player(World world, float x, float y) {
        sprite = new AnimatedSprite(new Texture("cat.png"), 5, 2, 0.1f);
        sprite.setSize(105, 105);
        initPhysicsAsBox(world, x, y, true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void jump() {
        Log.d("Player", "jump!");
        body.applyLinearImpulse(new Vector2(0, 30), body.getPosition(), true);
    }
}
