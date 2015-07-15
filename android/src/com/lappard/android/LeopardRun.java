package com.lappard.android;

import android.content.Context;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.lappard.android.screens.GameScreen;
import com.lappard.android.screens.IScreen;
import com.lappard.android.screens.MainMenu;
import com.lappard.android.screens.MenuScreen;
import com.lappard.android.screens.SplashScreen;
import com.lappard.android.screens.events.ScreenActivateEvent;
import com.lappard.android.screens.events.ScreenCreationEvent;
import com.lappard.android.screens.events.ScreenRemoveEvent;
import com.lappard.android.util.Event;
import com.squareup.otto.Subscribe;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;


public class LeopardRun implements ApplicationListener {
    public static boolean DEBUG_MODE = !true;

    private Context context;
    private List<IScreen> _screens;
    private List<IScreen> _toRemoveScreens;

    public LeopardRun(Context context) {
        this.context = context;
        _screens = new Vector<IScreen>();
        _toRemoveScreens = new Vector<IScreen>();
        Event.getBus().register(this);
    }

    public void dispose() {
        for(IScreen s : _screens){
            s.dispose();
        }
    }

    public void pause(){
        for(IScreen s : _screens){
            s.pause();
        }
    }

    @Override
    public void resume() {
        for(IScreen s : _screens){
            s.resume();
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int size = _screens.size();
        for(int i = size-1; i >= 0; i--){
            _screens.get(i).render(Gdx.graphics.getDeltaTime());
        }
        for (IScreen rms : _toRemoveScreens){
            _screens.remove(rms);
        }
        _toRemoveScreens.clear();

    }

    public void resize(int width, int height) {
        for(IScreen s : _screens){
            s.resize(width,height);
        }
    }


    private void addScreen(IScreen s) {
        if (s != null){
            s.show();
            s.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            _screens.add(s);
        }
    }


    @Subscribe
    public void removeScreen(ScreenRemoveEvent screenEvent){

            _toRemoveScreens.add(screenEvent.screen);

    }


    @Subscribe
    public void createScreen(ScreenCreationEvent screenEvent) {
        addScreen(screenEvent.screen);
    }

    @Subscribe
    public void activateScreen(ScreenActivateEvent screenEvent){
        if(screenEvent.screen != null){
            for(IScreen s : _screens){
                if(screenEvent.screen == s){
                    s.setActive();
                    break;
                }
            }
        }else{
            for(IScreen s : _screens){
                s.setActive();
            }
        }
    }


    @Override
    public void create() {
        Event.getBus().post(new ScreenCreationEvent(new SplashScreen()));
    }

    public Context getContext() {
        return context;
    }
}
