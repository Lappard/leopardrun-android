package com.lappard.android.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public abstract class Entity {

    protected Body body;
    protected Fixture fixture;

    public void initPhysics(PolygonShape shape, float x, float y, World world, boolean isDynamic) {
        BodyDef bodyDef = new BodyDef();

        if(isDynamic)
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

    public abstract void update();

    public abstract void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer);

}
