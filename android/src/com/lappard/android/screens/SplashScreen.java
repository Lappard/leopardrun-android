package com.lappard.android.screens;

import android.util.Log;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.lappard.android.graphic.AssetManager;
import com.lappard.android.screens.events.ScreenActivateEvent;
import com.lappard.android.screens.events.ScreenCreationEvent;
import com.lappard.android.screens.events.ScreenRemoveEvent;
import com.lappard.android.util.Event;

public class SplashScreen extends MenuScreen {

    Texture logo = AssetManager.getInstance().getTexture(AssetManager.TEXTURE_LOGO);

    public SplashScreen() {
        super(AssetManager.TEXTURE_BACKGROUND);
    }

    @Override
    public void show() {
        super.show();
        layout.add(new Image(logo)).center();
        layout.row();

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Event.getBus().post(new ScreenCreationEvent(new MainMenu()));
                Event.getBus().post(new ScreenActivateEvent());
                Event.getBus().post(new ScreenRemoveEvent(instance));
                Log.d("abc", "test");
            }
        }, 3);
    }
}

