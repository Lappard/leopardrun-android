package com.lappard.android.level;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lappard.android.actors.Block;
import com.lappard.android.actors.Floor;

import java.util.List;
import java.util.Vector;

public abstract class LevelCreator {

    public static final String OBJECT_TYPE_BLOCK = "b";
    public static final String OBJECT_TYPE_GROUND = "g";

    private World world;
    private int lastX;

    abstract public void requestLevelData();
    abstract public LevelData getRawData();

    public void setWorld(World world){
        this.world = world;
    }

    protected List<Actor> parseLevel(LevelData level){
        List<Actor> actors = new Vector<>();
        for (LevelData.LevelObject[] part : level.levelparts) {
            actors.addAll(createActors(part));
            lastX += part[part.length - 1].x;
        }

        return actors;
    }

    protected List<Actor> createActors(LevelData.LevelObject[] part) {
        List<Actor> actors = new Vector<>();
        for (LevelData.LevelObject obj : part) {
            switch (obj.type) {
                case OBJECT_TYPE_BLOCK:
                    actors.add(new Block(world, (lastX + obj.x) * 2, obj.y * 2));
                    break;
                case OBJECT_TYPE_GROUND:
                    actors.add(new Floor(world, (lastX + obj.x) * 2, obj.y * 2));
                    break;
            }
        }

        return actors;
    }

}

