package com.lappard.android.level;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

public interface LevelCreator {
    void requestLevelData();
    LevelData getRawData();

    public void setWorld(World world);

}

