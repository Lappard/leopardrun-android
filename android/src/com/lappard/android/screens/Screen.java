package com.lappard.android.screens;

import com.lappard.android.interfaces.TouchListener;

/**
 * Created by Jonas on 13.05.2015.
 */
public abstract class Screen implements TouchListener {

    public abstract void create();
    public abstract void update();
    public abstract void render();
    public abstract void destroy();
}
