package com.lappard.android;

import android.content.Context;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.lappard.android.screens.GameScreen;
import com.lappard.android.screens.MenuScreen;

import java.util.Vector;


public class LeopardRun extends Game {
    public static boolean DEBUG_MODE = true;

    private Context context;
    private Vector<Screen> _screens;

    public LeopardRun(Context context) {
        this.context = context;
        _screens = new Vector<Screen>();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int size = _screens.size();
        for(int i = size-1; i >= 0; i--){
            _screens.get(i).render(Gdx.graphics.getDeltaTime());
        }

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    public void addScreen(Screen s) {
        if (s != null){
            s.show();
            s.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            _screens.add(s);
        }
    }

    @Override
    public Screen getScreen() {
        return super.getScreen();
    }

    @Override
    public void create() {
        addScreen(new MenuScreen());
    }
}
