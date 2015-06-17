package com.lappard.android;

import android.content.Context;

import com.badlogic.gdx.Game;
import com.lappard.android.screens.GameScreen;


public class LeopardRun extends Game {
    public static boolean DEBUG_MODE = true;

    private Context context;


    public LeopardRun(Context context) {
        this.context = context;
    }

    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }
}
