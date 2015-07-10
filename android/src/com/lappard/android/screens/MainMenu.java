package com.lappard.android.screens;


import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.lappard.android.R;
import com.lappard.android.graphic.AssetManager;
import com.lappard.android.level.NetworkLevelCreator;
import com.lappard.android.screens.events.ScreenActivateEvent;
import com.lappard.android.screens.events.ScreenCreationEvent;
import com.lappard.android.screens.events.ScreenRemoveEvent;
import com.lappard.android.util.Event;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class MainMenu extends MenuScreen{


    public MainMenu() {
        super(AssetManager.TEXTURE_BACKGROUND);
    }

    @Override
    public void show() {
        super.show();
        Button singlePlayerButton = createImageButton(400, 200, AssetManager.TEXTURE_BLOCK,
                R.string.single_player_button, sequence(run(new Runnable() {
                    @Override
                    public void run() {
                        GameScreen game = new GameScreen();
                        game.setLevelCreator(new NetworkLevelCreator());
                        Event.getBus().post(new ScreenCreationEvent(game));
                    }
                }), moveTo(0, -stage.getHeight(), 1f), run(new Runnable() {

                    @Override
                    public void run() {
                        Event.getBus().post(new ScreenActivateEvent());
                        Event.getBus().post(new ScreenRemoveEvent(instance));
                    }
                }))
        );

        Button multiPlayerButton = createImageButton(400, 200, AssetManager.TEXTURE_BLOCK,
                R.string.multi_player_button, createScreenTransition(new ScreenCreator() {
                    @Override
                    public IScreen createScreen() {
                        return new GhostSelection();
                    }
                }));


        layout.add(createLabel(R.string.menu_heading)).center();
        layout.row();
        layout.add(singlePlayerButton);
        layout.row().padTop(20);
        layout.add(multiPlayerButton);
    }
}
