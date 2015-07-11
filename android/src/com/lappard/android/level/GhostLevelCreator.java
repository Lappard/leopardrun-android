package com.lappard.android.level;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lappard.android.util.Event;

import java.util.List;

public class GhostLevelCreator extends LevelCreator{

    private World world;
    private LevelData level;

    public GhostLevelCreator(LevelData level){
        this.level = level;
    }

    @Override
    public void requestLevelData() {
        List<Actor> actors = parseLevel(level);
        Event.getBus().post(new Level(actors));
    }

    @Override
    public LevelData getRawData() {
        return level;
    }

}
