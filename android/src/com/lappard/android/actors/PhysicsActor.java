package com.lappard.android.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PhysicsActor extends Actor {

    protected Sprite sprite;
    protected Body body;
    protected Fixture fixture;

    public void initPhysics(PolygonShape shape, float x, float y, World world, boolean isDynamic) {
        BodyDef bodyDef = new BodyDef();

        if (isDynamic)
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        else
            bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(x, y));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);
    }

    public void initPhysics(PolygonShape shape, float x, float y, World world) {
        initPhysics(shape, x, y, world, false);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.draw(batch, parentAlpha);
    }
}
