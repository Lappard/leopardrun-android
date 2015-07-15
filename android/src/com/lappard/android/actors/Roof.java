package com.lappard.android.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lappard.android.graphic.AnimatedSprite;
import com.lappard.android.graphic.AssetManager;

public class Roof extends PhysicsActor {
    private Leopard leo;

    public Roof(World world,Leopard leo, float x, float y) {
        this.leo = leo;
        this.collisionCategory = Obstacle.COLLISION_CATEGORY;
        this.collisionMask = Player.COLLISION_CATEGORY;
        sprite = new AnimatedSprite(AssetManager.getInstance().getTexture(AssetManager.TEXTURE_CAT), 5, 2, 0.1f);
        initPhysicsAsBox(world, x, y, BodyDef.BodyType.KinematicBody);
    }

    @Override
    public void act(float delta) {
        //super.act(delta);
        //this.body.setTransform(this.leo.getX(), this.leo.getY(), this.body.getAngle());
    }

    //@Override
    //public void draw(Batch batch, float parentAlpha) {
    //}
}
