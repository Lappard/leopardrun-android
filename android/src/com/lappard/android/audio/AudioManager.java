package com.lappard.android.audio;

import com.badlogic.gdx.audio.Sound;
import com.lappard.android.graphic.AssetManager;

public class AudioManager {

    private static AudioManager instance;

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
            return instance;
        } else {
            return instance;
        }
    }

    public long playSound(String s) {
        return AssetManager.getInstance().getSound(s).play();
    }
}
