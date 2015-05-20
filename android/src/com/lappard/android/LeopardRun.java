package com.lappard.android;

import android.content.Context;


import com.badlogic.gdx.ApplicationAdapter;
import com.lappard.android.manager.GameManager;


public class LeopardRun extends ApplicationAdapter {
    private final Context context;

    private GameManager gameManager;

    public LeopardRun(Context context){
        this.context = context;
    }

    public void create () {
        gameManager = GameManager.getInstance();
    }

    public void render () {
        gameManager.renderScreen();
    }



}
