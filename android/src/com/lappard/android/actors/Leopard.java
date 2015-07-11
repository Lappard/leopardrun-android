package com.lappard.android.actors;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lappard.android.graphic.AnimatedSprite;
import com.lappard.android.graphic.AssetManager;

public class Leopard extends PhysicsActor {

    public static float VELOCITY_JUMP = 15;
    public static float VELOCITY_RUN = 3;
    public static float HEIGHT_DEATH = -2;
    public static int SIZE = 90;


    public Leopard(World world, float x, float y, String textureName) {
        sprite = new AnimatedSprite(AssetManager.getInstance().getTexture(textureName), 5, 2, 0.1f);
        sprite.setSize(SIZE, SIZE);
        initPhysicsAsBox(world, x, y, BodyDef.BodyType.DynamicBody);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(VELOCITY_RUN, body.getLinearVelocity().y);
    }

    public void jump(){
        body.setLinearVelocity(body.getLinearVelocity().x, VELOCITY_JUMP);
    }
}
