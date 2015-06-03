package com.lappard.android.manager;


import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lappard.android.data.Level;
import com.lappard.android.data.NetworkCommand;
import com.lappard.android.screens.GameScreen;

public class GameManager {

    static private GameManager instance;

    private ScreenManager screenManager;
    private InputManager inputManager;
    private NetworkManager networkManager;

    private GameManager() {
        inputManager = new InputManager();
        screenManager = new ScreenManager(inputManager);

        Gdx.input.setInputProcessor(inputManager);

        networkManager = new NetworkManager();
        networkManager.connect();
        networkManager.on(NetworkManager.ACTION_CREATE_LEVEL, new NetworkManager.EventListener() {
            @Override
            public void onEvent(NetworkCommand cmd) {
                for (Level.LevelObject[] part : cmd.process.level.levelparts) {
                    for (Level.LevelObject o : part) {
                        Log.i("Levelobject", "type:" + o.type + " x:" + o.x + ", y:" + o.y);
                    }
                }
            }
        });
        networkManager.on(NetworkManager.ACTION_CONNECTION_ESTABLISHED, new NetworkManager.EventListener(){

            @Override
            public void onEvent(NetworkCommand cmd) {
                networkManager.emit(NetworkManager.ACTION_CREATE_LEVEL);
            }
        });

        screenManager.setScreen(new GameScreen());

    }

    static public GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
            return instance;
        } else {
            return instance;
        }
    }

    public void renderScreen() {
        this.screenManager.update();
        this.screenManager.render();
    }
}
