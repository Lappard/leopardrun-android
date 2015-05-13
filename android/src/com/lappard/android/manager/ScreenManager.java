package com.lappard.android.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.lappard.android.interfaces.TouchListener;
import com.lappard.android.screens.Screen;

/**
 * Created by Timpi on 10.05.15.
 */
public class ScreenManager{
    private Screen currentScreen;
    private InputManager inputManager;

    public ScreenManager(InputManager inputManager) {
        this.inputManager = inputManager;
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
            currentScreen.render();
    }

}
