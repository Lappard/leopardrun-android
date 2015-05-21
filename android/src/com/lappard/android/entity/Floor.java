package com.lappard.android.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Jonas on 13.05.2015.
 */
public class Floor extends Entity {

    private Rectangle rect;

    public Floor(float x, float y, float width, float height, World world) {
        rect = new Rectangle(x, y, width, height);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth() / 2f, rect.getHeight() / 2f);

        initPhysics(shape, x, y, world);
        body.setUserData(this);
    }

    @Override
    public void update() {
        rect.setPosition(body.getPosition());
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
/*        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
        shapeRenderer.rect(rect.getX() - rect.getWidth() / 2f, rect.getY() - rect.getHeight() / 2f, rect.getWidth(), rect.getHeight());
        shapeRenderer.end();*/
    }
}
