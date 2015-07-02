package com.lappard.android.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lappard.android.graphic.AnimatedSprite;
import com.lappard.android.graphic.AssetManager;

public class Player extends PhysicsActor {

    private boolean canJump;

    public Player(World world, float x, float y) {
        sprite = new AnimatedSprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_CAT), 5, 2, 0.1f);
        sprite.setSize(90, 90);
        initPhysicsAsBox(world, x, y, BodyDef.BodyType.DynamicBody);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(3f, body.getLinearVelocity().y);
        //body.applyLinearImpulse(new Vector2(0.4f, 0), body.getWorldCenter(), true);
    }

    @Override
    public void onContact(Actor other, Contact contact) {
        if (other instanceof Obstacle) {
            canJump = true;
        }
    }

    public void jump() {
        if (canJump) {
            body.setLinearVelocity(body.getLinearVelocity().x, 12);
            //body.applyLinearImpulse(new Vector2(0, 12), body.getWorldCenter(), true);
            canJump = false;
        }

    }

}
