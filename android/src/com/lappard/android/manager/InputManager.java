package com.lappard.android.manager;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.lappard.android.interfaces.TouchListener;

import java.util.ArrayList;
import java.util.List;

public class InputManager extends InputAdapter{

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

    public void removeListener(TouchListener l) {
        listener.remove(l);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchUp();
        return true;
    }
}

