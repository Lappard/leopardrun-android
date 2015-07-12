package com.lappard.android.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.lappard.android.logic.ScoreManager;
import com.lappard.android.screens.MainMenu;

public class ChangeNameAction extends Action {
    MainMenu menu;

    public ChangeNameAction (MainMenu menu) {
        super();
        this.menu = menu;
    }

    @Override
    public boolean act(float delta) {
        Gdx.input.getTextInput(menu, "Change your name", ScoreManager.getInstance()._playerName,"");
        return true;
    }
}
