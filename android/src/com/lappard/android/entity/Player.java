package com.lappard.android.entity;

import android.graphics.Rect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity{

    private Rectangle rect;

    public Player(float x, float y, World world){
        rect = new Rectangle(x, y, 1, 2);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth() / 2f, rect.getHeight() / 2f);

        initPhysics(shape, x, y, world, true);

    }

    public void jump() {
        body.applyLinearImpulse(0, 10, body.getPosition().x, body.getPosition().y, true);
    }

    @Override
    public void update() {
        Vector2 pos = body.getPosition();
        rect.setPosition(pos);
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 0.5f, 0.5f, 1);
        shapeRenderer.rect(rect.getX() - rect.getWidth() / 2f, rect.getY() - rect.getHeight() / 2f, rect.getWidth(), rect.getHeight());
        shapeRenderer.end();
    }
}
