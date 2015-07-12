package com.lappard.android.screens;


import android.util.Log;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.lappard.android.R;
import com.lappard.android.graphic.AssetManager;
import com.lappard.android.logic.ScoreManager;

public class GameOver extends MenuScreen {

    public GameOver() {
        super(AssetManager.TEXTURE_BACKGROUND);
    }

    @Override
    public void show() {
        super.show();
        Button menuButton = createImageButton(400, 200, AssetManager.TEXTURE_BLOCK,
                R.string.to_menu_button, createScreenTransition(new ScreenCreator() {
                    @Override
                    public IScreen createScreen() {
                        return new MainMenu();
                    }
                }));

        layout.add(createLabel(R.string.game_over)).center();
        layout.row();
        layout.add(createLabel("Score:")).center();
        int scoreInt = Math.round(ScoreManager.getInstance().getScore());
        layout.add(createLabel(Integer.toString(scoreInt)));
        layout.row();
        layout.add(menuButton);
    }
}
