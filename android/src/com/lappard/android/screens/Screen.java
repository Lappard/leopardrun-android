package com.lappard.android.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lappard.android.interfaces.TouchListener;

/**
 * Created by Jonas on 13.05.2015.
 */
public abstract class Screen implements TouchListener {

    public abstract void create();
    public abstract void update();
    public abstract void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer);
    public abstract void destroy();
}
