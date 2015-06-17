package com.lappard.android.actors;

import android.util.Log;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lappard.android.util.AnimatedSprite;

public class Player extends PhysicsActor {

    private boolean canJump;

    public Player(World world, float x, float y) {
        sprite = new AnimatedSprite(new Texture("cat.png"), 5, 2, 0.1f);
        sprite.setSize(100, 100);
        initPhysicsAsBox(world, x, y, true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void onContact(Actor other) {
        if(other instanceof Obstacle){
            canJump = true;
        }
    }

    public void jump() {
        if(canJump){
            body.applyLinearImpulse(new Vector2(0, 14), body.getWorldCenter(), true);
            canJump = false;
        }

    }
}
