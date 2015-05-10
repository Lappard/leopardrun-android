package com.lappard.android.manager;

import com.lappard.android.interfaces.TouchListener;

import java.util.ArrayList;
import java.util.List;

public class InputManager {

    private ArrayList<TouchListener> listener;

    public InputManager() {
        this.listener = new ArrayList<TouchListener>();
    }

    public void touchUp() {
        for(TouchListener l : listener) {
            l.touchUp();
        }
    }

    public boolean registerListener(TouchListener l) {
        if (!this.listener.contains(l)) {
            if (this.listener.add(l)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

