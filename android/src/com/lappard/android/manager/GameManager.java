package com.lappard.android.manager;


import com.badlogic.gdx.Gdx;
import com.lappard.android.screens.GameScreen;

public class GameManager {

    static private GameManager instance;

    private ScreenManager screenManager;
    private InputManager inputManager;

    private GameManager() {
        inputManager = new InputManager();
        screenManager = new ScreenManager(inputManager);

        Gdx.input.setInputProcessor(inputManager);

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
