package com.lappard.android.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Jonas on 13.05.2015.
 */
public class Floor extends Entity {

    private Rectangle rect;
    private ShapeRenderer shapeRenderer;

    public Floor(int x, int y, int width, int height, World world) {
        rect = new Rectangle(x, y, width, height);

        shapeRenderer = new ShapeRenderer();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth() / 2f, rect.getHeight() / 2f);

        initPhysics(shape, x, y, world);
    }

    @Override
    public void update() {
        rect.setPosition(body.getPosition());
    }

    @Override
    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.5f, 1f, 0.5f, 1);
        shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        shapeRenderer.end();
    }
}
