package com.lappard.android.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.lappard.android.R;
import com.lappard.android.graphic.AssetManager;
import com.lappard.android.level.NetworkLevelCreator;
import com.lappard.android.logic.ScoreManager;
import com.lappard.android.screens.events.ScreenActivateEvent;
import com.lappard.android.screens.events.ScreenCreationEvent;
import com.lappard.android.screens.events.ScreenRemoveEvent;
import com.lappard.android.util.ChangeNameAction;
import com.lappard.android.util.Event;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class MainMenu extends MenuScreen implements Input.TextInputListener{


    public MainMenu() {
        super(AssetManager.TEXTURE_BACKGROUND);
    }

    @Override
    public void show() {
        super.show();

        font = AssetManager.getInstance().getFont(AssetManager.FONT_SHOJUMARU_14);

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

        ChangeNameAction act = new ChangeNameAction(this);
        Button changeNameButton = createImageButton(650,100, AssetManager.TEXTURE_BLOCK,"Change Playername",act);


        font = AssetManager.getInstance().getFont(AssetManager.FONT_SHOJUMARU_24);

        layout.add(createLabel(R.string.menu_heading)).center();
        layout.row();
        layout.add(singlePlayerButton);
        layout.row().padTop(20);
        layout.add(multiPlayerButton);
        layout.row().padTop(20);
        layout.add(changeNameButton);

    }

    @Override
    public void input (String text) {
        ScoreManager.getInstance().setPlayername(text);
    }

    @Override
    public void canceled () {

    }
}
