package com.lappard.android.actors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lappard.android.screens.GameScreen;

public class PhysicsActor extends Actor {

    protected Sprite sprite;
    protected Body body;
    protected Fixture fixture;

    public void initPhysics(World world, PolygonShape shape, float x, float y, boolean isDynamic) {
        BodyDef bodyDef = new BodyDef();

        if (isDynamic)
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        else
            bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(x, y));
        bodyDef.fixedRotation = true;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);
        body.setUserData(this);
        fixture.setUserData(this);

        sprite.setCenter(sprite.getWidth() / 2f, sprite.getHeight() / 2f);
    }

    public void initPhysics(World world, PolygonShape shape, float x, float y) {
        initPhysics(world, shape, x, y, false);
    }

    public void initPhysicsAsBox(World world, float x, float y, boolean isDynamic) {
        sprite.setSize(sprite.getWidth() / GameScreen.PIXEL_PER_METER, sprite.getHeight() / GameScreen.PIXEL_PER_METER);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth() / 2f, sprite.getHeight() / 2f, new Vector2(sprite.getWidth() / 2f, sprite.getHeight() / 2f), 0);
        initPhysics(world, shape, x, y, isDynamic);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.draw(batch, parentAlpha);
    }

    public void onContact(Actor other){

    }
}
