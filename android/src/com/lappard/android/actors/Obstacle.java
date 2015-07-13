package com.lappard.android.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Obstacle extends PhysicsActor {


    public static final short COLLISION_CATEGORY = 0x1 << 4;


    private boolean switchToDynamic = false;

    public Obstacle(World world, Sprite sprite, float width, float height, float x, float y) {
        this.sprite = sprite;
        this.sprite.setSize(width, height);
        this.collisionCategory = COLLISION_CATEGORY;
        this.collisionMask = Player.COLLISION_CATEGORY | Ghost.COLLISION_CATEGORY;
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
