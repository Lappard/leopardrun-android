package com.lappard.android.screens.events;

import com.lappard.android.screens.IScreen;

/**
 * Created by Jonathan on 01.07.15.
 */
public class ScreenCreationEvent {
    public ScreenCreationEvent(IScreen s){
        screen = s;
    }

    public IScreen screen;
}