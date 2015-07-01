package com.lappard.android.screens.events;

import com.lappard.android.screens.IScreen;

/**
 * Created by Jonathan on 01.07.15.
 */
public class ScreenRemoveEvent {
    public ScreenRemoveEvent(IScreen s){
        screen = s;
    }
    public IScreen screen;
}