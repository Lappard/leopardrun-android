package com.lappard.android.entity;

import android.graphics.Rect;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity{

    private ShapeRenderer shapeRenderer;
    private Rectangle rect;
    private Body body;

    public Player(int x, int y, World w){
        shapeRenderer = new ShapeRenderer();
        rect = new Rectangle(0,0,10,20);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(rect.getX(), rect.getY());

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth() / 2f, rect.getHeight() / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        shapeRenderer.end();
    }
}
