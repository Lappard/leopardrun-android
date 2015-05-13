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

    public Player(int x, int y, World world){
        shapeRenderer = new ShapeRenderer();
        rect = new Rectangle(x, y, 100, 200);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth() / 2f, rect.getHeight() / 2f);

        initPhysics(shape, world);
    }

    @Override
    public void update() {
        rect.setPosition(body.getPosition());
    }

    @Override
    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 0.5f, 0.5f, 1);
        shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        shapeRenderer.end();
    }
}
