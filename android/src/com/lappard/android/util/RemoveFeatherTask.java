package com.lappard.android.util;

import com.badlogic.gdx.utils.Timer;
import com.lappard.android.actors.Leopard;
import com.lappard.android.audio.AudioManager;
import com.lappard.android.graphic.AssetManager;

public class RemoveFeatherTask extends Timer.Task {
    private Leopard leo;

    public RemoveFeatherTask(Leopard leo) {
        this.leo = leo;
    }

    @Override
    public void run() {
        this.leo.setHasFeather(false);
        AudioManager.getInstance().playSound(AssetManager.SOUND_THEME, true);
    }
}
