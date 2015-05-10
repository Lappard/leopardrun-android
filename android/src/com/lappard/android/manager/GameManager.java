package com.lappard.android.manager;

import com.lappard.android.entity.Screen;
import com.lappard.android.interfaces.TouchListener;

public class GameManager {
    static private GameManager instance;

    private ScreenManager screenManager;
    private InputManager inputManager;

    private GameManager() {
        this.screenManager = new ScreenManager();
        this.inputManager = new InputManager();
    }

    static public GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
            return instance;
        } else {
            return instance;
        }
    }
// INPUT MANAGER METHODS

    public void touchUp() {
        this.inputManager.touchUp();
    }

    public boolean registerTouchListener(TouchListener l) {
        if (this.inputManager.registerListener(l)) {
            return true;
        } else {
            return false;
        }
    }

// SCREEN MANAGER METHODS

    public Screen loadLevel() {
        return this.screenManager.loadLevel();
    }

    public void renderLevel() {
        this.screenManager.renderLevel();
    }
}
