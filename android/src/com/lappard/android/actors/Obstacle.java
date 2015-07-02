package com.lappard.android.actors;

import android.util.Log;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Obstacle extends PhysicsActor {


    private boolean switchToDynamic = false;

    public Obstacle(World world, Sprite sprite, float width, float height, float x, float y) {
        this.sprite = sprite;
        this.sprite.setSize(width, height);
        initPhysicsAsBox(world, x, y,  BodyDef.BodyType.StaticBody);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(switchToDynamic){
            switchToDynamic = false;
            body.setType(BodyDef.BodyType.DynamicBody);
        }
    }

    @Override
    public void onContact(Actor other, Contact contact) {
        if(other instanceof FireWall){
            switchToDynamic = true;
            sprite.setColor(0.2f, 0.1f, 0.1f, 1);
        }
    }
}
