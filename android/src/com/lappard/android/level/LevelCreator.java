package com.lappard.android.level;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

public interface LevelCreator {
    void requestLevelData();
    LevelData getRawData();

}

