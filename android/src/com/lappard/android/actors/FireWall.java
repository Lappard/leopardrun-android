package com.lappard.android.actors;

import android.util.Log;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lappard.android.graphic.AnimatedSprite;
import com.lappard.android.graphic.AssetManager;

public class FireWall extends PhysicsActor {


    public static final short COLLISION_CATEGORY = 0x1 << 8;

    public static float WIDTH = 110;
    public static float HEIGHT = 800;

    public FireWall(World world, float x, float y) {
        this.sprite = new AnimatedSprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_FIREWALL), 7, 1, 0.1f);
        this.sprite.setSize(WIDTH, HEIGHT);
        this.collisionCategory = COLLISION_CATEGORY;
        this.collisionMask = Player.COLLISION_CATEGORY | Ghost.COLLISION_CATEGORY | Obstacle.COLLISION_CATEGORY;
        initPhysicsAsBox(world, x, y, BodyDef.BodyType.DynamicBody, true);
        body.setGravityScale(0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(3f, body.getLinearVelocity().y);
        //body.applyLinearImpulse(new Vector2(0.4f, 0), body.getWorldCenter(), true);
    }


}
