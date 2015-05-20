package com.lappard.android.manager;

import android.graphics.Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.lappard.android.interfaces.TouchListener;
import com.lappard.android.screens.Screen;

/**
 * Created by Timpi on 10.05.15.
 */
public class ScreenManager{
    private Screen currentScreen;
    private InputManager inputManager;
    private OrthographicCamera cam;

    private ShapeRenderer shapeRender;
    private SpriteBatch spriteBatch;

    public ScreenManager(InputManager inputManager) {
        this.inputManager = inputManager;
        cam = new OrthographicCamera(24, 13.5f);
        cam.position.set(24/2f, 13.5f/2f, 0);
        cam.update();

        shapeRender = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        shapeRender.setProjectionMatrix(cam.combined);
        spriteBatch.setProjectionMatrix(cam.combined);
    }

    public void setScreen(Screen newScreen) {
        if(currentScreen != null) {
            inputManager.removeListener(currentScreen);
            currentScreen.destroy();
        }
        currentScreen = newScreen;
        inputManager.registerListener(newScreen);
        newScreen.create();
    }

    public void update() {
        if(currentScreen != null)
            currentScreen.update();
    }

    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(currentScreen != null)
            currentScreen.render(spriteBatch, shapeRender);
    }

}
