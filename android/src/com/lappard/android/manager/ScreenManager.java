package com.lappard.android.manager;

import com.lappard.android.entity.Screen;
import com.lappard.android.screens.GameScreen;

public class ScreenManager {
    private GameScreen gameScreen;

    public ScreenManager() {

    }

    public Screen loadLevel() {
        this.gameScreen = new GameScreen();
        return this.gameScreen;
    }

    public void renderLevel() {
        this.gameScreen.render();
    }
}
