package com.lappard.android.audio;

import com.badlogic.gdx.audio.Sound;
import com.lappard.android.graphic.AssetManager;

import java.util.List;
import java.util.Vector;

public class AudioManager {

    private static AudioManager instance;
    private Vector<Sound> soundList = new Vector<Sound>();

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
            return instance;
        } else {
            return instance;
        }
    }

    public void pauseAll() {
        for (Sound s : soundList) {
            s.pause();
            soundList.remove(s);
        }
    }

    public long playSound(String s, boolean loop) {
        if (loop) {
            soundList.add(AssetManager.getInstance().getSound(s));
            return AssetManager.getInstance().getSound(s).loop();
        }
        else {
            soundList.add(AssetManager.getInstance().getSound(s));
            return AssetManager.getInstance().getSound(s).play();
        }
    }

}
